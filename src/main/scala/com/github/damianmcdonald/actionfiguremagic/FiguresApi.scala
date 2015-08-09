package com.github.damianmcdonald.actionfiguremagic

import com.github.damianmcdonald.restmagic.configurators.DataMode.FileStub
import com.github.damianmcdonald.restmagic.configurators.SimpleRestConfig
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.HttpMethods._
import spray.http.MediaTypes._
import spray.routing.Directives._

class FiguresApi extends RegistrableMock {

  private val getFiguresApi = SimpleRestConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "figures",
    produces = `application/json`,
    dataMode = FileStub(),
    responseData = "/figures.json",
    validate = true,
    displayName = "Gets figures data",
    displayUrl = "/action-figure-magic/figures"
  )

  override def getApiConfig = {
    List(
      getFiguresApi
    )
  }
}
