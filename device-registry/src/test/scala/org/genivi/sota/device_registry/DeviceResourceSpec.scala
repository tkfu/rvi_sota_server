/**
 * Copyright: Copyright (C) 2016, ATS Advanced Telematic Systems GmbH
 * License: MPL-2.0
 */
package org.genivi.sota.device_registry

import akka.http.scaladsl.model.StatusCodes._
import io.circe.Json
import io.circe.generic.auto._
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.genivi.sota.data._
import org.genivi.sota.marshalling.CirceMarshallingSupport._
import org.scalacheck.Arbitrary._


/**
 * Spec for DeviceRepository REST actions
 */
class DeviceResourceSpec extends ResourcePropSpec {

  import Device._

  def isRecent(time: Option[Instant]): Boolean = time match {
    case Some(t) => t.isAfter(Instant.now.minus(3, ChronoUnit.MINUTES))
    case None => false
  }

  property("GET, PUT, DELETE, and POST '/ping' request fails on non-existent device") {
    forAll { (uuid: Uuid, device: DeviceT, json: Json) =>
      fetchDevice(uuid)          ~> route ~> check { status shouldBe NotFound }
      updateDevice(uuid, device) ~> route ~> check { status shouldBe NotFound }
      deleteDevice(uuid)         ~> route ~> check { status shouldBe NotFound }
      updateLastSeen(uuid)       ~> route ~> check { status shouldBe NotFound }
    }
  }

  property("GET request (for Id) after POST yields same device") {
    forAll { devicePre: DeviceT =>
      val uuid: Uuid = createDeviceOk(devicePre)

      fetchDevice(uuid) ~> route ~> check {
        status shouldBe OK
        val devicePost: Device = responseAs[Device]
        devicePost.deviceId shouldBe devicePre.deviceId
        devicePost.deviceType shouldBe devicePre.deviceType
        devicePost.lastSeen shouldBe None
      }

      deleteDeviceOk(uuid)
    }
  }

  property("GET request with ?deviceId after creating yields same device.") {
    forAll { (deviceId: DeviceId, devicePre: DeviceT) =>

      val uuid: Uuid = createDeviceOk(devicePre.copy(deviceId = Some(deviceId)))
      fetchByDeviceId(defaultNs, deviceId) ~> route ~> check {
        status shouldBe OK
        val devicePost1: Device = responseAs[Seq[Device]].head
        fetchDevice(uuid) ~> route ~> check {
          status shouldBe OK
          val devicePost2: Device = responseAs[Device]

          devicePost1 shouldBe devicePost2
        }
      }
      deleteDeviceOk(uuid)
    }
  }

  property("PUT request after POST succeeds with updated device.") {
    forAll(genConflictFreeDeviceTs(2)) { case Seq(d1, d2) =>

      val uuid: Uuid = createDeviceOk(d1)

      updateDevice(uuid, d2) ~> route ~> check {
        val updateStatus = status

        d1.deviceId match {
          case Some(deviceId) =>
            fetchByDeviceId(defaultNs, deviceId) ~> route ~> check {
              status match {
                case OK => responseAs[Seq[Device]].headOption match {
                  case Some(_) => updateStatus shouldBe Conflict
                  case None =>
                    updateStatus shouldBe OK

                    fetchDevice(uuid) ~> route ~> check {
                      status shouldBe OK
                      val devicePost: Device = responseAs[Device]
                      devicePost.uuid shouldBe uuid
                      devicePost.deviceId shouldBe d2.deviceId
                      devicePost.deviceType shouldBe d2.deviceType
                      devicePost.lastSeen shouldBe None
                    }
                }
                case _ => assert(false, "unexpected status code: " + status)
              }
            }
          case None => updateStatus shouldBe OK
        }
      }

      deleteDeviceOk(uuid)
    }
  }

  property("POST request creates a new device.") {
    forAll { devicePre: DeviceT =>

      val uuid: Uuid = createDeviceOk(devicePre)

      fetchDevice(uuid) ~> route ~> check {
        status shouldBe OK
        val devicePost: Device = responseAs[Device]
        devicePost.uuid shouldBe uuid
        devicePost.deviceId shouldBe devicePre.deviceId
        devicePost.deviceType shouldBe devicePre.deviceType
      }

      deleteDeviceOk(uuid)
    }
  }

