package $package$.impl

import $organization$.common.utils.JsonFormats.singletonFormat
import $organization$.common.utils.ErrorResponse
import $package$.api._

import ai.x.play.json.Jsonx
import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import java.util.UUID
import play.api.libs.json.{Format, Json}

trait $name;format="Camel"$Command[R] extends ReplyType[R]

case class Create$name;format="Camel"$Command(
  $name;format="camel"$Id: String,
  naturalKey: String,
  data: String
) extends Create$name;format="Camel"$Command[Create$name;format="Camel"$Response]

object Create$name;format="Camel"$Command {
  implicit val format: Format[Create$name;format="Camel"$Command] = Jsonx.formatCaseClass
}

case class Get$name;format="Camel"$Command(id: String) extends $name;format="Camel"$Command[$name;format="Camel"$State]

object Get$name;format="Camel"$Command {
  implicit val format: Format[Get$name;format="Camel"$Command] =  Jsonx.formatCaseClass
}