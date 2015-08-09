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

package com.github.damianmcdonald.restmagic.examples

import com.github.damianmcdonald.restmagic.api.RestMagicApi
import net.liftweb.json._
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class ParameterizedHttpByFormDataServiceSpec extends Specification with Specs2RouteTest with RestMagicApi {

  def actorRefFactory = system

  private val rootApiPath = "restmagic"

  implicit val formats = net.liftweb.json.DefaultFormats

  "The ParameterizedHttpService created via ParameterizedHttpByFormDataTestApi" should {
    "return a json object for a POST request to path /" + rootApiPath + "/examples/parameterizedhttp/post" in {
      val data = Map("lang" -> "2")
      Post("/" + rootApiPath + "/examples/parameterizedhttp/post", FormData(data)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Merhaba Dunya"
      }
    }
  }

  "The ParameterizedHttpService created via ParameterizedHttpByFormDataTestApi" should {
    "return a json object for a PUT request to path /" + rootApiPath + "/examples/parameterizedhttp/put" in {
      val data = Map("lang" -> "2")
      Put("/" + rootApiPath + "/examples/parameterizedhttp/put", FormData(data)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Merhaba Dunya"
      }
    }
  }

  "The ParameterizedHttpService created via ParameterizedHttpByFormDataTestApi" should {
    "return a json object for a DELETE request to path /" + rootApiPath + "/examples/parameterizedhttp/delete" in {
      val data = Map("lang" -> "2")
      Delete("/" + rootApiPath + "/examples/parameterizedhttp/delete", FormData(data)) ~> routes ~> check {
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
