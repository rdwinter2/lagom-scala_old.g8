package $package$.impl

import java.util.UUID

import $package$.impl.$name;format="Camel"$Status.$name;format="Camel"$Status
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventShards, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

trait $name;format="Camel"$Event extends AggregateEvent[$name;format="Camel"$Event] {
  override def aggregateTag: AggregateEventTagger[$name;format="Camel"$Event] = $name;format="Camel"$Event.Tag
}

object $name;format="Camel"$Event {
  val NumShards = 10
  val Tag: AggregateEventShards[$name;format="Camel"$Event] = AggregateEventTag.sharded[$name;format="Camel"$Event](NumShards)
}