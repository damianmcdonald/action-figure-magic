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

package com.github.damianmcdonald.restmagic.system

import com.github.damianmcdonald.restmagic.services.RootMockService
import spray.http.HttpHeaders._
import spray.http.HttpMethods._
import spray.http.{ AllOrigins, HttpMethod, HttpMethods, HttpResponse }
import spray.routing._

// https://gist.github.com/joseraya/176821d856b43b1cfe19
trait CORSSupport {
  this: RootMockService =>

  private val allowOriginHeader = `Access-Control-Allow-Origin`(AllOrigins)
  private val optionsCorsHeaders = List(
    `Access-Control-Allow-Headers`("Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Accept-Language, Host, Referer, User-Agent"),
    `Access-Control-Max-Age`(1728000)
  )

  def cors[T]: Directive0 = mapRequestContext { ctx =>
    ctx.withRouteResponseHandling({
      //It is an option request for a resource that responds to some other method
      case Rejected(x) if (ctx.request.method.equals(HttpMethods.OPTIONS) && x.exists(_.isInstanceOf[MethodRejection])) => {
        val allowedMethods: List[HttpMethod] = x.collect { case rejection: MethodRejection => rejection.supported }
        ctx.complete(HttpResponse().withHeaders(
          `Access-Control-Allow-Methods`(OPTIONS, allowedMethods: _*) :: allowOriginHeader ::
            optionsCorsHeaders
        ))
      }
    }).withHttpResponseHeadersMapped { headers =>
      allowOriginHeader :: headers

    }
  }
}
