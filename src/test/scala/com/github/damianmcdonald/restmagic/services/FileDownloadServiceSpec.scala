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

import com.github.damianmcdonald.restmagic.api.RestMagicApi
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class FileDownloadServiceSpec extends Specification with Specs2RouteTest with RestMagicApi {

  def actorRefFactory = system

  private val rootApiPath = "restmagic"

  implicit val formats = net.liftweb.json.DefaultFormats

  "The FileDownloadService created via FileDownloadTestApi" should {
    "return a binary pdf file as attachment" in {
      Get("/" + rootApiPath + "/examples/filedownload/attachment") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

  "The FileDownloadService created via FileDownloadTestApi" should {
    "return a binary pdf file inline" in {
      Get("/" + rootApiPath + "/examples/filedownload/inline") ~> routes ~> check {
        status mustEqual StatusCodes.OK
        val response = responseAs[String]
        response must not be empty
      }
    }
  }

}
