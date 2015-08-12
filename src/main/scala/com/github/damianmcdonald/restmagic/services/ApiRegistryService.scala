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

package com.github.damianmcdonald.restmagic.services

import akka.actor.ActorSystem
import akka.event.slf4j.SLF4JLogging
import com.github.damianmcdonald.restmagic.configurators.RegisteredApi
import spray.http.MediaTypes._
import spray.routing.Directives

class ApiRegistryService(apis: Map[String, List[RegisteredApi]])(implicit system: ActorSystem)
    extends Directives with RootMockService with SLF4JLogging {

  def getResponseDataTemplate(displayName: String, responseData: Map[String, String], produces: String): String = {

    def processResponseData(param: String = "None", value: String): String = {
      val escapedValue = {
        try {
          xml.Utility.escape(value)
        } catch {
          case e: Exception => value
        }
      }
      s"""
      |      <div class="container-fluid">
      |        <p class="lead">Response to param: $param, produces: $produces</p>
      |        <pre class="bg-success">
      |$escapedValue
      |      </pre>
      |      </div>
       """.stripMargin
    }

    def generateTemplate(responseTemplate: String): String = {
      s"""
         |<!DOCTYPE html>
         |<html lang="en">
         |  <head>
         |    <meta charset="utf-8">
         |    <meta http-equiv="X-UA-Compatible" content="IE=edge">
         |    <meta name="viewport" content="width=device-width, initial-scale=1">
         |    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
         |    <title>$displayName Response Data</title>
         |
         |    <!-- Bootstrap -->
         |    <!-- Latest compiled and minified CSS -->
         |    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
         |
         |    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
         |    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
         |    <!--[if lt IE 9]>
         |      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
         |      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
         |      <![endif]-->
         |  </head>
         |    <body>
         |
         |      <div class="container-fluid">
         |        <h2>$displayName</h2>
         |      </div>
         |
         |      <hr>
         |
         |      $responseTemplate
         |
         |      </div>
         |
         |  </body>
         |
         |</html>
         |
         |
         |
       """.stripMargin
    }

    generateTemplate(responseData.map({ case (k, v) => processResponseData(k, v) }).mkString("<hr>"))

  }

  lazy val route =
    path("restmagic" / "api" / "registry") {
      get {
        respondWithMediaType(`application/json`) {
          complete {
            val apiContent = apis.map({
              case (k, v) =>
                val directive = k.toString
                val registeredApis = v.map(api => api.toJson).mkString(",")
                s"""{
                	|"directive":"$directive",
                	|"registeredApis": [
                	|$registeredApis
                	|]
                	|}""".stripMargin
            }).mkString(",")

            if (apis.isEmpty) {
              """{ "status": "No registered API's" }"""
            } else {
              import net.liftweb.json._
              implicit val formats = net.liftweb.json.DefaultFormats
              val json = s"""{"apis": [ $apiContent ] }"""
              pretty(render(parse(json)))
            }
          }
        }
      }
    } ~
      path("restmagic" / "api" / "registry" / "responsedata" / """\w+""".r) { param =>
        get {
          respondWithMediaType(`text/html`) {
            complete {
              val apiList = apis.map({ case (k, v) => v }).flatten.filter(_.id.equals(param))
              val api: RegisteredApi = apiList.headOption.getOrElse(
                throw new RuntimeException("Unable to find matching registry entry for: " + param)
              )
              getResponseDataTemplate(api.displayName, api.responseData, api.produces)
            }
          }
        }
      }

}
