package com.github.damianmcdonald.actionfiguremagic

import com.github.damianmcdonald.restmagic.configurators.DataMode.{ FileStub, Inline }
import com.github.damianmcdonald.restmagic.configurators.ServeMode.{ ByParam, Random, Singular }
import com.github.damianmcdonald.restmagic.configurators._
import com.github.damianmcdonald.restmagic.configurators.utils.ConfiguratorUtils
import com.github.damianmcdonald.restmagic.system.RegistrableMock
import spray.http.HttpMethods._
import spray.http.MediaTypes._
import spray.http.StatusCodes
import spray.http.StatusCodes._
import spray.routing.Directives._

class CharactersApi extends RegistrableMock with ConfiguratorUtils {

  private val getCharactersByToylineIdApi = ParameterizedRestConfig(
    httpMethod = GET,
    apiPath = "action-figure-magic" / "characters" / MATCH_PARAM,
    produces = `application/json`,
    dataMode = FileStub(),
    Map(
      "1" -> "/toylines/starwars/characters.json",
      "2" -> "/toylines/motu/characters.json",
      "3" -> "/toylines/transformers/characters.json",
      "4" -> "/toylines/mask/characters.json",
      "5" -> "/toylines/gi-joe/characters.json",
      "6" -> "/toylines/tmnt/characters.json"
    ),
    serveMode = ByParam(),
    validate = true,
    displayName = "Gets character data",
    displayUrl = "/action-figure-magic/characters/{{toylineId}}"
  )

  private val putToylineByIdApi = ParameterizedRestConfig(
    httpMethod = PUT,
    apiPath = "action-figure-magic" / "characters" / MATCH_PARAM,
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map("singular" -> """{ "status": "modified" }"""),
    serveMode = Singular(),
    validate = true,
    displayName = "Modifies a character (without an image)",
    displayUrl = "/action-figure-magic/characters/{{characterId}}"
  )

  private val putCharacterByIdWithErrorApi = ParameterizedRestErrorConfig(
    httpMethod = PUT,
    apiPath = "action-figure-magic" / "characters" / "error" / MATCH_PARAM,
    responseData = Map(
      "1" -> ErrorCode(BadRequest, "a bad guy created trouble, BadRequest error"),
      "2" -> ErrorCode(BandwidthLimitExceeded, "a bad guy created trouble, BandwidthLimitExceeded error"),
      "3" -> ErrorCode(GatewayTimeout, "a bad guy created trouble, GatewayTimeout error"),
      "4" -> ErrorCode(NetworkConnectTimeout, "a bad guy created trouble, NetworkConnectTimeout error"),
      "5" -> ErrorCode(RequestUriTooLong, "a bad guy created trouble, RequestUriTooLong error")
    ),
    serveMode = Random(),
    displayName = "Errors on modifying a bad guy character",
    displayUrl = "/action-figure-magic/characters/error/{{characterId}}"
  )

  private val postCharacterApi = FileUploadConfig(
    httpMethod = POST,
    apiPath = "action-figure-magic" / "characters",
    produces = `application/json`,
    dataMode = Inline(),
    responseData = """{ "status": "added/modified" }""",
    fileParamName = "cover",
    validate = true,
    displayName = "Adds/Modifies a character (with an image)",
    displayUrl = "/action-figure-magic/characters"
  )

  private val postCharacterWithErrorApi = FileUploadErrorConfig(
    httpMethod = POST,
    apiPath = "action-figure-magic" / "characters" / "error",
    errorCode = StatusCodes.InternalServerError,
    errorMessage = "a bad guy created trouble, File upload failed!",
    fileParamName = "cover",
    displayName = "Error on add/modify a bad guy (with image)",
    displayUrl = "/action-figure-magic/characters/error"
  )

  private val deleteCharacterByIdApi = ParameterizedRestConfig(
    httpMethod = DELETE,
    apiPath = "action-figure-magic" / "characters" / MATCH_PARAM,
    produces = `application/json`,
    dataMode = Inline(),
    responseData = Map("singular" -> """{ "status": "deleted" }"""),
    serveMode = Singular(),
    validate = true,
    displayName = "Deletes character",
    displayUrl = "/action-figure-magic/characters/{{characterId}}"
  )

  private val deleteCharacterByIdWithErrorApi = ParameterizedRestErrorConfig(
    httpMethod = DELETE,
    apiPath = "action-figure-magic" / "characters" / "error" / MATCH_PARAM,
    responseData = Map(
      "1" -> ErrorCode(BadRequest, "a bad guy created trouble, BadRequest error"),
      "2" -> ErrorCode(BandwidthLimitExceeded, "a bad guy created trouble, BandwidthLimitExceeded error"),
      "3" -> ErrorCode(GatewayTimeout, "a bad guy created trouble, GatewayTimeout error"),
      "4" -> ErrorCode(NetworkConnectTimeout, "a bad guy created trouble, NetworkConnectTimeout error"),
      "5" -> ErrorCode(RequestUriTooLong, "a bad guy created trouble, RequestUriTooLong error")
    ),
    serveMode = Random(),
    displayName = "Errors on deleting a bad guy character",
    displayUrl = "/action-figure-magic/characters/error/{{characterId}}"
  )

  override def getApiConfig = {
    List(
      getCharactersByToylineIdApi,
      putToylineByIdApi,
      putCharacterByIdWithErrorApi,
      postCharacterApi,
      postCharacterWithErrorApi,
      deleteCharacterByIdApi,
      deleteCharacterByIdWithErrorApi
    )
  }
}
