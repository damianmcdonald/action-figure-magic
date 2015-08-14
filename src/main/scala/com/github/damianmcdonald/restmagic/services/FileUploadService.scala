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
import java.nio.file.{ Path, Paths }

import akka.actor.ActorSystem
import akka.event.slf4j.SLF4JLogging
import com.github.damianmcdonald.restmagic.configurators.FileUploadConfig
import com.github.damianmcdonald.restmagic.system.Configuration
import spray.http.StatusCodes
import spray.routing._

class FileUploadService(cfg: FileUploadConfig)(implicit system: ActorSystem)
    extends Directives with RootMockService with SLF4JLogging {

  def getFileName(): String = {
    val name = "rest-magic-file-upload"
    if (Configuration.uploadsDir.isEmpty) {
      val resourceUrl = this.getClass().getResource("/uploads")
      val resourcePath: Path = Paths.get(resourceUrl.toURI())
      resourcePath.toFile.getAbsolutePath + File.separator + name
    } else {
      new File(Configuration.uploadsDir + File.separator + name).getAbsolutePath
    }
  }

  lazy val route =
    cors {
      path(cfg.apiPath) {
        cfg.httpMethod {
          formFields(cfg.fileParamName.as[Array[Byte]]) { (file) =>
            detach() {
              complete {
                val fileName = getFileName
                val result = saveFile(fileName, file)
                log.debug("File Upload >>> Upload successful. File has been uploaded to " + fileName)
                if (result) cfg.responseData else (StatusCodes.InternalServerError, "Upload failed!!")
              }
            }
          }
        }
      }
    }

  def saveFile(fileName: String, content: Array[Byte]): Boolean = {
    saveFile[Array[Byte]](fileName, content, { (is, os) => os.write(is) })
  }

  def saveFile[T](fileName: String, content: T, writeFile: (T, OutputStream) => Unit): Boolean = {
    try {
      val fos = new java.io.FileOutputStream(fileName)
      writeFile(content, fos)
      fos.close()
      true
    } catch {
      case e: Exception => false
    }
  }

}
