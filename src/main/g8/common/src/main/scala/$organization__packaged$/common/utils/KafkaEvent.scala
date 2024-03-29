package $organization$.common.utils

import java.util.UUID

import $organization$.common.utils.JsonFormats.enumFormat
import $organization$.common.utils.AggregateClasses.AggregateClass
import $organization$.common.utils.Events.Event
import play.api.libs.json.{Format, Json}

case class KafkaEvent(event: Event,
                      AggregateClass: AggregateClass,
                      id: UUID,
                      data: Map[String, String] = Map.empty[String, String])
object KafkaEvent {
  implicit val format: Format[KafkaEvent] = Json.format[KafkaEvent]
}

object AggregateClasses extends Enumeration {
  type AggregateClass = Value
  val USER, EMAIL = Value

  implicit val format: Format[AggregateClass] = enumFormat(AggregateClasses)
}

object Events extends Enumeration {
  type Event = Value
  val REGISTERED, DELETED, VERIFIED, UNVERIFIED, SENT = Value

  implicit val format: Format[Event] = enumFormat(Events)
}