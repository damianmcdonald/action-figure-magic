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

import com.github.damianmcdonald.restmagic.configurators.DataMode.Inline
import com.github.damianmcdonald.restmagic.configurators.ServeMode.{ Random, Singular, ByParam }
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import org.specs2.mutable.Specification
import spray.http.HttpMethods._
import spray.http.MediaTypes._
import spray.routing.PathMatcher1
import spray.testkit.Specs2RouteTest
import spray.routing.Directives._

class ParameterizedRestConfigSpec extends Specification with Specs2RouteTest with ConfiguratorUtils {

  private val jsonAuthenticateResponse = """{ "status": "authenticated" }"""
  private val jsonAuthorizeResponse = """{ "status": "authorized" }"""

  "A ParameterizedRestConfig" should {
    "return a valid ParameterizedRestConfig class instance using a minimal constructor" in {
      val parameterizedRestConfig = ParameterizedRestConfig(
        httpMethod = GET,
        apiPath = "examples" / MATCH_PARAM,
        produces = `application/json`,
        dataMode = Inline(),
        responseData = Map(
          "english" -> """{ "response": "Hello World" }""",
          "turkish" -> """{ "response": "Merhaba Dunya" }""",
          "italian" -> """{ "response": "Salve Mondo" }""",
          "spanish" -> """{ "response": "Hola Mundo" }""",
          "german" -> """{ "response": "Halo Welt" }"""
        ),
        serveMode = ByParam()
      )
      parameterizedRestConfig must not be null
    }
  }

  "A ParameterizedRestConfig" should {
    "return a valid ParameterizedRestConfig class instance using a partial constructor" in {
      val parameterizedRestConfig = ParameterizedRestConfig(
        httpMethod = GET,
        apiPath = "examples" / MATCH_PARAM,
        produces = `application/json`,
        dataMode = Inline(),
        responseData = Map(
          "english" -> """{ "response": "Hello World" }""",
          "turkish" -> """{ "response": "Merhaba Dunya" }""",
          "italian" -> """{ "response": "Salve Mondo" }""",
          "spanish" -> """{ "response": "Hola Mundo" }""",
          "german" -> """{ "response": "Halo Welt" }"""
        ),
        serveMode = ByParam(),
        validate = true
      )
      parameterizedRestConfig must not be null
    }
  }

  "A ParameterizedRestConfig" should {
    "return a valid ParameterizedRestConfig class instance using a full constructor" in {
      val parameterizedRestConfig = ParameterizedRestConfig(
        httpMethod = GET,
        apiPath = "examples" / MATCH_PARAM,
        produces = `application/json`,
        dataMode = Inline(),
        responseData = Map(
          "english" -> """{ "response": "Hello World" }""",
          "turkish" -> """{ "response": "Merhaba Dunya" }""",
          "italian" -> """{ "response": "Salve Mondo" }""",
          "spanish" -> """{ "response": "Hola Mundo" }""",
          "german" -> """{ "response": "Halo Welt" }"""
        ),
        serveMode = ByParam(),
        validate = true,
        displayName = "Parameterized Rest Hello World Json",
        displayUrl = "/examples/parameterizedrest/helloworld/json/{{paramId}}"
      )
      parameterizedRestConfig must not be null
    }
  }

  "A ParameterizedRestConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRest minimal constructor contains empty responseData" in {
        def parameterizedRestConfig = ParameterizedRestConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          produces = `application/json`,
          dataMode = Inline(),
          responseData = Map[String, String](),
          serveMode = ByParam()
        )
        parameterizedRestConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRest minimal constructor is singular serve mode but responseData contains more than 1 item" in {
        def parameterizedRestConfig = ParameterizedRestConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          produces = `application/json`,
          dataMode = Inline(),
          responseData = Map(
            "english" -> """{ "response": "Hello World" }""",
            "turkish" -> """{ "response": "Merhaba Dunya" }""",
            "italian" -> """{ "response": "Salve Mondo" }""",
            "spanish" -> """{ "response": "Hola Mundo" }""",
            "german" -> """{ "response": "Halo Welt" }"""
          ),
          serveMode = Singular()
        )
        parameterizedRestConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRest minimal constructor is not singular serve mode but responseData contains only 1 item" in {
        def parameterizedRestConfig = ParameterizedRestConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          produces = `application/json`,
          dataMode = Inline(),
          responseData = Map("english" -> """{ "response": "Hello World" }"""),
          serveMode = Random()
        )
        parameterizedRestConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRest partial constructor contains empty responseData" in {
        def parameterizedRestConfig = ParameterizedRestConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          produces = `application/json`,
          dataMode = Inline(),
          responseData = Map[String, String](),
          serveMode = ByParam(),
          validate = true
        )
        parameterizedRestConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRest partial constructor is singular serve mode but responseData contains more than 1 item" in {
        def parameterizedRestConfig = ParameterizedRestConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          produces = `application/json`,
          dataMode = Inline(),
          responseData = Map(
            "english" -> """{ "response": "Hello World" }""",
            "turkish" -> """{ "response": "Merhaba Dunya" }""",
            "italian" -> """{ "response": "Salve Mondo" }""",
            "spanish" -> """{ "response": "Hola Mundo" }""",
            "german" -> """{ "response": "Halo Welt" }"""
          ),
          serveMode = Singular(),
          validate = true
        )
        parameterizedRestConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRest partial constructor is not singular serve mode but responseData contains only 1 item" in {
        def parameterizedRestConfig = ParameterizedRestConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          produces = `application/json`,
          dataMode = Inline(),
          responseData = Map("english" -> """{ "response": "Hello World" }"""),
          serveMode = Random(),
          validate = true
        )
        parameterizedRestConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRest full constructor contains empty responseData" in {
        def parameterizedRestConfig = ParameterizedRestConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          produces = `application/json`,
          dataMode = Inline(),
          responseData = Map[String, String](),
          serveMode = ByParam(),
          validate = true,
          displayName = "Parameterized Rest Hello World Json",
          displayUrl = "/examples/parameterizedrest/helloworld/json/{{paramId}}"
        )
        parameterizedRestConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRest full constructor is singular serve mode but responseData contains more than 1 item" in {
        def parameterizedRestConfig = ParameterizedRestConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          produces = `application/json`,
          dataMode = Inline(),
          responseData = Map(
            "english" -> """{ "response": "Hello World" }""",
            "turkish" -> """{ "response": "Merhaba Dunya" }""",
            "italian" -> """{ "response": "Salve Mondo" }""",
            "spanish" -> """{ "response": "Hola Mundo" }""",
            "german" -> """{ "response": "Halo Welt" }"""
          ),
          serveMode = Singular(),
          validate = true,
          displayName = "Parameterized Rest Hello World Json",
          displayUrl = "/examples/parameterizedrest/helloworld/json/{{paramId}}"
        )
        parameterizedRestConfig must throwA[AssertionError]
      }
    }
  }

  "A ParameterizedRestConfig" should {
    "throw an assertion error" in {
      "when a ParameterizedRest full constructor is not singular serve mode but responseData contains only 1 item" in {
        def parameterizedRestConfig = ParameterizedRestConfig(
          httpMethod = GET,
          apiPath = "examples" / MATCH_PARAM,
          produces = `application/json`,
          dataMode = Inline(),
          responseData = Map("english" -> """{ "response": "Hello World" }"""),
          serveMode = Random(),
          validate = true,
          displayName = "Parameterized Rest Hello World Json",
          displayUrl = "/examples/parameterizedrest/helloworld/json/{{paramId}}"
        )
        parameterizedRestConfig must throwA[AssertionError]
      }
    }
  }

}
