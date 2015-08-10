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
import com.github.damianmcdonald.restmagic.configurators.ServeMode._
import com.github.damianmcdonald.restmagic.configurators.ParameterizedRestConfig
import spray.routing.Directives
import shapeless.HList
import com.github.damianmcdonald.restmagic.configurators.ParameterizedRestErrorConfig
import com.github.damianmcdonald.restmagic.configurators.ErrorCode
import spray.http.StatusCodes._

class ParameterizedRestErrorService(cfg: ParameterizedRestErrorConfig)(implicit system: ActorSystem)
    extends Directives with RootMockService with SLF4JLogging {

  lazy val route =
    path(cfg.apiPath) { param =>
      cfg.httpMethod {
        complete {
          val error = cfg.serveMode match {
            case Singular() => serveSingularError(cfg.responseData)
            case Random() => serveRandomError(cfg.responseData)
            case ByParam() => serveByParamError(param.toString, cfg.responseData)
          }
          (error.errorCode, error.errorMessage)
        }
      }
    }

}
