/**
 * Copyright: Copyright (C) 2015, Jaguar Land Rover
 * License: MPL-2.0
 */
package org.genivi.sota.datatype

import eu.timepit.refined.{Predicate, Refined}
import org.scalacheck.{Arbitrary, Gen}


trait VehicleCommon {

  trait ValidVin

  implicit val validVin : Predicate[ValidVin, String] = Predicate.instance(
    vin => vin.length == 17
        && vin.forall(c => (c.isUpper  || c.isDigit)
                        && (c.isLetter || c.isDigit)
                        && !List('I', 'O', 'Q').contains(c)),
    vin => s"($vin must be 17 letters or digits long and not contain 'I', 'O', or 'Q')"
  )

  type Vin = Refined[String, ValidVin]

  implicit val VinOrdering: Ordering[Vin] = new Ordering[Vin] {
    override def compare(v1: Vin, v2: Vin): Int = v1.get compare v2.get
  }

  val genVinChar: Gen[Char] =
    Gen.oneOf('A' to 'Z' diff List('I', 'O', 'Q'))

  val genVin: Gen[Vin] =
    Gen.listOfN(17, genVinChar).map(cs => Refined(cs.mkString))

  implicit lazy val arbVin: Arbitrary[Vin] =
    Arbitrary(genVin)

  val genInvalidVin: Gen[Vin] = {

    val genTooLongVin: Gen[String] = for {
      n  <- Gen.choose(18, 100)
      cs <- Gen.listOfN(n, genVinChar)
    } yield cs.mkString

    val genTooShortVin: Gen[String] = for {
      n  <- Gen.choose(1, 16)
      cs <- Gen.listOfN(n, genVinChar)
    } yield cs.mkString

    val genNotAlphaNumVin: Gen[String] =
      Gen.listOfN(17, Arbitrary.arbitrary[Char]).
        suchThat(_.exists(c => !(c.isLetter || c.isDigit))).flatMap(_.mkString)

    Gen.oneOf(genTooLongVin, genTooShortVin, genNotAlphaNumVin)
       .map(Refined(_))
  }

}
