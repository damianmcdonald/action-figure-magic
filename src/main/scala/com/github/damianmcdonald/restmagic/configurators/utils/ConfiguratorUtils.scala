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

package com.github.damianmcdonald.restmagic.configurators.utils

import java.io.{ FileNotFoundException, File }
import java.nio.charset.StandardCharsets
import java.nio.file.{ Paths, Path }

import com.github.damianmcdonald.restmagic.configurators.DataMode.DataModeType
import com.github.damianmcdonald.restmagic.configurators.DataMode.FileStub
import com.github.damianmcdonald.restmagic.configurators.DataMode.Inline
import com.github.damianmcdonald.restmagic.configurators.ServeMode._
import com.github.damianmcdonald.restmagic.system.Configuration
import spray.http.HttpMethod
import spray.http.HttpMethods._
import spray.http.MediaType
import spray.http.MediaTypes._
import spray.routing.Directives._
import spray.routing.{ Directive0, PathMatcher1 }
import akka.event.slf4j.SLF4JLogging

trait ConfiguratorUtils extends SLF4JLogging {

  val ERROR_MULTI_MODE = "Singular serve mode can only be used when the responseData Map contains a single entry. Please use Random or ByParam serve modes"
  val ERROR_EMPTY_MAP = "The responseData map can not be empty. You must provide at least one response entry"
  val ERROR_SINGULAR_MODE = {
    "The selected serve mode can only be used when the responseData Map contains more than one entry." +
      "Please add additional responses to the Map or use Singular serve mode"
  }
  val ERROR_EMPTY_FILE_PARAM_NAME = {
    "The fileParamName can not be empty. " +
      "This value is used identify the file part of a multi part form submission. You must provide a fileParamName"
  }
  val ERROR_FILE_DOWNLOAD_NOT_EXISTS = "The provided filePath can not be resolved to a valid file."
  val ERROR_EMPTY_CREDENTIALS = "The credentials map can not be empty. You must provide at least one user -> password entry"
  val ERROR_EMPTY_AUTHORIZED_USERS = "The authorizedUsers list can not be empty. You must provide at least one authorized user"

  val API_TYPE_SIMPLE_REST = "Simple Rest"
  val API_TYPE_PARAMETERIZED_REST = "Parameterized Rest"
  val API_TYPE_SIMPLE_REST_ERROR = "Simple Rest Error"
  val API_TYPE_PARAMETERIZED_REST_ERROR = "Parameterized Rest Error"
  val API_TYPE_PARAMETERIZED_HTTP_BY_QUERY_STRING = "Parameterized Http By Query String"
  val API_TYPE_PARAMETERIZED_HTTP_BY_FORM_DATA = "Parameterized Http By Form Data"
  val API_TYPE_PARAMETERIZED_HTTP_ERROR_BY_QUERY_STRING = "Parameterized Http Error By Query String"
  val API_TYPE_PARAMETERIZED_HTTP_ERROR_BY_FORM_DATA = "Parameterized Http Error By Form Data"
  val API_TYPE_FILE_UPLOAD = "File Upload"
  val API_TYPE_FILE_UPLOAD_ERROR = "File Upload Error"
  val API_TYPE_FILE_DOWNLOAD = "File Download"
  val API_TYPE_AUTHENTICATE = "Authenticate"

  val MATCH_PARAM = """\w+""".r
  val MATCH_ANY: PathMatcher1[String] = Rest

  def getEmptyFieldMessage(field: String): String = { s"$field can not be empty. You must provide a $field entry" }

  def httpMethodToDirective(httpMethod: HttpMethod): Directive0 = {
    httpMethod match {
      case GET => get
      case POST => post
      case PUT => put
      case DELETE => delete
    }
  }

  def dataModeTypeToString(dataMode: DataModeType): String = {
    dataMode match {
      case FileStub() => "File stub"
      case Inline() => "Inline"
    }
  }

