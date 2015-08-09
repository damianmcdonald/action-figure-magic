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
import com.github.damianmcdonald.restmagic.configurators.DataMode._
import com.github.damianmcdonald.restmagic.configurators.ServeMode._
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.MediaTypes._
import spray.http.StatusCodes._
import spray.routing.Directives._
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import shapeless.HList
import spray.http.HttpMethods._

class ParameterizedRestTestApi extends RegistrableMock with ConfiguratorUtils {

  private val rootApiPath = "restmagic"

  private val helloWorldJsonByParamApi = ParameterizedRestConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "helloworld" / "json" / MATCH_PARAM,
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
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/helloworld/json/{{paramId}}"
  )

  private val mastersOfTheUniverseByParamApi = ParameterizedRestConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "motu" / "json" / "file" / MATCH_PARAM,
    produces = `application/json`,
    dataMode = FileStub(),
    responseData = Map(
      "1" -> "/examples/parameterizedrest/he-man.json",
      "2" -> "/examples/parameterizedrest/skeletor.json",
      "3" -> "/examples/parameterizedrest/man-at-arms.json",
      "4" -> "/examples/parameterizedrest/beast-man.json",
      "5" -> "/examples/parameterizedrest/orko.json"
    ),
    serveMode = ByParam(),
    validate = true,
    displayName = "Parameterized Rest Masters of the Universe Json File",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/motu/json/file/{{characterId}}"
  )

  private val heManSingularApi = ParameterizedRestConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "he-man" / MATCH_ANY,
    produces = `application/json`,
    dataMode = FileStub(),
    responseData = Map("1" -> "/examples/parameterizedrest/he-man.json"),
    serveMode = Singular(),
    validate = true,
    displayName = "Parameterized Rest He-Man",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/he-man/**"
  )

  private val matchAnyPathApi = ParameterizedRestConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "matchany" / MATCH_ANY,
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "english" -> """{ "response": "Hello World" }""",
      "turkish" -> """{ "response": "Merhaba Dunya" }""",
      "italian" -> """{ "response": "Salve Mondo" }""",
      "spanish" -> """{ "response": "Hola Mundo" }""",
      "german" -> """{ "response": "Halo Welt" }"""
    ),
    serveMode = Random(),
    validate = true,
    displayName = "Parameterized Rest matchany",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/matchany/**"
  )

  private val matchAnyPathByParamApi = ParameterizedRestConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "matchpath" / "asparam" / MATCH_ANY,
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "english/hello/world/response" -> """{ "response": "Hello World" }""",
      "turkish/hello/world/response" -> """{ "response": "Merhaba Dunya" }""",
      "italian/hello/world/response" -> """{ "response": "Salve Mondo" }""",
      "spanish/hello/world/response" -> """{ "response": "Hola Mundo" }""",
      "german/hello/world/response" -> """{ "response": "Halo Welt" }"""
    ),
    serveMode = ByParam(),
    validate = true,
    displayName = "Parameterized Rest matchany asparam",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/matchany/**"
  )

  private val postByParamApi = ParameterizedRestConfig(
    httpMethod = POST,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "post" / MATCH_PARAM,
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "1" -> """{ "response": "Hello World" }""",
      "2" -> """{ "response": "Merhaba Dunya" }""",
      "3" -> """{ "response": "Salve Mondo" }""",
      "4" -> """{ "response": "Hola Mundo" }""",
      "5" -> """{ "response": "Halo Welt" }"""
    ),
    serveMode = ByParam(),
    validate = true,
    displayName = "Parameterized Rest Post",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/post/{{paramId}}"
  )

  private val putByParamApi = ParameterizedRestConfig(
    httpMethod = PUT,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "put" / MATCH_PARAM,
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "1" -> """{ "response": "Hello World" }""",
      "2" -> """{ "response": "Merhaba Dunya" }""",
      "3" -> """{ "response": "Salve Mondo" }""",
      "4" -> """{ "response": "Hola Mundo" }""",
      "5" -> """{ "response": "Halo Welt" }"""
    ),
    serveMode = ByParam(),
    validate = true,
    displayName = "Parameterized Rest Put",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/put/{{paramId}}"
  )

  private val deleteByParamApi = ParameterizedRestConfig(
    httpMethod = DELETE,
    apiPath = rootApiPath / "examples" / "parameterizedrest" / "delete" / MATCH_PARAM,
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map(
      "1" -> """{ "response": "Hello World" }""",
      "2" -> """{ "response": "Merhaba Dunya" }""",
      "3" -> """{ "response": "Salve Mondo" }""",
      "4" -> """{ "response": "Hola Mundo" }""",
      "5" -> """{ "response": "Halo Welt" }"""
    ),
    serveMode = ByParam(),
    validate = true,
    displayName = "Parameterized Rest Delete",
    displayUrl = "/" + rootApiPath + "/examples/parameterizedrest/delete/{{paramId}}"
  )

  def getApiConfig: List[RootApiConfig] = {
    List(
      helloWorldJsonByParamApi,
      mastersOfTheUniverseByParamApi,
      heManSingularApi,
      matchAnyPathApi,
      matchAnyPathByParamApi,
      postByParamApi,
      putByParamApi,
      deleteByParamApi
    )
  }

}
