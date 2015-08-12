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

package com.github.damianmcdonald.restmagic.configurators

import org.specs2.mutable.Specification
import spray.http.HttpMethods._
import spray.http.MediaTypes._

class AuthenticateConfigSpec extends Specification {

  private val jsonAuthenticateResponse = """{ "status": "authenticated" }"""
  private val jsonAuthorizeResponse = """{ "status": "authorized" }"""

  "An AuthentiateConfig" should {
    "return a valid AuthentiateConfig class instance using a minimal constructor" in {
      val authConfig = AuthenticateConfig(
        httpMethod = POST,
        securePathPrefix = "examples",
        authenticatePath = "authenticate",
        authorizePath = "authorize",
        credentials = Map("luke" -> "12345", "han" -> "qwerty", "chewy" -> "zxcvb", "yoda" -> "654321"),
        authorizedUsers = List("luke", "yoda")
      )
      authConfig must not be null
    }
  }

  "An AuthentiateConfig" should {
    "return a valid AuthentiateConfig class instance using a partial constructor" in {
      val authConfig = AuthenticateConfig(
        httpMethod = POST,
        securePathPrefix = "examples",
        authenticatePath = "authenticate",
        authorizePath = "authorize",
        credentials = Map("luke" -> "12345", "han" -> "qwerty", "chewy" -> "zxcvb", "yoda" -> "654321"),
        authorizedUsers = List("luke", "yoda"),
        produces = `application/json`,
        authenticateResponseData = jsonAuthenticateResponse,
        authorizeResponseData = jsonAuthorizeResponse
      )
      authConfig must not be null
    }
  }

  "An AuthentiateConfig" should {
    "return a valid AuthentiateConfig class instance using a full constructor" in {
      val authConfig = AuthenticateConfig(
        httpMethod = POST,
        securePathPrefix = "examples",
        authenticatePath = "authenticate",
        authorizePath = "authorize",
        credentials = Map("luke" -> "12345", "han" -> "qwerty", "chewy" -> "zxcvb", "yoda" -> "654321"),
        authorizedUsers = List("luke", "yoda"),
        produces = `application/json`,
        authenticateResponseData = jsonAuthenticateResponse,
        authorizeResponseData = jsonAuthorizeResponse,
        displayName = "Authenticate Post example",
        displayUrl = "/examples/logon"
      )
      authConfig must not be null
    }
  }

  "An AuthentiateConfig" should {
    "throw an assertion error" in {
      "when an AuthentiateConfig minimal constructor contains empty credentials" in {
        def authConfig = AuthenticateConfig(
          httpMethod = POST,
          securePathPrefix = "examples",
          authenticatePath = "authenticate",
          authorizePath = "authorize",
          credentials = Map[String, String](),
          authorizedUsers = List("luke", "yoda")
        )
        authConfig must throwA[AssertionError]
      }
    }
  }

  "An AuthentiateConfig" should {
    "throw an assertion error" in {
      "when an AuthentiateConfig minimal constructor contains empty authorizedUsers" in {
        def authConfig = AuthenticateConfig(
          httpMethod = POST,
          securePathPrefix = "examples",
          authenticatePath = "authenticate",
          authorizePath = "authorize",
          credentials = Map("luke" -> "12345", "han" -> "qwerty", "chewy" -> "zxcvb", "yoda" -> "654321"),
          authorizedUsers = List[String]()
        )
        authConfig must throwA[AssertionError]
      }
    }
  }

  "An AuthentiateConfig" should {
    "throw an assertion error" in {
      "when an AuthentiateConfig partial constructor contains empty credentials" in {
        def authConfig = AuthenticateConfig(
          httpMethod = POST,
          securePathPrefix = "examples",
          authenticatePath = "authenticate",
          authorizePath = "authorize",
          credentials = Map[String, String](),
          authorizedUsers = List("luke", "yoda"),
          produces = `application/json`,
          authenticateResponseData = jsonAuthenticateResponse,
          authorizeResponseData = jsonAuthorizeResponse
        )
        authConfig must throwA[AssertionError]
      }
    }
  }

  "An AuthentiateConfig" should {
    "throw an assertion error" in {
      "when an AuthentiateConfig partial constructor contains empty authorizedUsers" in {
        def authConfig = AuthenticateConfig(
          httpMethod = POST,
          securePathPrefix = "examples",
          authenticatePath = "authenticate",
          authorizePath = "authorize",
          credentials = Map("luke" -> "12345", "han" -> "qwerty", "chewy" -> "zxcvb", "yoda" -> "654321"),
          authorizedUsers = List[String](),
          produces = `application/json`,
          authenticateResponseData = jsonAuthenticateResponse,
          authorizeResponseData = jsonAuthorizeResponse
        )
        authConfig must throwA[AssertionError]
      }
    }
  }

  "An AuthentiateConfig" should {
    "throw an assertion error" in {
      "when an AuthentiateConfig full constructor contains empty credentials" in {
        def authConfig = AuthenticateConfig(
          httpMethod = POST,
          securePathPrefix = "examples",
          authenticatePath = "authenticate",
          authorizePath = "authorize",
          credentials = Map[String, String](),
          authorizedUsers = List("luke", "yoda"),
          produces = `application/json`,
          authenticateResponseData = jsonAuthenticateResponse,
          authorizeResponseData = jsonAuthorizeResponse,
          displayName = "Authenticate Post example",
          displayUrl = "/examples/logon"
        )
        authConfig must throwA[AssertionError]
      }
    }
  }

  "An AuthentiateConfig" should {
    "throw an assertion error" in {
      "when an AuthentiateConfig full constructor contains empty authorizedUsers" in {
        def authConfig = AuthenticateConfig(
          httpMethod = POST,
          securePathPrefix = "examples",
          authenticatePath = "authenticate",
          authorizePath = "authorize",
          credentials = Map("luke" -> "12345", "han" -> "qwerty", "chewy" -> "zxcvb", "yoda" -> "654321"),
          authorizedUsers = List[String](),
          produces = `application/json`,
          authenticateResponseData = jsonAuthenticateResponse,
          authorizeResponseData = jsonAuthorizeResponse,
          displayName = "Authenticate Post example",
          displayUrl = "/examples/logon"
        )
        authConfig must throwA[AssertionError]
      }
    }
  }

}
