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

import com.github.damianmcdonald.restmagic.configurators.BinaryMode._
import com.github.damianmcdonald.restmagic.configurators._
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import com.sun.xml.internal.ws.api.message.AttachmentEx
import spray.http.HttpMethods._
import spray.http.MediaTypes._
import spray.routing.Directives._

class FileDownloadTestApi extends RegistrableMock {

  private val rootApiPath = "restmagic"

  private val getFileDownloadAttachmentExampleApi = FileDownloadConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "filedownload" / "attachment",
    produces = `application/pdf`,
    filePath = "/sample.pdf",
    binaryMode = Attachment(),
    displayName = "File Download Attachment Get example",
    displayUrl = "/" + rootApiPath + "/examples/filedownload/attachment"
  )

  private val getFileDownloadInlineExampleApi = FileDownloadConfig(
    httpMethod = GET,
    apiPath = rootApiPath / "examples" / "filedownload" / "inline",
    produces = `application/pdf`,
    filePath = "/sample.pdf",
    binaryMode = Inline(),
    displayName = "File Download Inline Get example",
    displayUrl = "/" + rootApiPath + "/examples/filedownload/inline"
  )

  def getApiConfig: List[RootApiConfig] = {
    List(
      getFileDownloadAttachmentExampleApi,
      getFileDownloadInlineExampleApi
    )
  }

}
