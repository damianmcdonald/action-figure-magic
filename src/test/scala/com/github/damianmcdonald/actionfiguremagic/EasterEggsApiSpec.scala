package com.github.damianmcdonald.actionfiguremagic

import com.github.damianmcdonald.restmagic.api.RestMagicApi
import net.liftweb.json._
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class EasterEggsApiSpec extends Specification with Specs2RouteTest with RestMagicApi {

  "The SimpleRestService created via EasterEggsApi" should {
    "return a json object for a GET request to path /action-figure-magic/eastereggs" in {
      Get("/action-figure-magic/eastereggs") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val xs = for { JObject(x) <- (json \ "toylines") } yield x
        xs must have size (6)
        val voltron = {
          for {
            JObject(toyline) <- json
            JField("name", JString(name)) <- toyline
            JField("manufacturer", JString(manufacturer)) <- toyline
            JField("id", JInt(id)) <- toyline
            JField("image", JString(image)) <- toyline
            if name.equals("Voltron")
          } yield (name, manufacturer, id, image)
        }
        voltron(0)._1 mustEqual "Voltron"
        voltron(0)._2 mustEqual "Panosh Place"
        voltron(0)._3 mustEqual 7
        voltron(0)._4 mustEqual "assets/images/eastereggs/voltron-cover.jpg"
        val centurions = {
          for {
            JObject(toyline) <- json
            JField("name", JString(name)) <- toyline
            JField("manufacturer", JString(manufacturer)) <- toyline
            JField("id", JInt(id)) <- toyline
            JField("image", JString(image)) <- toyline
            if name.equals("Centurions")
          } yield (name, manufacturer, id, image)
        }
        centurions(0)._1 mustEqual "Centurions"
        centurions(0)._2 mustEqual "Kenner"
        centurions(0)._3 mustEqual 10
        centurions(0)._4 mustEqual "assets/images/eastereggs/centurions-cover.jpg"
      }
    }
  }

}