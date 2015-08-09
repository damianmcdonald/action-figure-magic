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

import spray.routing.{ Directive0, PathMatcher0 }
import spray.http.StatusCode
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import spray.http.HttpMethod

object SimpleRestErrorConfig extends ConfiguratorUtils {

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    errorCode: StatusCode,
    errorMessage: String
  ): SimpleRestErrorConfig = {
    val directive = httpMethodToDirective(httpMethod)
    new SimpleRestErrorConfig(directive, apiPath, errorCode, errorMessage)
  }

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    errorCode: StatusCode,
    errorMessage: String,
    displayName: String,
    displayUrl: String
  ): SimpleRestErrorConfig = {
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
          API_TYPE_SIMPLE_REST_ERROR
        )
      )
    }
    new SimpleRestErrorConfig(directive, apiPath, errorCode, errorMessage, registeredApi)
  }

}

case class SimpleRestErrorConfig(
  httpMethod: Directive0,
  apiPath: PathMatcher0,
  errorCode: StatusCode,
  errorMessage: String,
  registeredApi: Option[RegisteredApi] = None
) extends RootApiConfig
