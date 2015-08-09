package com.github.damianmcdonald.actionfiguremagic

import com.github.damianmcdonald.restmagic.configurators.DataMode.FileStub
import com.github.damianmcdonald.restmagic.configurators.{ AuthenticateConfig, SimpleRestConfig }
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.HttpMethods._
import spray.http.MediaTypes._
import spray.routing.Directives._

class LogonApi extends RegistrableMock {

  private val jsonAuthenticateResponse = """{ "status": "authenticated" }"""
  private val jsonAuthorizeResponse = """{ "status": "authorized" }"""

  private val logonApi = AuthenticateConfig(
    httpMethod = POST,
    securePathPrefix = "action-figure-magic" / "logon",
    authenticatePath = "authenticate",
    authorizePath = "authorize",
    credentials = Map("luke" -> "12345", "han" -> "qwerty", "chewbacca" -> "zxcvb", "yoda" -> "54321"),
    authorizedUsers = List("luke", "yoda"),
    produces = `text/plain`,
    authenticateResponseData = jsonAuthenticateResponse,
    authorizeResponseData = jsonAuthorizeResponse,
    displayName = "Authenticates and Authorizes users",
    displayUrl = "/action-figure-magic/logon/authorize"
  )

  override def getApiConfig = {
    List(
      logonApi
    )
  }
}
