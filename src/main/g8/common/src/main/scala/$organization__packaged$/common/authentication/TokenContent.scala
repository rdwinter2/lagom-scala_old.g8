package $organization$.common.authentication

import java.util.UUID

import play.api.libs.json.{Format, Json}

// [JWT(https://tools.ietf.org/html/rfc7519)

case class TokenContent(clientId: UUID, userId: UUID, username: String, isRefreshToken: Boolean = false)
object TokenContent {
  implicit val format: Format[TokenContent] = Json.format
}