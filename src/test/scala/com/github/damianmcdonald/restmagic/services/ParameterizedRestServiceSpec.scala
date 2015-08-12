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

import com.github.damianmcdonald.restmagic.api.RestMagicApi
import net.liftweb.json._
import org.specs2.mutable.Specification
import spray.http.{ HttpEntity, MediaTypes, StatusCodes }
import spray.testkit.Specs2RouteTest

class ParameterizedRestServiceSpec extends Specification with Specs2RouteTest with RestMagicApi {

  def actorRefFactory = system

  private val rootApiPath = "restmagic"

  private val sampleJson = """{ "data": "sample" }"""

  implicit val formats = net.liftweb.json.DefaultFormats

  "The ParameterizedRestService created via ParameterizedRestTestApi" should {
    "return a json object for a GET request to path /" + rootApiPath + "/examples/parameterizedrest/helloworld/json/3" in {
      Get("/" + rootApiPath + "/examples/parameterizedrest/helloworld/json/turkish") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Merhaba Dunya"
      }
    }
  }

  "The ParameterizedRestService created via ParameterizedRestTestApi" should {
    "return a json object from file for a GET request to path /" + rootApiPath + "/examples/parameterizedrest/motu/json/file/2" in {
      Get("/" + rootApiPath + "/examples/parameterizedrest/motu/json/file/2") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "name").extract[String]
        value mustEqual "Skeletor"
      }
    }
  }

  "The ParameterizedRestService created via ParameterizedRestTestApi" should {
    "return a json object for a GET request to path /" + rootApiPath + "/examples/parameterizedrest/customstrategy/1236" in {
      Get("/" + rootApiPath + "/examples/parameterizedrest/customstrategy/1236") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Salve Mondo"
      }
    }
  }

  "The ParameterizedRestService created via ParameterizedRestTestApi" should {
    "return a json object for a GET request to path /" + rootApiPath + "/examples/parameterizedrest/matchany/any/sample/path" in {
      Get("/" + rootApiPath + "/examples/parameterizedrest/matchany/any/sample/path") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        val xs = List("Hello World", "Merhaba Dunya", "Salve Mondo", "Hola Mundo", "Halo Welt")
        xs must contain(value)
      }
    }
  }

  "The ParameterizedRestService created via ParameterizedRestTestApi" should {
    "return a json object for a GET request to path /" + rootApiPath + "/examples/parameterizedrest/matchpath/asparam/turkish/hello/world/response" in {
      Get("/" + rootApiPath + "/examples/parameterizedrest/matchpath/asparam/turkish/hello/world/response") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Merhaba Dunya"
      }
    }
  }

  "The ParameterizedRestService created via ParameterizedRestTestApi" should {
    "return a json object for a POST request to path /" + rootApiPath + "/examples/parameterizedrest/post/2" in {
      Post("/" + rootApiPath + "/examples/parameterizedrest/post/2").withEntity(HttpEntity(MediaTypes.`application/json`, sampleJson)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Merhaba Dunya"
      }
    }
  }

  "The ParameterizedRestService created via ParameterizedRestTestApi" should {
    "return a json object for a PUT request to path /" + rootApiPath + "/examples/parameterizedrest/put/2" in {
      Put("/" + rootApiPath + "/examples/parameterizedrest/put/2").withEntity(HttpEntity(MediaTypes.`application/json`, sampleJson)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Merhaba Dunya"
      }
    }
  }

  "The ParameterizedRestService created via ParameterizedRestTestApi" should {
    "return a json object for a DELETE request to path /" + rootApiPath + "/examples/parameterizedrest/delete/2" in {
      Delete("/" + rootApiPath + "/examples/parameterizedrest/delete/2") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Merhaba Dunya"
      }
    }
  }

}
