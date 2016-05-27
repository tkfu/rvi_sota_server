/**
 * Copyright: Copyright (C) 2015, Jaguar Land Rover
 * License: MPL-2.0
 */
package org.genivi.sota.device_registry.test

import akka.http.scaladsl.model.StatusCodes
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Regex
import io.circe.generic.auto._
import org.genivi.sota.data.Namespaces
import org.genivi.sota.data.{Device, DeviceT}
import org.genivi.sota.marshalling.CirceMarshallingSupport._
import org.joda.time.DateTime
import org.scalacheck._


/**
 * Spec for Devices REST actions
 */
class DeviceResourceSpec extends ResourcePropSpec {

  import Device._
  import DeviceGenerators._
  import StatusCodes._

  def createDeviceOk(device: DeviceT): Id = {
    createDevice(device) ~> route ~> check {
      status shouldBe Created
      responseAs[Id]
    }
  }

  def deleteDeviceOk(id: Id): Unit = {
    deleteDevice(id) ~> route ~> check {
      status shouldBe OK
    }
  }

  def isRecent(time: Option[DateTime]): Boolean = time match {
    case Some(time) => time.isAfter(DateTime.now.minusMinutes(3))
    case None => false
  }

  property("GET, PUT, DELETE, and POST '/ping' request fails on non-existent device") {
    forAll { (id: Id, device: DeviceT) =>
      fetchDevice(id)          ~> route ~> check { status shouldBe NotFound }
      updateDevice(id, device) ~> route ~> check { status shouldBe NotFound }
      deleteDevice(id)         ~> route ~> check { status shouldBe NotFound }
      updateLastSeen(id)       ~> route ~> check { status shouldBe NotFound }
    }
  }

  property("GET request (for Id) after POST yields same device") {
    forAll { devicePre: DeviceT =>
      val id: Id = createDeviceOk(devicePre)

      fetchDevice(id) ~> route ~> check {
        status shouldBe OK
        val devicePost: Device = responseAs[Device]
        devicePost.deviceId shouldBe devicePre.deviceId
        devicePost.deviceType shouldBe devicePre.deviceType
        devicePost.lastSeen shouldBe None
      }

      deleteDeviceOk(id)
    }
  }

  property("GET request with ?deviceId after creating yields same device.") {
    forAll { (deviceId: DeviceId, devicePre: DeviceT) =>

      val id: Id = createDeviceOk(devicePre.copy(deviceId = Some(deviceId)))
      fetchDeviceByDeviceId(deviceId) ~> route ~> check {
        status shouldBe OK
        val devicePost1: Device = responseAs[Device]
        fetchDevice(id) ~> route ~> check {
          status shouldBe OK
          val devicePost2: Device = responseAs[Device]

          devicePost1 shouldBe devicePost2
        }
      }
      deleteDeviceOk(id)
    }
  }

  implicit def DeviceTOrdering(implicit ord: Ordering[Option[DeviceId]]): Ordering[DeviceT] = new Ordering[DeviceT] {
    override def compare(d1: DeviceT, d2: DeviceT): Int = ord.compare(d1.deviceId, d2.deviceId)
  }

  property("GET request with ?regex yields devices which match the regex.") {

    import RegexGenerators._
    import scala.util.Random

    def injectSubstr(s: String, substr: String): String = {
      val pos = Random.nextInt(s.length)
      s.take(pos) ++ substr ++ s.drop(pos)
    }

    forAll { (devices: Seq[DeviceT], regex: String Refined Regex) =>
      val n: Int = Random.nextInt(devices.length + 1)
      val regexInstances: Seq[String] = Range(0, n).map(_ => genStrFromRegex(regex))
      val preparedDevices: Seq[DeviceT] =
        devices.take(n).zip(regexInstances).map { case (d, re) =>
          d.copy(deviceId = d.deviceId match {
            case Some(DeviceId(id)) => Some(DeviceId(injectSubstr(id, re)))
            case None => Some(DeviceId(re))
          })
        }
      val  unpreparedDevices: Seq[DeviceT] = devices.drop(n)

      preparedDevices.length + unpreparedDevices.length shouldBe devices.length

      val created = devices.map(d => createDeviceOk(d) -> d)

      val expectedIds: Seq[Id] = created
        .filter { case (_, d) => d.deviceId.isDefined &&
                                 regex.get.r.findFirstIn(d.deviceId.get.underlying).isDefined }
        .map(_._1)

      searchDevice(regex.get) ~> route ~> check {
        val matchingDevices: Seq[Device] = responseAs[Seq[Device]]
        matchingDevices.map(_.id).toSet shouldBe expectedIds.toSet
      }

      created.map(_._1).foreach(deleteDeviceOk(_))
    }
  }

  property("PUT request after POST succeeds with updated device.") {
    forAll { (devicePre1: DeviceT, devicePre2: DeviceT) =>
      val id: Id = createDeviceOk(devicePre1)

      updateDevice(id, devicePre2) ~> route ~> check {
        val updateStatus = status
        val deviceId = devicePre1.deviceId

        deviceId match {
          case Some(deviceId) =>
            fetchDeviceByDeviceId(deviceId) ~> route ~> check {
              status match {
                case OK => updateStatus shouldBe Conflict
                case NotFound => {
                  updateStatus shouldBe OK

                  fetchDevice(id) ~> route ~> check {
                    status shouldBe OK
                    val devicePost: Device = responseAs[Device]
                    devicePost.id shouldBe id
                    devicePost.deviceId shouldBe devicePre2.deviceId
                    devicePost.deviceType shouldBe devicePre2.deviceType
                    devicePost.lastSeen shouldBe None
                  }
                }
                case _ => assert(false, "unexpected status code: " + status)
              }
            }
          case None => updateStatus shouldBe OK
        }
      }

      deleteDeviceOk(id)
    }
  }

  property("POST request creates a new device.") {
    forAll { devicePre: DeviceT =>
      val id: Id = createDeviceOk(devicePre)

      fetchDevice(id) ~> route ~> check {
        status shouldBe OK
        val devicePost: Device = responseAs[Device]
        devicePost.id shouldBe id
        devicePost.deviceId shouldBe devicePre.deviceId
        devicePost.deviceType shouldBe devicePre.deviceType
      }

      deleteDeviceOk(id)
    }
  }

  property("DELETE request after POST succeds and deletes device.") {
    forAll { (device: DeviceT) =>
      val id: Id = createDeviceOk(device)

      deleteDevice(id) ~> route ~> check {
        status shouldBe OK
      }

      fetchDevice(id) ~> route ~> check {
        status shouldBe NotFound
      }
    }
  }

  property("POST request on 'ping' should update 'lastSeen' field for device.") {
    forAll { (id: Id, devicePre: DeviceT) =>

      val id: Id = createDeviceOk(devicePre)

      updateLastSeen(id) ~> route ~> check {
        status shouldBe OK
      }

      fetchDevice(id) ~> route ~> check {
        val devicePost: Device = responseAs[Device]
        val after: DateTime = DateTime.now()

        devicePost.lastSeen should not be (None)
        isRecent(devicePost.lastSeen) shouldBe true
      }

      deleteDeviceOk(id)
    }
  }

  property("POST request with same deviceId fails with conflict.") {
    forAll { (device1: DeviceT, device2: DeviceT) =>

      val id: Id = createDeviceOk(device1)

      createDevice(device2.copy(deviceId = device1.deviceId)) ~> route ~> check {
        device1.deviceId match {
          case Some(deviceId) => status shouldBe Conflict
          case _ => deleteDeviceOk(responseAs[Id])
        }
      }

      deleteDeviceOk(id)
    }
  }

}
