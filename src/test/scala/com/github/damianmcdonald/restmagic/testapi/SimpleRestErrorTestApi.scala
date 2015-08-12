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

package com.github.damianmcdonald.restmagic.testapi

import com.github.damianmcdonald.restmagic.configurators._
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.HttpMethods._
import spray.http.StatusCodes._
import spray.routing.Directives._

class SimpleRestErrorTestApi extends RegistrableMock {

  private val rootApiPath = "restmagic"

  private val simpleRestInternalError = SimpleRestErrorConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "simplerest" / "error" / "500",
    errorCode = InternalServerError,
    errorMessage = "Example of 500 Internal Server Error from RestMagic",
    displayName = "Simple Rest Error example",
    displayUrl = "/" + rootApiPath + "/examples/simplerest/error/500"
  )

  def getApiConfig: List[RootApiConfig] = {
    List(
      simpleRestInternalError
    )
  }

}
