package $package$.api.response

case class HypermediaResponse(
  link: String
)

object HypermediaResponse {
  implicit val format: Format[HypermediaResponse] = Json.format
}