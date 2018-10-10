package $package$.api

import play.api.libs.json.{Format, Json}

case class HypermediaResponse(
  link: String
)

object HypermediaResponse {
  implicit val format: Format[HypermediaResponse] = Json.format
}