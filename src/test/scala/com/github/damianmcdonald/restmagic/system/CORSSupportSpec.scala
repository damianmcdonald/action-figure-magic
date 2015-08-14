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

package com.github.damianmcdonald.restmagic.system

import com.github.damianmcdonald.restmagic.services.RootMockService
import org.specs2.mutable.Specification
import spray.http._
import spray.routing.Route
import spray.testkit.Specs2RouteTest

class CORSSupportSpec extends Specification with Specs2RouteTest with RootMockService {

  override def route: Route = null

  val testRoute = path("test") {
    cors {
      get { complete((200, "'CORS it works!")) } ~
        post { complete((200, "'CORS I'll update that!")) }
    }
  }

  "A CORS route" should {
    "work" in {
      Get("/test") ~> testRoute ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
        response mustEqual "'CORS it works!"
      }
      Post("/test") ~> testRoute ~> check {
        val response = responseAs[String]
        response must not be empty
        response mustEqual "'CORS I'll update that!"

      }
    }
  }

  "A CORS route" should {
    "respond to OPTIONS requests properly" in {
      Options("/test") ~> testRoute ~> check {
        status mustEqual StatusCodes.OK
        val allowMethods: List[String] = header("Access-Control-Allow-Methods").get.value.split(", ").toList
        Array("OPTIONS", "POST", "GET") foreach { allowMethods must contain(_) }
        Array("PUT", "DELETE") foreach { allowMethods must not contain (_) }
        header("Access-Control-Allow-Headers").isDefined mustEqual (true)
        header("Access-Control-Max-Age").isDefined mustEqual (true)
      }
    }
  }

  "A CORS route" should {
    "respond to all requests with the Access-Control-Allow-Origin header" in {
      Get("/test") ~> testRoute ~> check {
        header("Access-Control-Allow-Origin").isDefined mustEqual (true)
      }
      Post("/test") ~> testRoute ~> check {
        header("Access-Control-Allow-Origin").isDefined mustEqual (true)
      }
    }
  }

}
