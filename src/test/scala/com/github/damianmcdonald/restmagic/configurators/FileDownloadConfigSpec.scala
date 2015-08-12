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

package com.github.damianmcdonald.restmagic.configurators

import java.io.FileNotFoundException

import com.github.damianmcdonald.restmagic.configurators.BinaryMode.Attachment
import org.specs2.mutable.Specification
import spray.http.HttpMethods._
import spray.http.MediaTypes._

class FileDownloadConfigSpec extends Specification {

  "A FileDownloadConfig" should {
    "return a valid FileDownloadConfig class instance using a minimal constructor" in {
      val fileDownloadConfig = FileDownloadConfig(
        httpMethod = GET,
        apiPath = "examples",
        produces = `application/pdf`,
        filePath = "/sample.pdf"
      )
      fileDownloadConfig must not be null
    }
  }

  "A FileDownloadConfig" should {
    "return a valid FileDownloadConfig class instance using a full constructor" in {
      val fileDownloadConfig = FileDownloadConfig(
        httpMethod = GET,
        apiPath = "examples",
        produces = `application/pdf`,
        filePath = "/sample.pdf",
        binaryMode = Attachment(),
        displayName = "File Download Attachment Get example",
        displayUrl = "/examples/filedownload/attachment"
      )
      fileDownloadConfig must not be null
    }
  }

  "A FileDownloadConfig" should {
    "throw an assertion error" in {
      "when a  FileDownloadConfig minimal constructor contains an empty file path" in {
        def fileDownloadConfig = FileDownloadConfig(
          httpMethod = GET,
          apiPath = "examples",
          produces = `application/pdf`,
          filePath = ""
        )
        fileDownloadConfig must throwA[AssertionError]
      }
    }
  }

  "A FileDownloadConfig" should {
    "throw an assertion error" in {
      "when a  FileDownloadConfig minimal constructor contains an invalid file path" in {
        def fileDownloadConfig = FileDownloadConfig(
          httpMethod = GET,
          apiPath = "examples",
          produces = `application/pdf`,
          filePath = "/some/path/tofile"
        )
        fileDownloadConfig must throwA[FileNotFoundException]
      }
    }
  }

  "A FileDownloadConfig" should {
    "throw an assertion error" in {
      "when a  FileDownloadConfig full constructor contains an empty file path" in {
        def fileDownloadConfig = FileDownloadConfig(
          httpMethod = GET,
          apiPath = "examples",
          produces = `application/pdf`,
          filePath = "",
          binaryMode = Attachment(),
          displayName = "File Download Attachment Get example",
          displayUrl = "/examples/filedownload/attachment"
        )
        fileDownloadConfig must throwA[AssertionError]
      }
    }
  }

  "A FileDownloadConfig" should {
    "throw an assertion error" in {
      "when a  FileDownloadConfig full constructor contains an invalid file path" in {
        def fileDownloadConfig = FileDownloadConfig(
          httpMethod = GET,
          apiPath = "examples",
          produces = `application/pdf`,
          filePath = "/some/path/tofile",
          binaryMode = Attachment(),
          displayName = "File Download Attachment Get example",
          displayUrl = "/examples/filedownload/attachment"
        )
        fileDownloadConfig must throwA[FileNotFoundException]
      }
    }
  }

}
