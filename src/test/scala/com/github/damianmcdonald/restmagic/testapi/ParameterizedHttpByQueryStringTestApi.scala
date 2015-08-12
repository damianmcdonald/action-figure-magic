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

import com.github.damianmcdonald.restmagic.configurators.DataMode._
import com.github.damianmcdonald.restmagic.configurators.FormMode.ByQueryString
import com.github.damianmcdonald.restmagic.configurators.ServeMode._
import com.github.damianmcdonald.restmagic.configurators._
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.HttpMethods._
import spray.http.MediaTypes._
import spray.routing.Directives._

class ParameterizedHttpByQueryStringTestApi extends RegistrableMock {

  private val rootApiPath = "restmagic"

  private val strategy = (param: String, data: Map[String, String]) => {
    if (param.toInt > 0 && param.toInt < 500) {
      data.getOrElse("1", throw new RuntimeException("Unable to find map entry for key 1"))
    } else if (param.toInt > 501 && param.toInt < 1000) {
      data.getOrElse("2", throw new RuntimeException("Unable to find map entry for key 2"))
    } else if (param.toInt > 1001 && param.toInt < 1500) {
      data.getOrElse("3", throw new RuntimeException("Unable to find map entry for key 3"))
    } else if (param.toInt > 1501 && param.toInt < 2000) {
      data.getOrElse("4", throw new RuntimeException("Unable to find map entry for key 4"))
    } else {
      data.getOrElse("5", throw new RuntimeException("Unable to find map entry for key 5"))
    }
  }

  private val helloWorldJsonByParamApi = ParameterizedHttpConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "querystring",
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
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/querystring?lang={{langId}}"
  )

  private val getWithCustomStrategyApi = ParameterizedHttpConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedhttp" / "querystring" / "customstrategy",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "1" -> """{ "response": "Hello World" }""",
      "2" -> """{ "response": "Merhaba Dunya" }""",
      "3" -> """{ "response": "Salve Mondo" }""",
      "4" -> """{ "response": "Hola Mundo" }""",
      "5" -> """{ "response": "Halo Welt" }"""
    ),
    paramName = "id",
    serveMode = CustomStrategy(strategy),
    formMode = ByQueryString(),
    validate = true,
    displayName = "Parameterized Http Custom Strategy",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedhttp/querystring/customstrategy?id={{id}}"
  )

  def getApiConfig: List[RootApiConfig] = {
    List(
      helloWorldJsonByParamApi,
      getWithCustomStrategyApi
    )
  }

}
