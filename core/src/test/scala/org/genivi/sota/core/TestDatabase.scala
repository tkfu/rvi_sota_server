/**
  * Copyright: Copyright (C) 2016, Jaguar Land Rover
  * License: MPL-2.0
  */
package org.genivi.sota.core

import akka.http.scaladsl.util.FastFuture
import com.typesafe.config.{Config, ConfigFactory}
import org.genivi.sota.common.DeviceRegistry
import org.genivi.sota.core.data.{Package, UpdateRequest, UpdateSpec}
import org.genivi.sota.core.db.{Packages, UpdateRequests, UpdateSpecs}
import org.genivi.sota.data._
import org.genivi.sota.http.NamespaceDirectives

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import slick.driver.MySQLDriver.api._

object NamespaceSpec {
  import eu.timepit.refined.auto._
  import eu.timepit.refined.string._

  lazy val defaultNamespace: Namespace = {
    val config = ConfigFactory.load()
    NamespaceDirectives.configNamespace(config)
  }
}


trait UpdateResourcesDatabaseSpec {
  self: DatabaseSpec =>

  import Generators._
  import DeviceGenerators._

  def createUpdateSpecFor(device: Uuid, installPos: Int = 0, withMillis: Long = -1)
                         (implicit ec: ExecutionContext): DBIO[(Package, UpdateSpec)] = {
    val (packageModel, updateSpec0) = genUpdateSpecFor(device, withMillis).sample.get
    val updateSpec = updateSpec0.copy(installPos = installPos)

    val dbIO = DBIO.seq(
      Packages.create(packageModel),
      UpdateRequests.persist(updateSpec.request),
      UpdateSpecs.persist(updateSpec)
    )

    dbIO.map(_ => (packageModel, updateSpec))
  }

  def createUpdateSpecAction()(implicit ec: ExecutionContext): DBIO[(Package, Device, UpdateSpec)] = {
    val device = genDevice.sample.get

    for {
      (packageModel, updateSpec) <- createUpdateSpecFor(device.uuid)
    } yield (packageModel, device, updateSpec)
  }

  def createUpdateSpec()(implicit ec: ExecutionContext): Future[(Package, Device, UpdateSpec)] = {
    db.run(createUpdateSpecAction())
  }

}
