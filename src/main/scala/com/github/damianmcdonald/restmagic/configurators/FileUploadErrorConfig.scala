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

import com.github.damianmcdonald.restmagic.configurators.FileUploadConfig._
import spray.routing.{ Directive0, PathMatcher0 }
import spray.http.StatusCode
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import spray.http.HttpMethod

object FileUploadErrorConfig extends ConfiguratorUtils {

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    errorCode: StatusCode,
    errorMessage: String,
    fileParamName: String
  ): FileUploadErrorConfig = {
    assert(!fileParamName.isEmpty, ERROR_EMPTY_FILE_PARAM_NAME)
    val directive = httpMethodToDirective(httpMethod)
    new FileUploadErrorConfig(directive, apiPath, errorCode, errorMessage, fileParamName)
  }

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    errorCode: StatusCode,
    errorMessage: String,
    fileParamName: String,
    displayName: String,
    displayUrl: String
  ): FileUploadErrorConfig = {
    assert(!fileParamName.isEmpty, ERROR_EMPTY_FILE_PARAM_NAME)
    val directive = httpMethodToDirective(httpMethod)
    val registeredApi = {
      Option(
        new RegisteredApi(
          displayName,
          displayUrl,
          httpMethod.toString,
          "Http errors",
          dataModeTypeToString(DataMode.Inline()),
          serveModeTypeToString(ServeMode.Singular()),
          Map(errorCode.toString -> errorMessage),
          API_TYPE_FILE_UPLOAD_ERROR
        )
      )
    }
    new FileUploadErrorConfig(directive, apiPath, errorCode, errorMessage, fileParamName, registeredApi)
  }

}

case class FileUploadErrorConfig(
  httpMethod: Directive0,
  apiPath: PathMatcher0,
  errorCode: StatusCode,
  errorMessage: String,
  fileParamName: String,
  registeredApi: Option[RegisteredApi] = None
) extends RootApiConfig
