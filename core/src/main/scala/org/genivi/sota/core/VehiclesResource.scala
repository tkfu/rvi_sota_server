/**
 * Copyright: Copyright (C) 2015, Jaguar Land Rover
 * License: MPL-2.0
 */
package org.genivi.sota.core

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.Marshaller._
import akka.http.scaladsl.model.StatusCodes.NoContent
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route, StandardRoute}
import akka.stream.ActorMaterializer
import eu.timepit.refined._
import eu.timepit.refined.string._
import io.circe.generic.auto._
import io.circe.syntax._
import org.genivi.sota.core.common.NamespaceDirective._
import org.genivi.sota.core.data._
import org.genivi.sota.core.db.{InstallHistories, UpdateSpecs, Vehicles}
import org.genivi.sota.core.resolver.{ConnectivityClient, ExternalResolverClient}
import org.genivi.sota.data.Namespace._
import org.genivi.sota.data.Vehicle
import org.genivi.sota.marshalling.CirceMarshallingSupport
import org.genivi.sota.rest.ErrorRepresentation
import org.genivi.sota.rest.Validation._
import org.joda.time.DateTime
import scala.concurrent.{ExecutionContext, Future}
import scala.languageFeature.implicitConversions
import scala.languageFeature.postfixOps
import slick.driver.MySQLDriver.api.Database


class VehiclesResource(db: Database, client: ConnectivityClient, resolverClient: ExternalResolverClient)
                      (implicit system: ActorSystem, mat: ActorMaterializer) {

  import CirceMarshallingSupport._
  import Directives._
  import WebService._
  import system.dispatcher

  implicit val _db = db

  case object MissingVehicle extends Throwable

  def exists(vehicle: Vehicle)
    (implicit ec: ExecutionContext): Future[Vehicle] =
    db.run(Vehicles.exists(vehicle))
      .flatMap(_
        .fold[Future[Vehicle]]
          (Future.failed(MissingVehicle))(Future.successful))

  def deleteVehicle(ns: Namespace, vehicle: Vehicle)
  (implicit ec: ExecutionContext): Future[Unit] =
    for {
      _ <- exists(vehicle)
      _ <- db.run(UpdateSpecs.deleteRequiredPackageByVin(ns, vehicle))
      _ <- db.run(UpdateSpecs.deleteUpdateSpecByVin(ns, vehicle))
      _ <- db.run(Vehicles.deleteById(vehicle))
    } yield ()

  def fetchVehicle(ns: Namespace, vin: Vehicle.Vin): Route = {
    completeOrRecoverWith(exists(Vehicle(ns, vin))) {
      case MissingVehicle =>
        complete(StatusCodes.NotFound ->
          ErrorRepresentation(ErrorCodes.MissingVehicle, "Vehicle doesn't exist"))
    }
  }

  def updateVehicle(ns: Namespace, vin: Vehicle.Vin): Route = {
    complete(db.run(Vehicles.create(Vehicle(ns, vin))).map(_ => NoContent))
  }

  def deleteVehicle(ns: Namespace, vin: Vehicle.Vin): Route = {
    completeOrRecoverWith(deleteVehicle(ns, Vehicle(ns, vin))) {
      case MissingVehicle =>
        complete(StatusCodes.NotFound ->
          ErrorRepresentation(ErrorCodes.MissingVehicle, "Vehicle doesn't exist"))
    }
  }

  def search(ns: Namespace): Route = {
    parameters(('status.?(false), 'regex.?)) { (includeStatus: Boolean, regex: Option[String]) =>
      val resultIO = VehicleSearch.search(ns, regex, includeStatus)
      complete(db.run(resultIO))
    }
  }

  val route =
    (pathPrefix("vehicles") & extractNamespace) { ns =>
      extractVin { vin =>
        pathEnd {
          get {
            fetchVehicle(ns, vin)
          } ~
          put {
            updateVehicle(ns, vin)
          } ~
          delete {
            deleteVehicle(ns, vin)
          }
        }
      } ~
      (pathEnd & get) {
        search(ns)
      }
    }
}