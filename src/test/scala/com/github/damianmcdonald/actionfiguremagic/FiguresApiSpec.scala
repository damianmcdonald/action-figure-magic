package com.github.damianmcdonald.actionfiguremagic

import com.github.damianmcdonald.restmagic.api.RestMagicApi
import net.liftweb.json._
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class FiguresApiSpec extends Specification with Specs2RouteTest with RestMagicApi {

  "The SimpleRestService created via FiguresApi" should {
    "return a json object for a GET request to path /action-figure-magic/figures" in {
      Get("/action-figure-magic/figures") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val xs = for { JObject(x) <- (json \ "figures") } yield x
        xs must have size (144)
        val leonardo = {
          for {
            JObject(figure) <- json
            JField("name", JString(name)) <- figure
            JField("id", JInt(id)) <- figure
            if name.equals("Leonardo")
          } yield (name, id)
        }
        leonardo(0)._1 mustEqual "Leonardo"
        leonardo(0)._2 mustEqual 121
        val he_man = {
          for {
            JObject(figure) <- json
            JField("name", JString(name)) <- figure
            JField("toylineId", JInt(toylineId)) <- figure
            JField("isHero", JBool(isHero)) <- figure
            if name.equals("He-Man")
          } yield (name, toylineId, isHero)
        }
        he_man(0)._1 mustEqual "He-Man"
        he_man(0)._2 mustEqual 2
        he_man(0)._3 mustEqual true
      }
    }
  }

}