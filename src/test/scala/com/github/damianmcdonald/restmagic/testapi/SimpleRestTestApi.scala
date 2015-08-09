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
import com.github.damianmcdonald.restmagic.configurators.SimpleRestConfig
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.MediaTypes._
import spray.routing.Directives._
import spray.http.StatusCodes._
import spray.http.HttpMethods._

class SimpleRestTestApi extends RegistrableMock {

  private val rootApiPath = "restmagic"

  private val helloWorldHtmlApi = SimpleRestConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "simplerest" / "helloworld" / "html",
    produces = `text/html`,
    dataMode = Inline(),
    responseData = """
      |<html>
      |<head>
      |</head>
      |<body>
      |<h1>Hello World</h1>
      |</body>
      |</html>
    """.stripMargin,
    validate = true,
    displayName = "Hello World Html",
    displayUrl = "/" + rootApiPath + "/examples/simplerest/helloworld/html"
  )

  private val helloWorldJsonApi = SimpleRestConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "simplerest" / "helloworld" / "json",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = """{ "response": "Hello World" }""",
    validate = true,
    displayName = "Hello World Json",
    displayUrl = "/" + rootApiPath + "/examples/simplerest/helloworld/json"
  )

  private val helloWorldXmlApi = SimpleRestConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "simplerest" / "helloworld" / "xml",
    produces = `text/html`,
    dataMode = Inline(),
    responseData = """<root><response>Hello World</response></root>""",
    validate = true,
    displayName = "Hello World Xml",
    displayUrl = "/" + rootApiPath + "/examples/simplerest/helloworld/xml"
  )

  private val helloWorldFileApi = SimpleRestConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "simplerest" / "helloworld" / "file",
    produces = `application/json`,
    dataMode = FileStub(),
    responseData = "/examples/simplerest/hello_world.json",
    validate = true,
    displayName = "Hello World Json from file stub",
    displayUrl = "/" + rootApiPath + "/examples/simplerest/helloworld/file"
  )

  private val postExampleApi = SimpleRestConfig(
    httpMethod = POST,
    apiPath = rootApiPath / "examples" / "simplerest" / "post",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = """{ "status": "success" }""",
    validate = true,
    displayName = "Simple Rest Post example",
    displayUrl = "/" + rootApiPath + "/examples/simplerest/post"
  )

  private val putExampleApi = SimpleRestConfig(
    httpMethod = PUT,
    apiPath = rootApiPath / "examples" / "simplerest" / "put",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = """{ "status": "success" }""",
    validate = true,
    displayName = "Simple Rest Put example",
    displayUrl = "/" + rootApiPath + "/examples/simplerest/put"
  )

  private val deleteExampleApi = SimpleRestConfig(
    httpMethod = DELETE,
    apiPath = rootApiPath / "examples" / "simplerest" / "delete",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = """{ "status": "success" }""",
    validate = true,
    displayName = "Simple Rest Delete example",
    displayUrl = "/" + rootApiPath + "/examples/simplerest/delete"
  )

  def getApiConfig: List[RootApiConfig] = {
    List(
      helloWorldHtmlApi,
      helloWorldJsonApi,
      helloWorldXmlApi,
      helloWorldFileApi,
      postExampleApi,
      putExampleApi,
      deleteExampleApi
    )
  }

}
