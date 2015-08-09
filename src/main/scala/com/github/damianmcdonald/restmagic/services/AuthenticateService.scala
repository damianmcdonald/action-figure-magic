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

package com.github.damianmcdonald.restmagic.services

import akka.actor.ActorSystem
import akka.event.slf4j.SLF4JLogging
import com.github.damianmcdonald.restmagic.configurators.AuthenticateConfig
import spray.http.StatusCodes
import spray.routing.Directives
import spray.routing.authentication.{ BasicAuth, UserPass }
import spray.routing.directives.AuthMagnet

import scala.concurrent.{ ExecutionContext, Future }

class UserDetails(val userName: String, val password: String, val isAuthenticated: Boolean)

class AuthenticateService(cfg: AuthenticateConfig)(implicit system: ActorSystem) extends Directives with RootMockService with SLF4JLogging {

  implicit val ec: ExecutionContext = system.dispatcher

  def isCredentialValid(userPass: Option[UserPass]): Boolean = {
    val credentials: Map[String, String] = cfg.credentials

    userPass match {
      case Some(UserPass(user, pass)) => {
        credentials.get(user) match {
          case Some(v) if (v.equals(pass)) => true
          case Some(v) if (!v.equals(pass)) => false
          case None => false
        }
      }
      case _ => false
    }
  }

  def isAuthorizedForSecuredPage(userName: String): Boolean = {
    cfg.authorizedUsers.contains(userName)
  }

  def basicUserAuthenticator(implicit ec: ExecutionContext): AuthMagnet[UserDetails] = {
    def validateUser(userPass: Option[UserPass]): Option[UserDetails] = {
      if (isCredentialValid(userPass)) {
        userPass match {
          case Some(UserPass(user, pass)) => Some(new UserDetails(user, pass, true))
          case None => Some(new UserDetails("", "", false))
        }
      } else {
        Some(new UserDetails("", "", false))
      }
    }

    def authenticator(userPass: Option[UserPass]): Future[Option[UserDetails]] = Future {
      validateUser(userPass)
    }

    BasicAuth(authenticator _, realm = "RestMagic Secure API")
  }

  def myUserPassAuthenticator(userPass: Option[UserPass]): Future[Option[UserDetails]] =
    Future {
      if (isCredentialValid(userPass)) {
        userPass match {
          case Some(UserPass(user, pass)) => Some(new UserDetails(user, pass, true))
          case None => Some(new UserDetails("", "", false))
        }
      } else {
        Some(new UserDetails("", "", false))
      }
    }

  lazy val route =
    pathPrefix(cfg.securePathPrefix) {
      respondWithMediaType(cfg.produces) {
        cfg.httpMethod {
          authenticate(basicUserAuthenticator) { userDetails: UserDetails =>
            path(cfg.authorizePath) {
              // authorize(isAuthorizedForSecuredPage(userDetails.userName)) {
              complete {
                if (isAuthorizedForSecuredPage(userDetails.userName)) {
                  cfg.authorizeResponseData
                } else {
                  (StatusCodes.Forbidden, "Authorization failure!")
                }
              }
            } ~
              path(cfg.authenticatePath) {
                complete {
                  if (userDetails.isAuthenticated) {
                    cfg.authenticateResponseData
                  } else {
                    (StatusCodes.Unauthorized, "Authentication failure!")
                  }
                }
              }
          }
        }
      }
    }

}
