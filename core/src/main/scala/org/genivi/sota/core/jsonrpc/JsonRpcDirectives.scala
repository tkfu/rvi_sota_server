/**
 * Copyright: Copyright (C) 2015, Jaguar Land Rover
 * License: MPL-2.0
 */
package org.genivi.sota.core.jsonrpc

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server._
import cats.data.Xor
import io.circe.DecodingFailure
import io.circe._
import scala.concurrent.ExecutionContext
import shapeless.HList

import scala.concurrent.Future

final case class Request( jsonrpc: String, method: String, params: Json, id: Option[Int] )

object PredefinedErrors {

  val ParseError = JsonRpcError(-32700, "Parse error")

  object InvalidRequest {
    def apply(data: Json) = JsonRpcError(-32600, "Invalid request", Some(data))
  }

  val MethodNotFound = JsonRpcError(-32601, "Method not found")

  val InvalidParams = JsonRpcError(-32602, "Invalid params")
}

trait JsonRpcDirectives {

  import akka.http.scaladsl.server.Directives._
  import org.genivi.sota.CirceSupport._
  import io.circe.generic.auto._
  import io.circe.syntax._

  type MethodFn = Request => StandardRoute

  import scala.language.implicitConversions

  implicit def lift[In, Out](fn: In => Future[Out])
                            (implicit inDecoder: Decoder[In], outEncoder: Encoder[Out], ec: ExecutionContext) : MethodFn = request => {
    import shapeless._
    import syntax.singleton._
    inDecoder.decodeJson( request.params ).map( fn ).fold[StandardRoute](
      err => complete(ErrorResponse( PredefinedErrors.InvalidParams, request.id)),
      res => complete( res.map( x => ('jsonrpc ->> "2.0") :: ('result ->> outEncoder(x) ) :: ('id ->> request.id) :: HNil) ))
  }

  val ParseErrorHandler = RejectionHandler.newBuilder().handle{
    case MalformedRequestContentRejection(_, Some(ParsingFailure(_, _))) => complete(ErrorResponse( PredefinedErrors.ParseError, None ) )
    case MalformedRequestContentRejection(_, Some(DecodingFailure(msg, _))) => complete(ErrorResponse( PredefinedErrors.InvalidRequest(msg.asJson), None ) )
  }.result()

  def service(methods: (String, MethodFn)*) : Route = service(methods.toMap)

  def service(methods: Map[String, MethodFn]) : Route = extractMaterializer { implicit mat =>
    (handleRejections(ParseErrorHandler) & entity(as[Request])) { request =>
      methods.get(request.method).fold(complete(ErrorResponse(PredefinedErrors.MethodNotFound, request.id)))(_(request))
    }
  }

}

object JsonRpcDirectives extends JsonRpcDirectives