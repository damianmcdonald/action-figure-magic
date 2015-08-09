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

import com.github.damianmcdonald.restmagic.configurators.ServeMode.{ ByParam, Random, Singular }
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import org.specs2.mutable.Specification
import spray.http.HttpMethods._
import spray.http.StatusCodes._
import spray.routing.Directives._
import spray.testkit.Specs2RouteTest

class ParameterizedRestErrorConfigSpec extends Specification with Specs2RouteTest with ConfiguratorUtils {

  private val jsonAuthenticateResponse = """{ "status": "authenticated" }"""
  private val jsonAuthorizeResponse = """{ "status": "authorized" }"""

  "A ParameterizedRestErrorConfig" should {
    "return a valid ParameterizedRestErrorConfig class instance using a minimal constructor" in {
      val parameterizedRestErrorConfig = ParameterizedRestErrorConfig(
        httpMethod = GET,
        apiPath = "examples" / MATCH_PARAM,
        responseData = Map(
          "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
          "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
          "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
          "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
          "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
        ),
        serveMode = ByParam()
      )
      parameterizedRestErrorConfig must not be null
    }
  }

  "A ParameterizedRestErrorConfig" should {
    "return a valid ParameterizedRestErrorConfig class instance using a full constructor" in {
      val parameterizedRestErrorConfig = ParameterizedRestErrorConfig(
        httpMethod = GET,
        apiPath = "examples" / MATCH_PARAM,
        responseData = Map(
          "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
          "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
          "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
          "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
          "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
        ),
        serveMode = ByParam(),
        displayName = "Parameterized Rest Error by param",
        displayUrl = "/examples/parameterizedrest/error/{{errorId}}"
      )
      parameterizedRestErrorConfig must not be null
    }
  }

  "A ParameterizedRestErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRestErrorConfig minimal constructor contains empty responseData" in {
        def parameterizedRestErrorConfig = ParameterizedRestErrorConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          responseData = Map[String, ErrorCode](),
          serveMode = ByParam()
        )
        parameterizedRestErrorConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRestErrorConfig minimal constructor is singular serve mode but responseData contains more than 1 item" in {
        def parameterizedRestErrorConfig = ParameterizedRestErrorConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          responseData = Map(
            "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
            "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
            "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
            "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
            "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
          ),
          serveMode = Singular()
        )
        parameterizedRestErrorConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRestErrorConfig minimal constructor is not singular serve mode but responseData contains only 1 item" in {
        def parameterizedRestErrorConfig = ParameterizedRestErrorConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          responseData = Map("1" -> ErrorCode(BadRequest, "RestMagic BadRequest error")),
          serveMode = Random()
        )
        parameterizedRestErrorConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRestErrorConfig full constructor contains empty responseData" in {
        def parameterizedRestErrorConfig = ParameterizedRestErrorConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          responseData = Map[String, ErrorCode](),
          serveMode = ByParam(),
          displayName = "Parameterized Rest Error by param",
          displayUrl = "/examples/parameterizedrest/error/{{errorId}}"
        )
        parameterizedRestErrorConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRestErrorConfig full constructor is singular serve mode but responseData contains more than 1 item" in {
        def parameterizedRestErrorConfig = ParameterizedRestErrorConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          responseData = Map(
            "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
            "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
            "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
            "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
            "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
          ),
          serveMode = Singular(),
          displayName = "Parameterized Rest Error by param",
          displayUrl = "/examples/parameterizedrest/error/{{errorId}}"
        )
        parameterizedRestErrorConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestErrorConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRestErrorConfig full constructor is not singular serve mode but responseData contains only 1 item" in {
        def parameterizedRestErrorConfig = ParameterizedRestErrorConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          responseData = Map("1" -> ErrorCode(BadRequest, "RestMagic BadRequest error")),
          serveMode = Random(),
          displayName = "Parameterized Rest Error by param",
          displayUrl = "/examples/parameterizedrest/error/{{errorId}}"
        )
        parameterizedRestErrorConfig must throwA[AssertionError]
      }
    }
  }

}
