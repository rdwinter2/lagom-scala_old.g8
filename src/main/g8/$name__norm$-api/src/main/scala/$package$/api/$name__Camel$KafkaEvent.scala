package $package$.api

case class $name;format="Camel"$KafkaEvent(event: $name;format="Camel"$EventType,
                          id: UUID,
                          data: Map[String, String] = Map.empty[String, String])

object $name;format="Camel"$KafkaEvent {
  implicit val format: Format[$name;format="Camel"$KafkaEvent] = Json.format
}

object $name;format="Camel"$EventTypes extends Enumeration {
  type $name;format="Camel"$EventType = Value
  val REGISTERED, DELETED, VERIFIED, UNVERIFIED = Value

  implicit val format: Format[$name;format="Camel"$EventType] = enumFormat($name;format="Camel"$EventTypes)
}