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

import java.io._

import akka.actor.ActorSystem
import akka.event.slf4j.SLF4JLogging
import com.github.damianmcdonald.restmagic.configurators.{ FileUploadErrorConfig, FileUploadConfig }
import com.github.damianmcdonald.restmagic.system.Configuration
import spray.http.StatusCodes
import spray.routing._

import scala.util.Random

class FileUploadErrorService(cfg: FileUploadErrorConfig)(implicit system: ActorSystem) extends Directives with RootMockService with SLF4JLogging {

  lazy val route =
    path(cfg.apiPath) {
      post {
        formFields(cfg.fileParamName.as[Array[Byte]]) { (file) =>
          detach() {
            complete {
              (cfg.errorCode, cfg.errorMessage)
            }
          }
        }
      }
    }

}
