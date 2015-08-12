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

import com.github.damianmcdonald.restmagic.configurators.FormMode.ByQueryString
import com.github.damianmcdonald.restmagic.configurators.ServeMode._
import org.specs2.mutable.Specification
import spray.http.HttpMethods._
import spray.http.StatusCodes._

class ParameterizedHttpErrorConfigSpec extends Specification {

  private val jsonAuthenticateResponse = """{ "status": "authenticated" }"""
  private val jsonAuthorizeResponse = """{ "status": "authorized" }"""
  private val strategy = (param: String, data: Map[String, String]) => {
    "dummy value"
  }

  "A ParameterizedHttpErrorConfig" should {
    "return a valid ParameterizedHttpErrorConfig class instance using a minimal constructor" in {
      val parameterizedHttpErrorConfig = ParameterizedHttpErrorConfig(
        httpMethod = GET,
        apiPath = "examples",
        responseData = Map(
          "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
          "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
          "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
          "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
          "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
        ),
        paramName = "errorId",
        serveMode = ByParam(),
        formMode = ByQueryString()
      )
      parameterizedHttpErrorConfig must not be null
    }
  }

  "A ParameterizedHttpErrorConfig" should {
    "return a valid ParameterizedHttpErrorConfig class instance using a full constructor" in {
      val parameterizedHttpErrorConfig = ParameterizedHttpErrorConfig(
        httpMethod = GET,
        apiPath = "examples",
        responseData = Map(
          "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
          "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
          "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
          "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
          "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
        ),
        paramName = "errorId",
        serveMode = ByParam(),
        formMode = ByQueryString(),
        displayName = "Parameterized Http Error Get by query string",
        displayUrl = "/examples/parameterizedhttp/error/querystring/get/{{errorId}}"
      )
      parameterizedHttpErrorConfig must not be null
    }
  }

  "A ParameterizedHttpErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedHttpErrorConfig minimal constructor contains empty responseData" in {
        def parameterizedHttpConfig = ParameterizedHttpErrorConfig(
          httpMethod = GET,
          apiPath = "examples",
          responseData = Map[String, ErrorCode](),
          paramName = "errorId",
          serveMode = ByParam(),
          formMode = ByQueryString()
        )
        parameterizedHttpConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedHttpErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedHttpErrorConfig minimal constructor is CustomStrategy mode" in {
        def parameterizedHttpConfig = ParameterizedHttpErrorConfig(
          httpMethod = GET,
          apiPath = "examples",
          responseData = Map[String, ErrorCode](),
          paramName = "errorId",
          serveMode = CustomStrategy(strategy),
          formMode = ByQueryString()
        )
        parameterizedHttpConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedHttpErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedHttpErrorConfig minimal constructor is singular serve mode but responseData contains more than 1 item" in {
        def parameterizedHttpConfig = ParameterizedHttpErrorConfig(
          httpMethod = GET,
          apiPath = "examples",
          responseData = Map(
            "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
            "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
            "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
            "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
            "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
          ),
          paramName = "errorId",
          serveMode = Singular(),
          formMode = ByQueryString()
        )
        parameterizedHttpConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedHttpErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedHttpErrorConfig minimal constructor is not singular serve mode but responseData contains only 1 item" in {
        def parameterizedHttpConfig = ParameterizedHttpErrorConfig(
          httpMethod = GET,
          apiPath = "examples",
          responseData = Map("1" -> ErrorCode(BadRequest, "RestMagic BadRequest error")),
          paramName = "lang",
          serveMode = Random(),
          formMode = ByQueryString()
        )
        parameterizedHttpConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedHttpErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedHttpErrorConfig full constructor contains empty responseData" in {
        def parameterizedHttpConfig = ParameterizedHttpErrorConfig(
          httpMethod = GET,
          apiPath = "examples",
          responseData = Map[String, ErrorCode](),
          paramName = "errorId",
          serveMode = ByParam(),
          formMode = ByQueryString(),
          displayName = "Parameterized Http Error Get by query string",
          displayUrl = "/examples/parameterizedhttp/error/querystring/get/{{errorId}}"
        )
        parameterizedHttpConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedHttpErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedHttpErrorConfig full constructor is CustomStrategy mode" in {
        def parameterizedHttpConfig = ParameterizedHttpErrorConfig(
          httpMethod = GET,
          apiPath = "examples",
          responseData = Map[String, ErrorCode](),
          paramName = "errorId",
          serveMode = CustomStrategy(strategy),
          formMode = ByQueryString(),
          displayName = "Parameterized Http Error Get by query string",
          displayUrl = "/examples/parameterizedhttp/error/querystring/get/{{errorId}}"
        )
        parameterizedHttpConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedHttpErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedHttpErrorConfig full constructor is singular serve mode but responseData contains more than 1 item" in {
        def parameterizedHttpConfig = ParameterizedHttpErrorConfig(
          httpMethod = GET,
          apiPath = "examples",
          responseData = Map(
            "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
            "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
            "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
            "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
            "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
          ),
          paramName = "errorId",
          serveMode = Singular(),
          formMode = ByQueryString(),
          displayName = "Parameterized Http Error Get by query string",
          displayUrl = "/examples/parameterizedhttp/error/querystring/get/{{errorId}}"
        )
        parameterizedHttpConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedHttpErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedHttpErrorConfig full constructor is not singular serve mode but responseData contains only 1 item" in {
        def parameterizedHttpConfig = ParameterizedHttpErrorConfig(
          httpMethod = GET,
          apiPath = "examples",
          responseData = Map("1" -> ErrorCode(BadRequest, "RestMagic BadRequest error")),
          paramName = "lang",
          serveMode = Random(),
          formMode = ByQueryString(),
          displayName = "Parameterized Http Error Get by query string",
          displayUrl = "/examples/parameterizedhttp/error/querystring/get/{{errorId}}"
        )
        parameterizedHttpConfig must throwA[AssertionError]
      }
    }
  }

}
