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

import com.github.damianmcdonald.restmagic.configurators.FormMode.{ ByQueryString, FormModeType }
import com.github.damianmcdonald.restmagic.configurators.ServeMode.ServeModeType
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import spray.http.HttpMethod
import spray.routing._

object ParameterizedHttpErrorConfig extends ConfiguratorUtils {

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    responseData: Map[String, ErrorCode],
    paramName: String,
    serveMode: ServeModeType,
    formMode: FormModeType
  ): ParameterizedHttpErrorConfig = {
    assert(responseData.nonEmpty, ERROR_EMPTY_MAP)
    assert(if (responseData.size == 1 && !serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_SINGULAR_MODE)
    assert(if (responseData.size > 1 && serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_MULTI_MODE)
    val directive = httpMethodToDirective(httpMethod)
    new ParameterizedHttpErrorConfig(directive, apiPath, responseData, paramName, serveMode, formMode)
  }

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    responseData: Map[String, ErrorCode],
    serveMode: ServeModeType,
    formMode: FormModeType,
    paramName: String,
    displayName: String,
    displayUrl: String
  ): ParameterizedHttpErrorConfig = {
    assert(responseData.nonEmpty, ERROR_EMPTY_MAP)
    assert(if (responseData.size == 1 && !serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_SINGULAR_MODE)
    assert(if (responseData.size > 1 && serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_MULTI_MODE)
    val directive = httpMethodToDirective(httpMethod)
    val registeredApi = {
      Option(
        new RegisteredApi(
          displayName,
          displayUrl,
          httpMethod.toString,
          "",
          "",
          serveModeTypeToString(ServeMode.Singular()),
          responseData.map({ case (k, v) => (k -> v.toString) }),
          if (formMode.isInstanceOf[ByQueryString]) API_TYPE_PARAMETERIZED_HTTP_ERROR_BY_QUERY_STRING else API_TYPE_PARAMETERIZED_HTTP_ERROR_BY_FORM_DATA,
          Option(paramName)
        )
      )
    }
    new ParameterizedHttpErrorConfig(directive, apiPath, responseData, paramName, serveMode, formMode, registeredApi)
  }
}

/**
 * A ParameterizedHttpErrorConfig that can be converted to a Parameterized Http Error Web Service
 *
 * This class should be created via it's companion object.
 */
case class ParameterizedHttpErrorConfig(
  httpMethod: Directive0,
  apiPath: PathMatcher0,
  responseData: Map[String, ErrorCode],
  paramName: String,
  serveMode: ServeModeType,
  formMode: FormModeType,
  registeredApi: Option[RegisteredApi] = None
) extends RootApiConfig
