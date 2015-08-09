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
import spray.http.{ MediaTypes, HttpMethod, MediaType }
import spray.routing.{ Directive0, PathMatcher0 }

object AuthenticateConfig extends ConfiguratorUtils {
  def apply(
    httpMethod: HttpMethod,
    securePathPrefix: PathMatcher0,
    authenticatePath: PathMatcher0,
    authorizePath: PathMatcher0,
    credentials: Map[String, String],
    authorizedUsers: List[String]
  ): AuthenticateConfig = {
    assert(credentials.nonEmpty, ERROR_EMPTY_CREDENTIALS)
    assert(authorizedUsers.nonEmpty, ERROR_EMPTY_AUTHORIZED_USERS)
    val directive = httpMethodToDirective(httpMethod)
    new AuthenticateConfig(directive, securePathPrefix, authenticatePath, authorizePath, credentials, authorizedUsers)
  }

  def apply(
    httpMethod: HttpMethod,
    securePathPrefix: PathMatcher0,
    authenticatePath: PathMatcher0,
    authorizePath: PathMatcher0,
    credentials: Map[String, String],
    authorizedUsers: List[String],
    produces: MediaType,
    authenticateResponseData: String,
    authorizeResponseData: String
  ): AuthenticateConfig = {
    assert(credentials.nonEmpty, ERROR_EMPTY_CREDENTIALS)
    assert(authorizedUsers.nonEmpty, ERROR_EMPTY_AUTHORIZED_USERS)
    val directive = httpMethodToDirective(httpMethod)
    new AuthenticateConfig(directive, securePathPrefix, authenticatePath, authorizePath, credentials, authorizedUsers)
  }

  def apply(
    httpMethod: HttpMethod,
    securePathPrefix: PathMatcher0,
    authenticatePath: PathMatcher0,
    authorizePath: PathMatcher0,
    credentials: Map[String, String],
    authorizedUsers: List[String],
    produces: MediaType,
    authenticateResponseData: String,
    authorizeResponseData: String,
    displayName: String,
    displayUrl: String
  ): AuthenticateConfig = {
    assert(credentials.nonEmpty, ERROR_EMPTY_CREDENTIALS)
    assert(authorizedUsers.nonEmpty, ERROR_EMPTY_AUTHORIZED_USERS)
    val directive = httpMethodToDirective(httpMethod)
    val registeredApi = {
      Option(
        new RegisteredApi(
          displayName,
          displayUrl,
          httpMethod.toString,
          MediaTypes.`text/plain`.toString,
          dataModeTypeToString(DataMode.Inline()),
          serveModeTypeToString(ServeMode.Singular()),
          Map("singular" -> "Auth responses"),
          API_TYPE_AUTHENTICATE
        )
      )
    }
    new AuthenticateConfig(directive, securePathPrefix, authenticatePath, authorizePath, credentials, authorizedUsers,
      produces, authenticateResponseData, authorizeResponseData, registeredApi)
  }
}

case class AuthenticateConfig(
  httpMethod: Directive0,
  securePathPrefix: PathMatcher0,
  authenticatePath: PathMatcher0,
  authorizePath: PathMatcher0,
  credentials: Map[String, String],
  authorizedUsers: List[String],
  produces: MediaType = MediaTypes.`text/plain`,
  authenticateResponseData: String = "Authentication Successful",
  authorizeResponseData: String = "Authorization Successful",
  registeredApi: Option[RegisteredApi] = None
) extends RootApiConfig
