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
import spray.http._
import spray.testkit.Specs2RouteTest

class ParameterizedHttpByQueryStringServiceSpec extends Specification with Specs2RouteTest with RestMagicApi {

  def actorRefFactory = system

  private val rootApiPath = "restmagic"

  implicit val formats = net.liftweb.json.DefaultFormats

  "The ParameterizedHttpService created via ParameterizedHtpByQueryStringTestApi" should {
    "return a json object for a GET request to path /" + rootApiPath + "/examples/parameterizedhttp/querystring?lang=turkish" in {
      Get("/" + rootApiPath + "/examples/parameterizedhttp/querystring?lang=turkish") ~> routes ~> check {
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
    "return a json object for a GET request to path /" + rootApiPath + "/examples/parameterizedhttp/querystring/customstrategy?id=1309" in {
      Get("/" + rootApiPath + "/examples/parameterizedhttp/querystring/customstrategy?id=1309") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "response").extract[String]
        value mustEqual "Salve Mondo"
      }
    }
  }

}
