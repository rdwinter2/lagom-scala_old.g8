package $package$.api

import $organization$.common.regex.Matchers
import $organization$.common.validation.ValidationViolationKeys._

import play.api.libs.json.{Format, Json}
import com.wix.accord.dsl._
import java.util.UUID
import ai.x.play.json.Jsonx

case class Create$name;format="Camel"$Request(
  naturalKey: String,
  data: String
)

object Create$name;format="Camel"$Request {
  implicit val format: Format[Create$name;format="Camel"$Request] = Jsonx.formatCaseClass

  implicit val Create$name;format="Camel"$RequestValidator = validator[Create$name;format="Camel"$Request] {u =>
    u.naturalKey as notEmptyKey("naturalKey") is notEmpty
//    u.naturalKey as matchRegexFullyKey("naturalKey") should matchRegexFully(Matchers.naturalKey)
  }
}