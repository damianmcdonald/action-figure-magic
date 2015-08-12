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

import com.github.damianmcdonald.restmagic.configurators.DataMode.DataModeType
import com.github.damianmcdonald.restmagic.configurators.ServeMode.ServeModeType
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import spray.http.{ HttpMethod, MediaType }
import spray.routing.{ Directive0, PathMatcher1 }

object ParameterizedRestConfig extends ConfiguratorUtils {

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher1[String],
    produces: MediaType,
    dataMode: DataModeType,
    responseData: Map[String, String],
    serveMode: ServeModeType
  ): ParameterizedRestConfig = {
    assert(responseData.nonEmpty, ERROR_EMPTY_MAP)
    assert(if (responseData.size == 1 && !serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_SINGULAR_MODE)
    assert(if (responseData.size > 1 && serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_MULTI_MODE)
    val directive = httpMethodToDirective(httpMethod)
    val validatedMap = validateAndLoadResponses(dataMode, produces, responseData)
    new ParameterizedRestConfig(directive, apiPath, produces, dataMode, validatedMap, serveMode)
  }

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher1[String],
    produces: MediaType,
    dataMode: DataModeType,
    responseData: Map[String, String],
    serveMode: ServeModeType,
    validate: Boolean
  ): ParameterizedRestConfig = {
    assert(responseData.nonEmpty, ERROR_EMPTY_MAP)
    assert(if (responseData.size == 1 && !serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_SINGULAR_MODE)
    assert(if (responseData.size > 1 && serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_MULTI_MODE)
    val directive = httpMethodToDirective(httpMethod)
    val validatedMap = if (validate) validateAndLoadResponses(dataMode, produces, responseData) else loadResponses(dataMode, responseData)
    new ParameterizedRestConfig(directive, apiPath, produces, dataMode, validatedMap, serveMode)
  }

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher1[String],
    produces: MediaType,
    dataMode: DataModeType,
    responseData: Map[String, String],
    serveMode: ServeModeType,
    validate: Boolean,
    displayName: String,
    displayUrl: String
  ): ParameterizedRestConfig = {
    assert(responseData.nonEmpty, ERROR_EMPTY_MAP)
    assert(if (responseData.size == 1 && !serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_SINGULAR_MODE)
    assert(if (responseData.size > 1 && serveMode.isInstanceOf[ServeMode.Singular]) false else true, ERROR_MULTI_MODE)
    val directive = httpMethodToDirective(httpMethod)
    val validatedMap = if (validate) validateAndLoadResponses(dataMode, produces, responseData) else loadResponses(dataMode, responseData)
    val registeredApi = {
      Option(
        new RegisteredApi(
          displayName,
          displayUrl,
          httpMethod.toString,
          produces.toString,
          dataModeTypeToString(DataMode.Inline()),
          serveModeTypeToString(ServeMode.Singular()),
          validatedMap,
          API_TYPE_PARAMETERIZED_REST
        )
      )
    }
    new ParameterizedRestConfig(directive, apiPath, produces, dataMode, validatedMap, serveMode, registeredApi)
  }
}

/**
 * A ParameterizedRestConfig that can be converted to a Parameterized Rest Web Service
 *
 * This class should be created via it's companion object.
 */
case class ParameterizedRestConfig(
  httpMethod: Directive0,
  apiPath: PathMatcher1[String],
  produces: MediaType,
  dataMode: DataModeType,
  responseData: Map[String, String],
  serveMode: ServeModeType,
  registeredApi: Option[RegisteredApi] = None
) extends RootApiConfig
