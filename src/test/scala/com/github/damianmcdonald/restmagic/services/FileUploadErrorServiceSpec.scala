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

import java.nio.file.{ Path, Paths }

import com.github.damianmcdonald.restmagic.api.RestMagicApi
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class FileUploadErrorServiceSpec extends Specification with Specs2RouteTest with RestMagicApi {

  def actorRefFactory = system

  private val rootApiPath = "restmagic"

  implicit val formats = net.liftweb.json.DefaultFormats

  "The FileUploadErrorService created via FileUploadErrorTestApi" should {
    "reject a multi-part form data upload containing a binary file" in {
      val resourceUrl = this.getClass().getResource("/uploads/upload-test.jpg")
      val resourcePath: Path = Paths.get(resourceUrl.toURI());
      val payload = MultipartFormData(Seq(BodyPart(resourcePath.toFile, "myfile", MediaTypes.`image/jpeg`)))
      Post("/" + rootApiPath + "/examples/fileupload/error/post", payload) ~> routes ~> check {
        status mustEqual StatusCodes.InternalServerError
        val response = responseAs[String]
        response must not be empty
        response mustEqual "File upload has failed!"
      }
    }
  }

}
