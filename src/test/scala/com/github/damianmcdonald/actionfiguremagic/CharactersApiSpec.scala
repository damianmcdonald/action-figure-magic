package com.github.damianmcdonald.actionfiguremagic

import java.nio.file.{ Path, Paths }

import com.github.damianmcdonald.restmagic.api.RestMagicApi
import net.liftweb.json._
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class CharactersApiSpec extends Specification with Specs2RouteTest with RestMagicApi {

  implicit val formats = net.liftweb.json.DefaultFormats

  private val sampleJson = """{ "data": "sample" }"""

  "The SimpleRestService created via CharactersApi" should {
    "return a json object for a GET request to path /action-figure-magic/characters/1" in {
      Get("/action-figure-magic/characters/1") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val heroesName = (json \ "factions" \ "heroes" \ "name").extract[String]
        heroesName mustEqual "Rebels"
        val xs = for { JObject(x) <- (json \ "characters") } yield x
        xs must have size (24)
        val yoda = {
          for {
            JObject(character) <- json
            JField("name", JString(name)) <- character
            JField("isHero", JBool(isHero)) <- character
            JField("id", JInt(id)) <- character
            JField("image", JString(image)) <- character
            if name.equals("Yoda")
          } yield (name, isHero, id, image)
        }
        yoda(0)._1 mustEqual "Yoda"
        yoda(0)._2 mustEqual true
        yoda(0)._3 mustEqual 11
        yoda(0)._4 mustEqual "assets/images/characters/starwars/yoda.jpg"
        val vader = {
          for {
            JObject(character) <- json
            JField("name", JString(name)) <- character
            JField("isHero", JBool(isHero)) <- character
            JField("id", JInt(id)) <- character
            JField("image", JString(image)) <- character
            if name.equals("Darth Vader")
          } yield (name, isHero, id, image)
        }
        vader(0)._1 mustEqual "Darth Vader"
        vader(0)._2 mustEqual false
        vader(0)._3 mustEqual 3
        vader(0)._4 mustEqual "assets/images/characters/starwars/darth-vader.jpg"
      }
    }
  }

  "The SimpleRestService created via CharactersApi" should {
    "return a json object for a PUT request to path /action-figure-magic/characters/65" in {
      Put("/action-figure-magic/characters/65").withEntity(HttpEntity(MediaTypes.`application/json`, sampleJson)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "modified"
      }
    }
  }

  "The SimpleRestService created via CharactersApi" should {
    "return a json object for a DELETE request to path /action-figure-magic/CharactersApi/44" in {
      Delete("/action-figure-magic/characters/44") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "deleted"
      }
    }
  }

  "The SimpleRestService created via CharactersApi" should {
    "accept a multi-part form data upload containing a binary file" in {
      val resourceUrl = this.getClass().getResource("/uploads/upload-test.jpg")
      val resourcePath: Path = Paths.get(resourceUrl.toURI());
      val payload = MultipartFormData(Seq(BodyPart(resourcePath.toFile, "cover", MediaTypes.`image/jpeg`)))
      Post("/action-figure-magic/characters", payload) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "added/modified"
      }
    }
  }

  "The SimpleRestErrorService created via CharactersApi" should {
    "return a Server Error for a PUT request to path /action-figure-magic/characters/error/15" in {
      Put("/action-figure-magic/characters/error/15") ~> routes ~> check {
        status mustNotEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

  "The SimpleRestErrorService created via CharactersApi" should {
    "return a Server Error for a PUT request to path /action-figure-magic/characters/error/137" in {
      Delete("/action-figure-magic/characters/error/137") ~> routes ~> check {
        status mustNotEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

  "The FileUploadErrorService created via CharactersApi" should {
    "reject a multi-part form data upload containing a binary file" in {
      val resourceUrl = this.getClass().getResource("/uploads/upload-test.jpg")
      val resourcePath: Path = Paths.get(resourceUrl.toURI());
      val payload = MultipartFormData(Seq(BodyPart(resourcePath.toFile, "cover", MediaTypes.`image/jpeg`)))
      Post("/action-figure-magic/characters/error", payload) ~> routes ~> check {
        status mustNotEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

}