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
import spray.http.StatusCodes
import spray.http.HttpEntity
import spray.http.MediaTypes

class ParameterizedRestErrorServiceSpec extends Specification with Specs2RouteTest with RestMagicApi {

  def actorRefFactory = system

  private val rootApiPath = "restmagic"

  "The ParameterizedRestErrorService created via ParameterizedRestErrorTestApi" should {
    "return a 400 Bad Request Error for a GET request to path /" + rootApiPath + "/examples/parameterizedrest/error/1" in {
      Get("/" + rootApiPath + "/examples/parameterizedrest/error/1") ~> routes ~> check {
        status mustEqual StatusCodes.BadRequest
        val response = responseAs[String]
        response must not be empty
        response mustEqual "RestMagic BadRequest error"
      }
    }
  }

}
