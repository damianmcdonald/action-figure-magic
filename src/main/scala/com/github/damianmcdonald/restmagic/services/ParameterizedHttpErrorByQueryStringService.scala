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

import com.github.damianmcdonald.restmagic.configurators.{ ErrorCode, ParameterizedHttpErrorConfig, ParameterizedHttpConfig }
import com.github.damianmcdonald.restmagic.configurators.ServeMode.ByParam
import com.github.damianmcdonald.restmagic.configurators.ServeMode.Random
import com.github.damianmcdonald.restmagic.configurators.ServeMode.Singular
import com.github.damianmcdonald.restmagic.exceptions._
import akka.actor.ActorSystem
import akka.event.slf4j.SLF4JLogging
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import spray.routing.Directives
import spray.routing.directives.ParamDefMagnet.apply
import spray.http.StatusCodes._
import spray.util.LoggingContext
import spray.routing.ExceptionHandler

class ParameterizedHttpErrorByQueryStringService(cfg: ParameterizedHttpErrorConfig)(implicit system: ActorSystem)
    extends Directives with RootMockService with SLF4JLogging {

  lazy val route =
    path(cfg.apiPath) {
      cfg.httpMethod {
        parameter(cfg.paramName ?) { query =>
          complete {
            val error = cfg.serveMode match {
              case Singular() => serveSingularError(cfg.responseData)
              case Random() => serveRandomError(cfg.responseData)
              case ByParam() => {
                query match {
                  case Some(s) => serveByParamError(s, cfg.responseData)
                  case None => ErrorCode(BadRequest, "Parameter: " + cfg.paramName + " is missing!")
                }
              }
            }
            (error.errorCode, error.errorMessage)
          }
        }
      }
    }

}
