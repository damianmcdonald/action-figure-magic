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

import com.github.damianmcdonald.restmagic.configurators.DataMode.Inline
import org.specs2.mutable.Specification
import spray.http.HttpMethods._
import spray.http.MediaTypes._

class FileUploadConfigSpec extends Specification {

  "A FileUploadConfig" should {
    "return a valid FileUploadConfig class instance using a minimal constructor" in {
      val fileUploadConfig = FileUploadConfig(
        httpMethod = POST,
        apiPath = "examples",
        produces = `application/json`,
        dataMode = Inline(),
        responseData = """{ "status": "success" }""",
        fileParamName = "myfile"
      )
      fileUploadConfig must not be null
    }
  }

  "A FileUploadConfig" should {
    "return a valid FileUploadConfig class instance using a partial constructor" in {
      val fileUploadConfig = FileUploadConfig(
        httpMethod = POST,
        apiPath = "examples",
        produces = `application/json`,
        dataMode = Inline(),
        responseData = """{ "status": "success" }""",
        fileParamName = "myfile",
        validate = true
      )
      fileUploadConfig must not be null
    }
  }

  "A FileUploadConfig" should {
    "return a valid FileUploadConfig class instance using a full constructor" in {
      val fileUploadConfig = FileUploadConfig(
        httpMethod = POST,
        apiPath = "examples",
        produces = `application/json`,
        dataMode = Inline(),
        responseData = """{ "status": "success" }""",
        fileParamName = "myfile",
        validate = true,
        displayName = "File Upload Post example",
        displayUrl = "/examples/fileupload/post"
      )
      fileUploadConfig must not be null
    }
  }

  "A FileUploadConfig" should {
    "throw an assertion error" in {
      "when a  FileUploadConfig minimal constructor contains empty responseData" in {
        def fileUploadConfig = FileUploadConfig(
          httpMethod = POST,
          apiPath = "examples",
          produces = `application/json`,
          dataMode = Inline(),
          responseData = "",
          fileParamName = "myfile"
        )
        fileUploadConfig must throwA[AssertionError]
      }
    }
  }

  "A FileUploadConfig" should {
    "throw an assertion error" in {
      "when a  FileUploadConfig minimal constructor contains empty fileParamName" in {
        def fileUploadConfig = FileUploadConfig(
          httpMethod = POST,
          apiPath = "examples",
          produces = `application/json`,
          dataMode = Inline(),
          responseData = """{ "status": "success" }""",
          fileParamName = ""
        )
        fileUploadConfig must throwA[AssertionError]
      }
    }
  }

  "A FileUploadConfig" should {
    "throw an assertion error" in {
      "when a  FileUploadConfig partial constructor contains empty responseData" in {
        def fileUploadConfig = FileUploadConfig(
          httpMethod = POST,
          apiPath = "examples",
          produces = `application/json`,
          dataMode = Inline(),
          responseData = "",
          fileParamName = "myfile",
          validate = true
        )
        fileUploadConfig must throwA[AssertionError]
      }
    }
  }

  "A FileUploadConfig" should {
    "throw an assertion error" in {
      "when a  FileUploadConfig partial constructor contains empty fileParamName" in {
        def fileUploadConfig = FileUploadConfig(
          httpMethod = POST,
          apiPath = "examples",
          produces = `application/json`,
          dataMode = Inline(),
          responseData = """{ "status": "success" }""",
          fileParamName = "",
          validate = true
        )
        fileUploadConfig must throwA[AssertionError]
      }
    }
  }

  "A FileUploadConfig" should {
    "throw an assertion error" in {
      "when a  FileUploadConfig full constructor contains empty responseData" in {
        def fileUploadConfig = FileUploadConfig(
          httpMethod = POST,
          apiPath = "examples",
          produces = `application/json`,
          dataMode = Inline(),
          responseData = "",
          fileParamName = "myfile",
          validate = true,
          displayName = "File Upload Post example",
          displayUrl = "/examples/fileupload/post"
        )
        fileUploadConfig must throwA[AssertionError]
      }
    }
  }

  "A FileUploadConfig" should {
    "throw an assertion error" in {
      "when a  FileUploadConfig full constructor contains empty fileParamName" in {
        def fileUploadConfig = FileUploadConfig(
          httpMethod = POST,
          apiPath = "examples",
          produces = `application/json`,
          dataMode = Inline(),
          responseData = """{ "status": "success" }""",
          fileParamName = "",
          validate = true,
          displayName = "File Upload Post example",
          displayUrl = "/examples/fileupload/post"
        )
        fileUploadConfig must throwA[AssertionError]
      }
    }
  }

}
