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
import spray.http.StatusCodes._

class SimpleRestErrorConfigSpec extends Specification {

  private val jsonAuthenticateResponse = """{ "status": "authenticated" }"""
  private val jsonAuthorizeResponse = """{ "status": "authorized" }"""

  "A SimpleRestErrorConfig" should {
    "return a valid SimpleRestErrorConfig class instance using a minimal constructor" in {
      val simpleRestErrorConfig = SimpleRestErrorConfig(
        httpMethod = GET,
        apiPath = "examples",
        errorCode = InternalServerError,
        errorMessage = "Example of 500 Internal Server Error from RestMagic"
      )
      simpleRestErrorConfig must not be null
    }
  }

  "A SimpleRestErrorConfig" should {
    "return a valid SimpleRestErrorConfig class instance using a full constructor" in {
      val simpleRestErrorConfig = SimpleRestErrorConfig(
        httpMethod = GET,
        apiPath = "examples",
        errorCode = InternalServerError,
        errorMessage = "Example of 500 Internal Server Error from RestMagic",
        displayName = "Simple Rest Error example",
        displayUrl = "/examples/simplerest/error/500"
      )
      simpleRestErrorConfig must not be null
    }
  }

}