  def serveModeTypeToString(serveMode: ServeModeType): String = {
    serveMode match {
      case Singular() => "Singular"
      case Random() => "Random"
      case ByParam() => "By Parameter"
    }
  }

  protected def fileExists(fileName: String): Boolean = {
    if (Configuration.downloadsDir.isEmpty) {
      val resourceUrl = Option(this.getClass().getResource("/downloads" + fileName))
      val validUrl = resourceUrl.getOrElse(throw new FileNotFoundException("File: " + fileName + " not found"))
      val resourcePath: Path = Paths.get(validUrl.toURI);
      resourcePath.toFile.exists()
    } else {
      new File(Configuration.downloadsDir + File.separator + fileName).exists()
    }
  }

  protected def fileNameToFile(fileName: String): File = {
    if (Configuration.downloadsDir.isEmpty) {
      val resourceUrl = Option(this.getClass().getResource("/downloads" + fileName))
      val validUrl = resourceUrl.getOrElse(throw new FileNotFoundException("File: " + fileName + " not found"))
      val resourcePath: Path = Paths.get(validUrl.toURI);
      resourcePath.toFile
    } else {
      new File(Configuration.downloadsDir + File.separator + fileName)
    }
  }

  private def fileToString(fileName: String): String = {
    if (Configuration.stubsDir.isEmpty) {
      val is = this.getClass().getResourceAsStream("/stubs" + fileName)
      try {
        scala.io.Source.fromInputStream(is, "UTF-8").mkString
      } catch {
        case e: Exception => {
          e.printStackTrace()
          log.error("A problem has occured attempting to read file: " + fileName)
          e.getMessage
        }
      }
    } else {
      scala.io.Source.fromFile(Configuration.stubsDir + fileName).mkString
    }
  }

  protected def validateAndLoadResponses(dataMode: DataModeType, produces: MediaType, s: String): String = {
    val data = dataMode match {
      case FileStub() => fileToString(s)
      case Inline() => s
      case _ => throw new MatchError("Unable to find a valid match for DataModeType")
    }

    produces match {
      case `application/json` => {
        import net.liftweb.json._
        implicit val formats = net.liftweb.json.DefaultFormats
        parse(data) // if the json is not valid we will get an exception here!
      }
      case `text/xml` => {
        scala.xml.XML.loadString(data) // if the xml is not valid we will get an exception here!
      }
      case `text/html` => {
        scala.xml.XML.loadString(data) // if the html is not valid we will get an exception here!
      }
      case _ => log.debug("No validator available for MediaType: " + produces)
    }

    data
  }

  protected def loadResponses(dataMode: DataModeType, s: String): String = {
    dataMode match {
      case FileStub() => fileToString(s)
      case Inline() => s
      case _ => throw new MatchError("Unable to find a valid match for DataModeType")
    }
  }

  protected def validateAndLoadResponses(dataMode: DataModeType, produces: MediaType, xs: Map[String, String]): Map[String, String] = {
    xs map {
      case (key, value) => {
        val data = dataMode match {
          case FileStub() => fileToString(value)
          case Inline() => value
          case _ => throw new MatchError("Unable to find a valid match for DataModeType")
        }
        produces match {
          case `application/json` => {
            import net.liftweb.json._
            implicit val formats = net.liftweb.json.DefaultFormats
            parse(data) // if the json is not valid we will get an exception here!
          }
          case `text/xml` => {
            scala.xml.XML.loadString(data) // if the xml is not valid we will get an exception here!
          }
          case `text/html` => {
            scala.xml.XML.loadString(data) // if the html is not valid we will get an exception here!
          }
          case _ => log.debug("No validator available for MediaType: " + produces)
        }
        (key -> data)
      }
    }
  }

  protected def loadResponses(dataMode: DataModeType, xs: Map[String, String]): Map[String, String] = {
    xs map {
      case (key, value) => {
        val data = dataMode match {
          case FileStub() => fileToString(value)
          case Inline() => value
          case _ => throw new MatchError("Unable to find a valid match for DataModeType")
        }
        (key -> data)
      }
    }
  }
}
