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
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import spray.http.{ HttpMethod, MediaType }
import spray.routing.{ Directive0, PathMatcher0 }

object SimpleRestConfig extends ConfiguratorUtils {

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    produces: MediaType,
    dataMode: DataModeType,
    responseData: String
  ): SimpleRestConfig = {
    assert(!responseData.isEmpty, getEmptyFieldMessage("responseData"))
    val validatedResponse = validateAndLoadResponses(dataMode, produces, responseData)
    val directive = httpMethodToDirective(httpMethod)
    new SimpleRestConfig(directive, apiPath, produces, dataMode, validatedResponse)
  }

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    produces: MediaType,
    dataMode: DataModeType,
    responseData: String,
    validate: Boolean
  ): SimpleRestConfig = {
    assert(!responseData.isEmpty, getEmptyFieldMessage("responseData"))
    val validatedResponse = if (validate) validateAndLoadResponses(dataMode, produces, responseData) else loadResponses(dataMode, responseData)
    val directive = httpMethodToDirective(httpMethod)
    new SimpleRestConfig(directive, apiPath, produces, dataMode, validatedResponse)
  }

  def apply(
    httpMethod: HttpMethod,
    apiPath: PathMatcher0,
    produces: MediaType,
    dataMode: DataModeType,
    responseData: String,
    validate: Boolean,
    displayName: String,
    displayUrl: String
  ): SimpleRestConfig = {
    assert(!responseData.isEmpty, getEmptyFieldMessage("responseData"))
    val validatedResponse = if (validate) validateAndLoadResponses(dataMode, produces, responseData) else loadResponses(dataMode, responseData)
    val directive = httpMethodToDirective(httpMethod)
    val registeredApi = {
      Option(
        new RegisteredApi(
          displayName,
          displayUrl,
          httpMethod.toString,
          produces.toString,
          dataModeTypeToString(dataMode),
          serveModeTypeToString(ServeMode.Singular()),
          Map("singular" -> validatedResponse),
          API_TYPE_SIMPLE_REST
        )
      )
    }
    new SimpleRestConfig(directive, apiPath, produces, dataMode, validatedResponse, registeredApi)
  }
}

/**
 * A SimpleRestConfig that can be converted to a Simple Rest Web Service
 *
 * This class should be created via it's companion object.
 */
case class SimpleRestConfig(
  httpMethod: Directive0,
  apiPath: PathMatcher0,
  produces: MediaType,
  dataMode: DataModeType,
  responseData: String,
  registeredApi: Option[RegisteredApi] = None
) extends RootApiConfig
