package $package$.impl

import $package$.api._

import play.api.libs.json.{Format, Json}

case class $name;format="Camel"$State($name;format="camel"$: Option[$name;format="Camel"$]) {
}

object $name;format="Camel"$State {
  implicit val format: Format[$name;format="Camel"$State] = Json.format
  val newEntity = $name;format="Camel"$State(None)
}