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

import com.github.damianmcdonald.restmagic.configurators.ServeMode.ServeModeType
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import spray.http.HttpMethod
import spray.routing._

object ParameterizedRestErrorConfig extends ConfiguratorUtils {

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher1[String],
    responseData: Map[String, ErrorCode],
    serveMode: ServeModeType
  ): ParameterizedRestErrorConfig = {
    assert(responseData.nonEmpty, ERROR_EMPTY_MAP)
    assert(!serveMode.isInstanceOf[ServeMode.CustomStrategy], ERROR_CUSTOM_STRATEGY_MODE)
    assert(if (responseData.size == 1 && !serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_SINGULAR_MODE)
    assert(if (responseData.size > 1 && serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_MULTI_MODE)
    val directive = httpMethodToDirective(httpMethod)
    new ParameterizedRestErrorConfig(directive, apiPath, responseData, serveMode)
  }

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher1[String],
    responseData: Map[String, ErrorCode],
    serveMode: ServeModeType,
    displayName: String,
    displayUrl: String
  ): ParameterizedRestErrorConfig = {
    assert(responseData.nonEmpty, ERROR_EMPTY_MAP)
    assert(!serveMode.isInstanceOf[ServeMode.CustomStrategy], ERROR_CUSTOM_STRATEGY_MODE)
    assert(if (responseData.size == 1 && !serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_SINGULAR_MODE)
    assert(if (responseData.size > 1 && serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_MULTI_MODE)
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
          responseData.map({
            case (k, v) => {
              val s = v.errorCode.toString() + " -- " + v.errorMessage
              (k -> s)
            }
          }),
          API_TYPE_PARAMETERIZED_REST_ERROR
        )
      )
    }
    new ParameterizedRestErrorConfig(directive, apiPath, responseData, serveMode, registeredApi)
  }

}

/**
 * A ParameterizedRestErrorConfig that can be converted to a Parameterized Rest Error Web Service
 *
 * This class should be created via it's companion object.
 */
case class ParameterizedRestErrorConfig(
  httpMethod: Directive0,
  apiPath: PathMatcher1[String],
  responseData: Map[String, ErrorCode],
  serveMode: ServeModeType,
  registeredApi: Option[RegisteredApi] = None
) extends RootApiConfig