  property("DELETE request after POST succeds and deletes device.") {
    forAll { (device: DeviceT) =>

      val uuid: Uuid = createDeviceOk(device)

      deleteDevice(uuid) ~> route ~> check {
        status shouldBe OK
      }

      fetchDevice(uuid) ~> route ~> check {
        status shouldBe NotFound
      }
    }
  }

  property("POST request on 'ping' should update 'lastSeen' field for device.") {
    forAll { (uuid: Uuid, devicePre: DeviceT) =>

      val uuid: Uuid = createDeviceOk(devicePre)

      updateLastSeen(uuid) ~> route ~> check {
        status shouldBe OK
      }

      fetchDevice(uuid) ~> route ~> check {
        val devicePost: Device = responseAs[Device]

        devicePost.lastSeen should not be None
        isRecent(devicePost.lastSeen) shouldBe true
      }

      deleteDeviceOk(uuid)
    }
  }

  property("POST request with same deviceName fails with conflict.") {
    forAll(genConflictFreeDeviceTs(2)) { case Seq(d1, d2) =>

      val name = arbitrary[DeviceName].sample.get
      val uuid: Uuid = createDeviceOk(d1.copy(deviceName = name))

      createDevice(d2.copy(deviceName = name)) ~> route ~> check {
        status shouldBe Conflict
      }

      deleteDeviceOk(uuid)
    }
  }

  property("POST request with same deviceId fails with conflict.") {
    forAll(genConflictFreeDeviceTs(2)) { case Seq(d1, d2) =>

      val uuid: Uuid = createDeviceOk(d1)

      createDevice(d2.copy(deviceId = d1.deviceId)) ~> route ~> check {
        d1.deviceId match {
          case Some(deviceId) => status shouldBe Conflict
          case None => deleteDeviceOk(responseAs[Uuid])
        }
      }

      deleteDeviceOk(uuid)
    }
  }

  property("PUT request updates device.") {
    forAll(genConflictFreeDeviceTs(2)) { case Seq(d1: DeviceT, d2: DeviceT) =>

      val uuid: Uuid = createDeviceOk(d1)

      updateDevice(uuid, d2) ~> route ~> check {
        status shouldBe OK
        fetchDevice(uuid) ~> route ~> check {
          status shouldBe OK
          val updatedDevice: Device = responseAs[Device]
          updatedDevice.deviceId shouldBe d2.deviceId
          updatedDevice.deviceType shouldBe d2.deviceType
          updatedDevice.lastSeen shouldBe None
        }
      }

      deleteDeviceOk(uuid)
    }
  }

  property("PUT request does not update last seen") {
    forAll(genConflictFreeDeviceTs(2)) { case Seq(d1: DeviceT, d2: DeviceT) =>

      val uuid: Uuid = createDeviceOk(d1)

      updateLastSeen(uuid) ~> route ~> check {
        status shouldBe OK
      }

      updateDevice(uuid, d2) ~> route ~> check {
        status shouldBe OK
        fetchDevice(uuid) ~> route ~> check {
          status shouldBe OK
          val updatedDevice: Device = responseAs[Device]
          updatedDevice.lastSeen shouldBe defined
        }
      }

      deleteDeviceOk(uuid)
    }
  }

  property("PUT request with same deviceName fails with conflict.") {
    forAll(genConflictFreeDeviceTs(2)) { case Seq(d1, d2) =>

      val uuid1: Uuid = createDeviceOk(d1)
      val uuid2: Uuid = createDeviceOk(d2)

      updateDevice(uuid1, d1.copy(deviceName = d2.deviceName)) ~> route ~> check {
        status shouldBe Conflict
      }

      deleteDeviceOk(uuid1)
      deleteDeviceOk(uuid2)
    }
  }

  property("PUT request with same deviceId fails with conflict.") {
    forAll(genConflictFreeDeviceTs(2)) { case Seq(d1, d2) =>

      val deviceId = arbitrary[DeviceId].suchThat(_ != d1.deviceId).sample.get
      val uuid1: Uuid = createDeviceOk(d1)
      val uuid2: Uuid = createDeviceOk(d2.copy(deviceId = Some(deviceId)))

      updateDevice(uuid1, d1.copy(deviceId = Some(deviceId))) ~> route ~> check {
        d2.deviceId match {
          case Some(_) => status shouldBe Conflict
          case None => ()
        }
      }

      deleteDeviceOk(uuid1)
      deleteDeviceOk(uuid2)
    }
  }


}
