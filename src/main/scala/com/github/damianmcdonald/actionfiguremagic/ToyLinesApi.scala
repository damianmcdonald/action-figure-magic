package com.github.damianmcdonald.actionfiguremagic

import com.github.damianmcdonald.restmagic.configurators.BinaryMode.Attachment
import com.github.damianmcdonald.restmagic.configurators.DataMode.{ FileStub, Inline }
import com.github.damianmcdonald.restmagic.configurators.ServeMode.Singular
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import com.github.damianmcdonald.restmagic.configurators.{ FileDownloadConfig, FileUploadConfig, ParameterizedRestConfig, SimpleRestConfig }
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.HttpMethods._
import spray.http.MediaTypes._
import spray.routing.Directives._

class ToyLinesApi extends RegistrableMock with ConfiguratorUtils {

  private val getToylinesApi = SimpleRestConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "toylines",
    produces = `application/json`,
    dataMode = FileStub(),
    responseData = "/toylines.json",
    validate = true,
    displayName = "Gets toylines data",
    displayUrl = "/action-figure-magic/toylines"
  )

  private val putToylineByIdApi = ParameterizedRestConfig(
    httpMethod = PUT,
    apiPath = "action-figure-magic" / "toylines" / MATCH_PARAM,
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map("singular" -> """{ "status": "modified" }"""),
    serveMode = Singular(),
    validate = true,
    displayName = "Modifies a toyline (without an image)",
    displayUrl = "/action-figure-magic/toylines/{{toylineId}}"
  )

  private val postToylineApi = FileUploadConfig(
    httpMethod = POST,
    apiPath = "action-figure-magic" / "toylines",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = """{ "status": "added/modified" }""",
    fileParamName = "cover",
    validate = true,
    displayName = "Adds/Modifies a toyline (with an image)",
    displayUrl = "/action-figure-magic/toylines"
  )

  private val deleteToylineByIdApi = ParameterizedRestConfig(
    httpMethod = DELETE,
    apiPath = "action-figure-magic" / "toylines" / MATCH_PARAM,
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map("singular" -> """{ "status": "deleted" }"""),
    serveMode = Singular(),
    validate = true,
    displayName = "Deletes toyline",
    displayUrl = "/action-figure-magic/toylines/{{toylineId}}"
  )

  private val getStarWarsDownloadApi = FileDownloadConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "toylines" / "download" / "1",
    produces = `application/pdf`,
    filePath = "/starwars.pdf",
    binaryMode = Attachment(),
    displayName = "Get Star Wars binary download",
    displayUrl = "action-figure-magic/toylines/download/1"
  )

  private val getMotuDownloadApi = FileDownloadConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "toylines" / "download" / "2",
    produces = `application/pdf`,
    filePath = "/motu.pdf",
    binaryMode = Attachment(),
    displayName = "Get Masters of the Universe binary download",
    displayUrl = "action-figure-magic/toylines/download/2"
  )

  private val getTransformersDownloadApi = FileDownloadConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "toylines" / "download" / "3",
    produces = `application/pdf`,
    filePath = "/transformers.pdf",
    binaryMode = Attachment(),
    displayName = "Get Transformers binary download",
    displayUrl = "action-figure-magic/toylines/download/3"
  )

  private val getMaskDownloadApi = FileDownloadConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "toylines" / "download" / "4",
    produces = `application/pdf`,
    filePath = "/mask.pdf",
    binaryMode = Attachment(),
    displayName = "Get M.A.S.K. binary download",
    displayUrl = "action-figure-magic/toylines/download/4"
  )

  private val getGiJoeDownloadApi = FileDownloadConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "toylines" / "download" / "5",
    produces = `application/pdf`,
    filePath = "/gi-joe.pdf",
    binaryMode = Attachment(),
    displayName = "Get G.I. Joe binary download",
    displayUrl = "action-figure-magic/toylines/download/5"
  )

  private val getTmntDownloadApi = FileDownloadConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "toylines" / "download" / "6",
    produces = `application/pdf`,
    filePath = "/tmnt.pdf",
    binaryMode = Attachment(),
    displayName = "Get TMNT binary download",
    displayUrl = "action-figure-magic/toylines/download/6"
  )

  override def getApiConfig = {
    List(
      getToylinesApi,
      putToylineByIdApi,
      postToylineApi,
      deleteToylineByIdApi,
      getStarWarsDownloadApi,
      getMotuDownloadApi,
      getTransformersDownloadApi,
      getMaskDownloadApi,
      getGiJoeDownloadApi,
      getTmntDownloadApi
    )
  }

}
