package $package$.api

import $organization$.common.regex.Matchers
import $organization$.common.validation.ValidationViolationKeys._

import play.api.libs.json.{Format, Json}
import com.wix.accord.dsl._
import java.util.UUID

case class Get$name;format="Camel"$Response(id: String)

object Get$name;format="Camel"$Response {
  implicit val format: Format[Get$name;format="Camel"$Response] = Json.format
}