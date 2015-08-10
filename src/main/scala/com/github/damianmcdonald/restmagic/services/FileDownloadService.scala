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
import com.github.damianmcdonald.restmagic.configurators.BinaryMode.{ Inline, Attachment, BinaryModeType }
import com.github.damianmcdonald.restmagic.configurators.{ FileDownloadConfig, FileUploadConfig }
import com.github.damianmcdonald.restmagic.system.Configuration
import spray.http.HttpHeaders.{ `Content-Disposition`, `Content-Type` }
import spray.http._
import spray.routing._
import org.apache.commons.io.FileUtils

import scala.util.Random

class FileDownloadService(cfg: FileDownloadConfig)(implicit system: ActorSystem)
    extends Directives with RootMockService with SLF4JLogging {

  lazy val route =
    path(cfg.apiPath) {
      cfg.httpMethod {
        respondWithHeaders(getBinaryHeaders(cfg.binaryMode, cfg.produces, cfg.filePath.getName)) {
          complete {
            val bytes: Array[Byte] = FileUtils.readFileToByteArray(cfg.filePath);
            HttpResponse(entity = HttpEntity(cfg.produces, HttpData(bytes)))
          }
        }
      }
    }

  def getBinaryHeaders(binaryMode: BinaryModeType, produces: MediaType, fileName: String): List[HttpHeader] = {
    val contentType = `Content-Type`(cfg.produces)
    val contentDisposition = {
      val binary = {
        binaryMode match {
          case Inline() => "inline"
          case Attachment() => "attachment"
        }
      }
      `Content-Disposition`(binary, Map("filename" -> fileName))
    }
    List(contentType, contentDisposition)
  }

}
