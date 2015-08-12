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

import com.github.damianmcdonald.restmagic.configurators.ServeMode._
import com.github.damianmcdonald.restmagic.configurators._
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.HttpMethods._
import spray.http.StatusCodes._
import spray.routing.Directives._

class ParameterizedRestErrorTestApi extends RegistrableMock with ConfiguratorUtils {

  private val rootApiPath = "restmagic"

  private val errorWithGetByParamApi = ParameterizedRestErrorConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "error" / MATCH_PARAM,
    responseData = Map(
      "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
      "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
      "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
      "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
      "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
    ),
    serveMode = ByParam(),
    displayName = "Parameterized Rest Error by param",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/error/{{errorId}}"
  )

  private val errorWithPostByParamApi = ParameterizedRestErrorConfig(
    httpMethod = POST,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "error" / MATCH_ANY,
    responseData = Map(
      "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
      "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
      "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
      "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
      "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
    ),
    serveMode = Random(),
    displayName = "Parameterized Rest Error by param",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/error/**"
  )

  def getApiConfig: List[RootApiConfig] = {
    List(
      errorWithGetByParamApi,
      errorWithPostByParamApi
    )
  }

}
