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
import org.specs2.mutable.Specification
import spray.http.HttpMethods._
import spray.http.MediaTypes._

class SimpleRestConfigSpec extends Specification {

  private val jsonAuthenticateResponse = """{ "status": "authenticated" }"""
  private val jsonAuthorizeResponse = """{ "status": "authorized" }"""

  "A SimpleRestConfig" should {
    "return a valid SimpleRestConfig class instance using a minimal constructor" in {
      val simpleRestConfig = SimpleRestConfig(
        httpMethod = GET,
        apiPath = "examples",
        produces = `application/json`,
        dataMode = Inline(),
        responseData = """{ "response": "Hello World" }"""
      )
      simpleRestConfig must not be null
    }
  }

  "A SimpleRestConfig" should {
    "return a valid SimpleRestConfig class instance using a partial constructor" in {
      val simpleRestConfig = SimpleRestConfig(
        httpMethod = GET,
        apiPath = "examples",
        produces = `application/json`,
        dataMode = Inline(),
        responseData = """{ "response": "Hello World" }""",
        validate = true
      )
      simpleRestConfig must not be null
    }
  }

  "A SimpleRestConfig" should {
    "return a valid SimpleRestConfig class instance using a full constructor" in {
      val simpleRestConfig = SimpleRestConfig(
        httpMethod = GET,
        apiPath = "examples",
        produces = `application/json`,
        dataMode = Inline(),
        responseData = """{ "response": "Hello World" }""",
        validate = true,
        displayName = "Hello World Json",
        displayUrl = "/examples/simplerest/helloworld/json"
      )
      simpleRestConfig must not be null
    }
  }

  "A SimpleRestConfig" should {
    "throw an assertion error" in {
      "when a SimpleRestConfig minimal constructor contains empty responseData" in {
        def simpleRestConfig = SimpleRestConfig(
          httpMethod = GET,
          apiPath = "examples",
          produces = `application/json`,
          dataMode = Inline(),
          responseData = ""
        )
        simpleRestConfig must throwA[AssertionError]
      }
    }
  }

  "A SimpleRestConfig" should {
    "throw an assertion error" in {
      "when a SimpleRestConfig partial constructor contains empty responseData" in {
        def simpleRestConfig = SimpleRestConfig(
          httpMethod = GET,
          apiPath = "examples",
          produces = `application/json`,
          dataMode = Inline(),
          responseData = "",
          validate = true
        )
        simpleRestConfig must throwA[AssertionError]
      }
    }
  }

  "A SimpleRestConfig" should {
    "throw an assertion error" in {
      "when a SimpleRestConfig full constructor contains empty responseData" in {
        def simpleRestConfig = SimpleRestConfig(
          httpMethod = GET,
          apiPath = "examples",
          produces = `application/json`,
          dataMode = Inline(),
          responseData = "",
          validate = true,
          displayName = "Hello World Json",
          displayUrl = "/examples/simplerest/helloworld/json"
        )
        simpleRestConfig must throwA[AssertionError]
      }
    }
  }

}
