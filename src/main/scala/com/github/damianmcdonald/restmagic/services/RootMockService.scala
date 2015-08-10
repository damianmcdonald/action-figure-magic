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

import scala.util.Random
import spray.util.LoggingContext
import spray.routing._
import spray.http._
import StatusCodes._
import Directives._
import com.github.damianmcdonald.restmagic.configurators.ErrorCode
import com.github.damianmcdonald.restmagic.exceptions.HttpParameterMissingException

trait RootMockService {

  private def getRandomInt(x: Int, y: Int) = {
    Random.nextInt((y - x) + 1) + x
  }

  protected def serveRandom(xs: Map[String, String]): String = {
    val index = getRandomInt(0, xs.size - 1)
    def inner(xs: List[String], accum: Int): String = {
      xs match {
        case Nil => xs.headOption.getOrElse("No response data available in responseData Map").toString
        case head :: tail if (accum == index) => head
        case head :: tail => inner(tail, accum + 1)
      }
    }
    inner(xs.values.toList, 0)
  }

  protected def serveSingular(xs: Map[String, String]): String = {
    xs.values.headOption.getOrElse("No response data available in responseData Map").toString
  }

  protected def serveByParam(index: String, xs: Map[String, String]): String = {
    val headVal = xs.values.headOption.getOrElse("No response data available in responseData Map").toString
    xs.getOrElse(index, headVal)
  }

  protected def serveRandomError(xs: Map[String, ErrorCode]): ErrorCode = {
    val index = getRandomInt(0, xs.size - 1)
    def inner(xs: List[ErrorCode], accum: Int): ErrorCode = {
      xs match {
        case Nil => xs.headOption.getOrElse(ErrorCode(InternalServerError, "Default 500 Internal Server Error"))
        case head :: tail if (accum == index) => head
        case head :: tail => inner(tail, accum + 1)
      }
    }
    inner(xs.values.toList, 0)
  }

  protected def serveSingularError(xs: Map[String, ErrorCode]): ErrorCode = {
    xs.values.headOption.getOrElse(ErrorCode(InternalServerError, "Default 500 Internal Server Error"))
  }

  protected def serveByParamError(index: String, xs: Map[String, ErrorCode]): ErrorCode = {
    val headVal = xs.values.headOption.getOrElse(ErrorCode(InternalServerError, "Default 500 Internal Server Error"))
    xs.getOrElse(index, headVal)
  }

  def route: Route
}
