package com.github.damianmcdonald.actionfiguremagic

import com.github.damianmcdonald.restmagic.configurators.DataMode.FileStub
import com.github.damianmcdonald.restmagic.configurators.SimpleRestConfig
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.HttpMethods._
import spray.http.MediaTypes._
import spray.routing.Directives._

class EasterEggsApi extends RegistrableMock {

  private val getEasterEggsApi = SimpleRestConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "eastereggs",
    produces = `application/json`,
    dataMode = FileStub(),
    responseData = "/eastereggs.json",
    validate = true,
    displayName = "Gets easter eggs data",
    displayUrl = "/action-figure-magic/eastereggs"
  )

  override def getApiConfig = {
    List(
      getEasterEggsApi
    )
  }

}
