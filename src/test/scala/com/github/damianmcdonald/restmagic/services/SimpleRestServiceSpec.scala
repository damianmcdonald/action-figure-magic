/*
 * Copyright 2015 Damian McDonald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.damianmcdonald.restmagic.services

import org.specs2.mutable.Specification
import com.github.damianmcdonald.restmagic.api.RestMagicApi
import net.liftweb.json._
import spray.testkit.Specs2RouteTest
import spray.http._
import java.io.File

class SimpleRestServiceSpec extends Specification with Specs2RouteTest with RestMagicApi {

  def actorRefFactory = system

  private val rootApiPath = "restmagic"

  private val sampleJson = """{ "data": "sample" }"""

  implicit val formats = net.liftweb.json.DefaultFormats

  "The SimpleRestService created via SimpleRestTestApi" should {
    "return a json object for a GET request to path /" + rootApiPath + "/examples/simplerest/helloworld/json" in {
      Get("/" + rootApiPath + "/examples/simplerest/helloworld/json") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Hello World"
      }
    }
  }

  "The SimpleRestService created via SimpleRestTestApi" should {
    "return a xml object for a GET request to path /" + rootApiPath + "/examples/simplerest/helloworld/xml" in {
      Get("/" + rootApiPath + "/examples/simplerest/helloworld/xml") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val xml = scala.xml.XML.loadString(response)
        val value = (xml \ "response").text
        value mustEqual "Hello World"
      }
    }
  }

  "The SimpleRestService created via SimpleRestTestApi" should {
    "return a html object for a GET request to path /" + rootApiPath + "/examples/simplerest/helloworld/html" in {
      Get("/" + rootApiPath + "/examples/simplerest/helloworld/html") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val html = scala.xml.XML.loadString(response)
        val value = (html \\ "h1").text
        value mustEqual "Hello World"
      }
    }
  }

  "The SimpleRestService created via SimpleRestTestApi" should {
    "return a json object from a file for a GET request to path /" + rootApiPath + "/examples/simplerest/helloworld/file" in {
      Get("/" + rootApiPath + "/examples/simplerest/helloworld/file") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Hello World"
      }
    }
  }

  "The SimpleRestService created via SimpleRestTestApi" should {
    "return a json object for a POST request to path /" + rootApiPath + "/examples/simplerest/post" in {
      Post("/" + rootApiPath + "/examples/simplerest/post").withEntity(HttpEntity(MediaTypes.`application/json`, sampleJson)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "success"
      }
    }
  }

  "The SimpleRestService created via SimpleRestTestApi" should {
    "return a json object for a PUT request to path /" + rootApiPath + "/examples/simplerest/put" in {
      Put("/" + rootApiPath + "/examples/simplerest/put").withEntity(HttpEntity(MediaTypes.`application/json`, sampleJson)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "success"
      }
    }
  }

  "The SimpleRestService created via SimpleRestTestApi" should {
    "return a json object for a DELETE request to path /" + rootApiPath + "/examples/simplerest/delete" in {
      Delete("/" + rootApiPath + "/examples/simplerest/delete") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "success"
      }
    }
  }

}
