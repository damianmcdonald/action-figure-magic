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

class ParameterizedHttpErrorByFormDataServiceSpec extends Specification with Specs2RouteTest with RestMagicApi {

  def actorRefFactory = system

  private val rootApiPath = "restmagic"

  "The ParameterizedHttpErrorService created via ParameterizedHttpErrorByFormDataTestApi" should {
    "return a 400 Bad Request Error for a POST request to path /" + rootApiPath + "/examples/parameterizedhttp/error/formdata/post" in {
      val data = Map("errorId" -> "2")
      Post("/" + rootApiPath + "/examples/parameterizedhttp/error/formdata/post", FormData(data)) ~> routes ~> check {
        status mustEqual StatusCodes.BandwidthLimitExceeded
        val response = responseAs[String]
        response must not be empty
        response mustEqual "RestMagic BandwidthLimitExceeded error"
      }
    }
  }

}
