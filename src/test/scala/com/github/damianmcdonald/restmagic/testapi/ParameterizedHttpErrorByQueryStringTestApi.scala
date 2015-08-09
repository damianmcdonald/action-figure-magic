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

import com.github.damianmcdonald.restmagic.configurators.FormMode.{ ByFormData, ByQueryString }
import com.github.damianmcdonald.restmagic.configurators._
import com.github.damianmcdonald.restmagic.configurators.DataMode._
import com.github.damianmcdonald.restmagic.configurators.ServeMode._
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.HttpMethods._
import spray.http.MediaTypes._
import spray.routing.Directives._
import spray.http.StatusCodes._

class ParameterizedHttpErrorByQueryStringTestApi extends RegistrableMock {

  private val rootApiPath = "restmagic"

  private val errorWithGetByQueyStringParamApi = ParameterizedHttpErrorConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "error" / "querystring" / "get",
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
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/error/querystring/get/{{errorId}}"
  )

  private val errorWithPostByQueyStringParamApi = ParameterizedHttpErrorConfig(
    httpMethod = POST,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "error" / "querystring" / "post",
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
    displayName = "Parameterized Http Error Post by query string",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/error/querystring/post/{{errorId}}"
  )

  private val errorWithGetByFormDataParamApi = ParameterizedHttpErrorConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "error" / "formdata" / "get",
    responseData = Map(
      "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
      "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
      "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
      "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
      "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
    ),
    paramName = "errorId",
    serveMode = ByParam(),
    formMode = ByFormData(),
    displayName = "Parameterized Http Error Get by form data",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/error/formdata/get/{{errorId}}"
  )

  private val errorWithPostByFormDataParamApi = ParameterizedHttpErrorConfig(
    httpMethod = POST,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "error" / "formdata" / "post",
    responseData = Map(
      "1" -> ErrorCode(BadRequest, "RestMagic BadRequest error"),
      "2" -> ErrorCode(BandwidthLimitExceeded, "RestMagic BandwidthLimitExceeded error"),
      "3" -> ErrorCode(GatewayTimeout, "RestMagic GatewayTimeout error"),
      "4" -> ErrorCode(NetworkConnectTimeout, "RestMagic NetworkConnectTimeout error"),
      "5" -> ErrorCode(RequestUriTooLong, "RestMagic RequestUriTooLong error")
    ),
    paramName = "errorId",
    serveMode = ByParam(),
    formMode = ByFormData(),
    displayName = "Parameterized Http Error Post by form data",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/error/formdata/post/{{errorId}}"
  )

  def getApiConfig: List[RootApiConfig] = {
    List(
      errorWithGetByQueyStringParamApi,
      errorWithPostByQueyStringParamApi,
      errorWithGetByFormDataParamApi,
      errorWithPostByFormDataParamApi
    )
  }

}
