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

import java.io.File

import com.github.damianmcdonald.restmagic.configurators.BinaryMode.BinaryModeType
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import spray.http.{ HttpMethod, MediaType }
import spray.routing.{ Directive0, PathMatcher0 }

object FileDownloadConfig extends ConfiguratorUtils {

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    produces: MediaType,
    filePath: String
  ): FileDownloadConfig = {
    assert(!filePath.isEmpty, getEmptyFieldMessage("filePath"))
    assert(fileExists(filePath), ERROR_FILE_DOWNLOAD_NOT_EXISTS + ": " + filePath)
    val directive = httpMethodToDirective(httpMethod)
    new FileDownloadConfig(directive, apiPath, produces, fileNameToFile(filePath), BinaryMode.Attachment())
  }

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    produces: MediaType,
    filePath: String,
    binaryMode: BinaryModeType,
    displayName: String,
    displayUrl: String
  ): FileDownloadConfig = {
    assert(!filePath.isEmpty, getEmptyFieldMessage("filePath"))
    assert(fileExists(filePath), ERROR_FILE_DOWNLOAD_NOT_EXISTS + ": " + filePath)
    val directive = httpMethodToDirective(httpMethod)
    val registeredApi = {
      Option(
        new RegisteredApi(
          displayName,
          displayUrl,
          httpMethod.toString,
          produces.toString,
          dataModeTypeToString(DataMode.FileStub()),
          serveModeTypeToString(ServeMode.Singular()),
          Map("singular" -> "Binary File"),
          API_TYPE_FILE_DOWNLOAD
        )
      )
    }
    new FileDownloadConfig(directive, apiPath, produces, fileNameToFile(filePath), binaryMode, registeredApi)
  }
}

/**
 * A FileDownloadConfig that can be converted to a File Download Web Service
 *
 * This class should be created via it's companion object.
 */
case class FileDownloadConfig(
  httpMethod: Directive0,
  apiPath: PathMatcher0,
  produces: MediaType,
  filePath: File,
  binaryMode: BinaryModeType,
  registeredApi: Option[RegisteredApi] = None
) extends RootApiConfig
