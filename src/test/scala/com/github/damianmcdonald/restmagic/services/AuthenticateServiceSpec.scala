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
import spray.http.HttpHeaders.Authorization
import spray.http._
import spray.testkit.Specs2RouteTest

class AuthenticateServiceSpec extends Specification with Specs2RouteTest with RestMagicApi {

  def actorRefFactory = system

  private val rootApiPath = "restmagic"

  implicit val formats = net.liftweb.json.DefaultFormats

  "The AuthenicateService created via AuthenticateTestApi" should {
    "authorize valid credentials" in {
      val validCredentials = BasicHttpCredentials("luke", "12345")
      Post("/" + rootApiPath + "/examples/logon/authorize").withHeaders(Authorization(validCredentials)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "authorized"
      }
    }
  }

  "The AuthenicateService created via AuthenticateTestApi" should {
    "reject invalid credentials" in {
      val invalidCredentials = BasicHttpCredentials("chewy", "zxcvb")
      Post("/" + rootApiPath + "/examples/logon/authorize").withHeaders(Authorization(invalidCredentials)) ~> routes ~> check {
        status mustEqual StatusCodes.Forbidden
        val response = responseAs[String]
        response must not be empty
        response === "Authorization failure"
      }
    }
  }

  "The AuthenicateService created via AuthenticateTestApi" should {
    "authenticate valid credentials" in {
      val validCredentials = BasicHttpCredentials("han", "qwerty")
      Post("/" + rootApiPath + "/examples/logon/authenticate").withHeaders(Authorization(validCredentials)) ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        val json = parse(response)
        val value = (json \ "status").extract[String]
        value mustEqual "authenticated"
      }
    }
  }

  "The AuthenicateService created via AuthenticateTestApi" should {
    "not authenticate invalid credentials" in {
      val validCredentials = BasicHttpCredentials("bad", "creds")
      Post("/" + rootApiPath + "/examples/logon/authenticate").withHeaders(Authorization(validCredentials)) ~> routes ~> check {
        status mustEqual StatusCodes.Unauthorized
        val response = responseAs[String]
        response must not be empty
        response === "Authentication failure"
      }
    }
  }

}
