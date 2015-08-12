package com.github.damianmcdonald.actionfiguremagic

import java.nio.file.{ Path, Paths }

import com.github.damianmcdonald.restmagic.api.RestMagicApi
import net.liftweb.json._
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class ToyLinesApiSpec extends Specification with Specs2RouteTest with RestMagicApi {

  implicit val formats = net.liftweb.json.DefaultFormats

  private val sampleJson = """{ "data": "sample" }"""

  "The SimpleRestService created via ToyLinesApi" should {
    "return a json object for a GET request to path /action-figure-magic/toylines" in {
      Get("/action-figure-magic/toylines") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val xs = for { JObject(x) <- (json \ "toylines") } yield x
        xs must have size (6)
        val starwars = {
          for {
            JObject(toyline) <- json
            JField("name", JString(name)) <- toyline
            JField("manufacturer", JString(manufacturer)) <- toyline
            JField("id", JInt(id)) <- toyline
            JField("image", JString(image)) <- toyline
            if name.equals("Star Wars")
          } yield (name, manufacturer, id, image)
        }
        starwars(0)._1 mustEqual "Star Wars"
        starwars(0)._2 mustEqual "Kenner"
        starwars(0)._3 mustEqual 1
        starwars(0)._4 mustEqual "assets/images/toylines/starwars-cover.jpg"
        val mask = {
          for {
            JObject(toyline) <- json
            JField("name", JString(name)) <- toyline
            JField("manufacturer", JString(manufacturer)) <- toyline
            JField("id", JInt(id)) <- toyline
            JField("image", JString(image)) <- toyline
            if name.equals("M.A.S.K.")
          } yield (name, manufacturer, id, image)
        }
        mask(0)._1 mustEqual "M.A.S.K."
        mask(0)._2 mustEqual "Kenner"
        mask(0)._3 mustEqual 4
        mask(0)._4 mustEqual "assets/images/toylines/mask-cover.jpg"
      }
    }
  }

  "The SimpleRestService created via ToyLinesApi" should {
    "return a json object for a PUT request to path /action-figure-magic/toylines/5" in {
      Put("/action-figure-magic/toylines/5").withEntity(HttpEntity(MediaTypes.`application/json`, sampleJson)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "modified"
      }
    }
  }

  "The SimpleRestService created via ToyLinesApi" should {
    "return a json object for a DELETE request to path /action-figure-magic/toylines/5" in {
      Delete("/action-figure-magic/toylines/5") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "deleted"
      }
    }
  }

  "The SimpleRestService created via ToyLinesApi" should {
    "accept a multi-part form data upload containing a binary file" in {
      val resourceUrl = this.getClass().getResource("/uploads/upload-test.jpg")
      val resourcePath: Path = Paths.get(resourceUrl.toURI());
      val payload = MultipartFormData(Seq(BodyPart(resourcePath.toFile, "cover", MediaTypes.`image/jpeg`)))
      Post("/action-figure-magic/toylines", payload) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "added/modified"
      }
    }
  }

  "The SimpleRestService created via ToyLinesApi" should {
    "return a star wars binary pdf file as attachment" in {
      Get("/action-figure-magic/toylines/download/1") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

  "The SimpleRestService created via ToyLinesApi" should {
    "return a masters of the universe binary pdf file as attachment" in {
      Get("/action-figure-magic/toylines/download/2") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

  "The SimpleRestService created via ToyLinesApi" should {
    "return a transformers binary pdf file as attachment" in {
      Get("/action-figure-magic/toylines/download/3") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

  "The SimpleRestService created via ToyLinesApi" should {
    "return a mask binary pdf file as attachment" in {
      Get("/action-figure-magic/toylines/download/4") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

  "The SimpleRestService created via ToyLinesApi" should {
    "return a gi-joe binary pdf file as attachment" in {
      Get("/action-figure-magic/toylines/download/5") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

  "The SimpleRestService created via ToyLinesApi" should {
    "return a teenage mutant ninja turtles binary pdf file as attachment" in {
      Get("/action-figure-magic/toylines/download/6") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

}