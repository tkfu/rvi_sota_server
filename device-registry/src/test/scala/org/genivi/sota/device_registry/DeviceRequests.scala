/**
 * Copyright: Copyright (C) 2016, ATS Advanced Telematic Systems GmbH
 * License: MPL-2.0
 */
package org.genivi.sota.device_registry

import akka.http.scaladsl.client.RequestBuilding.{Delete, Get, Post, Put}
import akka.http.scaladsl.model.Uri.{Path, Query}
import akka.http.scaladsl.model.{HttpRequest, Uri, StatusCodes}
import cats.Show
import cats.syntax.show._
import io.circe.Json
import io.circe.generic.auto._
import org.genivi.sota.data.{Device, DeviceT, Namespace, Uuid}
import org.genivi.sota.marshalling.CirceMarshallingSupport._
import scala.concurrent.ExecutionContext


/**
 * Generic test resource object
 * Used in property-based testing
 */
object Resource {
  def uri(pathSuffixes: String*): Uri = {
    val BasePath = Path("/api") / "v1"
    Uri.Empty.withPath(pathSuffixes.foldLeft(BasePath)(_/_))
  }
}

/**
 * Testing Trait for building Device requests
 */
trait DeviceRequests { self: ResourceSpec =>

  import Device._
  import StatusCodes._

  val api = "devices"

  def fetchDevice(uuid: Uuid): HttpRequest =
    Get(Resource.uri(api, uuid.show))

  def searchDevice(namespace: Namespace, regex: String): HttpRequest =
    Get(Resource.uri(api).withQuery(Query("namespace" -> namespace.get, "regex" -> regex)))

  def fetchByDeviceId(namespace: Namespace, deviceId: Device.DeviceId): HttpRequest =
    Get(Resource.uri(api).withQuery(Query("namespace" -> namespace.get, "deviceId" -> deviceId.show)))

  def updateDevice(uuid: Uuid, device: DeviceT)
                  (implicit ec: ExecutionContext): HttpRequest =
    Put(Resource.uri(api, uuid.show), device)

  def createDevice(device: DeviceT)
                  (implicit ec: ExecutionContext): HttpRequest = {
    Post(Resource.uri(api), device)
  }

  def createDeviceOk(device: DeviceT)
                    (implicit ec: ExecutionContext): Uuid = {
    createDevice(device) ~> route ~> check {
      status shouldBe Created
      responseAs[Uuid]
    }
  }

  def deleteDevice(uuid: Uuid): HttpRequest =
    Delete(Resource.uri(api, uuid.show))

  def deleteDeviceOk(uuid: Uuid)
                    (implicit ec: ExecutionContext): Unit = {
    deleteDevice(uuid) ~> route ~> check {
      status shouldBe OK
    }
  }

  def updateLastSeen(uuid: Uuid): HttpRequest =
    Post(Resource.uri(api, uuid.show, "ping"))

  def fetchSystemInfo(uuid: Uuid): HttpRequest =
    Get(Resource.uri(api, uuid.show, "system_info"))

  def createSystemInfo(uuid: Uuid, json: Json)
                      (implicit ec: ExecutionContext): HttpRequest =
    Post(Resource.uri(api, uuid.show,"system_info"), json)

  def updateSystemInfo(uuid: Uuid, json: Json)
                      (implicit ec: ExecutionContext): HttpRequest =
    Put(Resource.uri(api, uuid.show,"system_info"), json)

  def listGroupsForDevice(device: Uuid)
                         (implicit ec: ExecutionContext): HttpRequest =
    Get(Resource.uri(api, device.show, "groups"))

}
