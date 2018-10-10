package $package$.impl

import $package$.api._

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import java.time.LocalDateTime
import play.api.libs.json.{Format, Json}
import scala.collection.immutable.Seq
//STATE is the condition that entity is in at specific instance. Events are replayed to recreate the current state of an entity.

/**
  * The current state held by the persistent entity.
  */
case class $name;format="Camel"$State(message: String, timestamp: String)

object $name;format="Camel"$State {
  /**
    * Format for the hello state.
    *
    * Persisted entities get snapshotted every configured number of events. This
    * means the state gets stored to the database, so that when the entity gets
    * loaded, you don't need to replay all the events, just the ones since the
    * snapshot. Hence, a JSON format needs to be declared so that it can be
    * serialized and deserialized when storing to and from the database.
    */
  implicit val format: Format[$name;format="Camel"$State] = Json.format
}
