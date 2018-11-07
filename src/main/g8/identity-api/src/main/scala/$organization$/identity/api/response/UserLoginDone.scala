package $organization$.identity.api.response

import play.api.libs.json.{Format, Json}

case class UserLoginDone(
  authToken: String,
  refreshToken: String
)

object UserLoginDone {
  implicit val format: Format[UserLoginDone] = Json.format
}