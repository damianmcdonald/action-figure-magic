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

class ParameterizedHttpByQueryStringTestApi extends RegistrableMock {

  private val rootApiPath = "restmagic"

  private val helloWorldJsonByParamApi = ParameterizedHttpConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "helloworld" / "json",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "english" -> """{ "response": "Hello World" }""",
      "turkish" -> """{ "response": "Merhaba Dunya" }""",
      "italian" -> """{ "response": "Salve Mondo" }""",
      "spanish" -> """{ "response": "Hola Mundo" }""",
      "german" -> """{ "response": "Halo Welt" }"""
    ),
    paramName = "lang",
    serveMode = ByParam(),
    formMode = ByQueryString(),
    validate = true,
    displayName = "Parameterized Http Hello World Json",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/helloworld/json?lang={{langId}}"
  )

  private val postByParamApi = ParameterizedHttpConfig(
    httpMethod = POST,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "post",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "1" -> """{ "response": "Hello World" }""",
      "2" -> """{ "response": "Merhaba Dunya" }""",
      "3" -> """{ "response": "Salve Mondo" }""",
      "4" -> """{ "response": "Hola Mundo" }""",
      "5" -> """{ "response": "Halo Welt" }"""
    ),
    paramName = "lang",
    serveMode = ByParam(),
    formMode = ByFormData(),
    validate = true,
    displayName = "Parameterized Http Post",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/post"
  )

  private val putByParamApi = ParameterizedHttpConfig(
    httpMethod = PUT,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "put",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "1" -> """{ "response": "Hello World" }""",
      "2" -> """{ "response": "Merhaba Dunya" }""",
      "3" -> """{ "response": "Salve Mondo" }""",
      "4" -> """{ "response": "Hola Mundo" }""",
      "5" -> """{ "response": "Halo Welt" }"""
    ),
    paramName = "lang",
    serveMode = ByParam(),
    formMode = ByFormData(),
    validate = true,
    displayName = "Parameterized Http Put",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/put"
  )

  private val deleteByParamApi = ParameterizedHttpConfig(
    httpMethod = DELETE,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "delete",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "1" -> """{ "response": "Hello World" }""",
      "2" -> """{ "response": "Merhaba Dunya" }""",
      "3" -> """{ "response": "Salve Mondo" }""",
      "4" -> """{ "response": "Hola Mundo" }""",
      "5" -> """{ "response": "Halo Welt" }"""
    ),
    paramName = "lang",
    serveMode = ByParam(),
    formMode = ByFormData(),
    validate = true,
    displayName = "Parameterized Http Delete",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/delete"
  )

  def getApiConfig: List[RootApiConfig] = {
    List(
      helloWorldJsonByParamApi,
      postByParamApi,
      putByParamApi,
      deleteByParamApi
    )
  }

}
