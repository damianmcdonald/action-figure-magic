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

  private val strategy = (param: String, data: Map[String, String]) => {
    if (param.toInt > 0 && param.toInt < 500) {
      data.getOrElse("1", throw new RuntimeException("Unable to find map entry for key 1"))
    } else if (param.toInt > 501 && param.toInt < 1000) {
      data.getOrElse("2", throw new RuntimeException("Unable to find map entry for key 2"))
    } else if (param.toInt > 1001 && param.toInt < 1500) {
      data.getOrElse("3", throw new RuntimeException("Unable to find map entry for key 3"))
    } else if (param.toInt > 1501 && param.toInt < 2000) {
      data.getOrElse("4", throw new RuntimeException("Unable to find map entry for key 4"))
    } else {
      data.getOrElse("5", throw new RuntimeException("Unable to find map entry for key 5"))
    }
  }

  implicit val formats = net.liftweb.json.DefaultFormats

  "The ParameterizedHttpService created via ParameterizedHttpByFormDataTestApi" should {
    "return a json object for a POST request to path /" + rootApiPath + "/examples/parameterizedhttp/formdata" in {
      val data = Map("lang" -> "2")
      Post("/" + rootApiPath + "/examples/parameterizedhttp/formdata", FormData(data)) ~> routes ~> check {
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
    "return a json object for a POST request to path /" + rootApiPath + "/examples/parameterizedhttp/formdata/customstrategy" in {
      val data = Map("id" -> "52435")
      Post("/" + rootApiPath + "/examples/parameterizedhttp/formdata/customstrategy", FormData(data)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Halo Welt"
      }
    }
  }

  "The ParameterizedHttpService created via ParameterizedHttpByFormDataTestApi" should {
    "return a json object for a PUT request to path /" + rootApiPath + "/examples/parameterizedhttp/formdata" in {
      val data = Map("lang" -> "2")
      Put("/" + rootApiPath + "/examples/parameterizedhttp/formdata", FormData(data)) ~> routes ~> check {
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
    "return a json object for a DELETE request to path /" + rootApiPath + "/examples/parameterizedhttp/formdata" in {
      val data = Map("lang" -> "2")
      Delete("/" + rootApiPath + "/examples/parameterizedhttp/formdata", FormData(data)) ~> routes ~> check {
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
