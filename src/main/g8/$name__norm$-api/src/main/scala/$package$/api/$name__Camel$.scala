package $package$.api

import $organization$.common.utils.JsonFormats._

import ai.x.play.json.Jsonx
import com.lightbend.lagom.scaladsl.api.deser.PathParamSerializer
import java.time.{Duration, Instant}
import java.util.UUID
import play.api.libs.json.{Format, Json}

case class $name;format="Camel"$(
  $name;format="camel"$Id: String,
  naturalKey: String,
  data: String
)

object $name;format="Camel"$ {
  implicit val format: Format[$name;format="Camel"$] = Jsonx.formatCaseClass
}