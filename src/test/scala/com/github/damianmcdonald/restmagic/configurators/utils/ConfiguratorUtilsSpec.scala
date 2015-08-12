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

import com.github.damianmcdonald.restmagic.configurators.DataMode.{ FileStub, Inline }
import net.liftweb.json.JsonParser.ParseException
import net.liftweb.json._
import org.specs2.mutable.Specification
import org.xml.sax.SAXParseException
import spray.http.MediaTypes._

class ConfiguratorUtilsSpec extends Specification with ConfiguratorUtils {

  implicit val formats = net.liftweb.json.DefaultFormats

  "ConfiguratorUtils" should {
    "validateAndLoadResponses correctly for valid json data" in {
      val response = validateAndLoadResponses(FileStub(), `application/json`, "/examples/simplerest/hello_world.json")
      response must not be empty
      val json = parse(response)
      val value = (json \ "response").extract[String]
      value mustEqual "Hello World"
    }
  }

  "ConfiguratorUtils" should {
    "validateAndLoadResponses correctly for valid xml data" in {
      val response = validateAndLoadResponses(Inline(), `text/xml`, "<root><data>Hello World</data></root>")
      response must not be empty
      val xml = scala.xml.XML.loadString(response)
      val value = (xml \ "data").text
      value mustEqual "Hello World"
    }
  }

  "ConfiguratorUtils" should {
    "validateAndLoadResponses correctly for a map of valid json data" in {
      val response = validateAndLoadResponses(
        FileStub(),
        `application/json`,
        Map(
          "1" -> "/examples/parameterizedrest/he-man.json",
          "2" -> "/examples/parameterizedrest/skeletor.json",
          "3" -> "/examples/parameterizedrest/man-at-arms.json",
          "4" -> "/examples/parameterizedrest/beast-man.json",
          "5" -> "/examples/parameterizedrest/orko.json"
        )
      )
      response must not be empty
      response.foreach({
        case (k, v) =>
          val json = parse(v)
          v must not be empty
      })
      response must have size 5
    }
  }

  "ConfiguratorUtils" should {
    "validateAndLoadResponses correctly for valid xml data" in {
      val response = validateAndLoadResponses(Inline(), `text/xml`, "<root><data>Hello World</data></root>")
      response must not be empty
      val xml = scala.xml.XML.loadString(response)
      val value = (xml \ "data").text
      value mustEqual "Hello World"
    }
  }

  "ConfiguratorUtils" should {
    "throw an assertion error" in {
      "when validateAndLoadResponses attempts to load invalid json data" in {
        def response = validateAndLoadResponses(FileStub(), `application/json`, "/examples/simplerest/hello_world_invalid.json")
        response must throwA[ParseException]
      }
    }
  }

  "ConfiguratorUtils" should {
    "throw an assertion error" in {
      "when validateAndLoadResponses attemts to load invalid xml data" in {
        def response = validateAndLoadResponses(Inline(), `text/xml`, "<root><data>Hello World</invalid1></invalid2>")
        response must throwA[SAXParseException]
      }
    }
  }

  "ConfiguratorUtils" should {
    "throw an assertion error" in {
      "when validateAndLoadResponses attempts to load invalid json data from map entries" in {
        def response = validateAndLoadResponses(
          FileStub(),
          `application/json`,
          Map(
            "1" -> "/examples/parameterizedrest/he-man.json",
            "2" -> "/examples/parameterizedrest/skeletor_invalid.json",
            "3" -> "/examples/parameterizedrest/man-at-arms.json",
            "4" -> "/examples/parameterizedrest/beast-man.json",
            "5" -> "/examples/parameterizedrest/orko.json"
          )
        )
        response must throwA[ParseException]
      }
    }
  }

}
