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

case class AccessTokenGranted($name;format="camel"$Id: UUID, session: $name;format="Camel"$Session) extends $name;format="Camel"$Event
object AccessTokenGranted {
  implicit val format: Format[AccessTokenGranted] = Json.format[AccessTokenGranted]
}
case class AccessTokenRevoked(access_token: UUID) extends $name;format="Camel"$Event
object AccessTokenRevoked {
  implicit val format: Format[AccessTokenRevoked] = Json.format[AccessTokenRevoked]
}

// change name to fit business model
case class $name;format="Camel"$Created($name;format="camel"$Id: UUID, $name;format="camel"$name: String, hash: String, status: $name;format="Camel"$Status, email: String) extends $name;format="Camel"$Event
object $name;format="Camel"$Created {
  implicit val format: Format[$name;format="Camel"$Created] = Json.format[$name;format="Camel"$Created]
}

case class $name;format="Camel"$Verified($name;format="camel"$Id: UUID) extends $name;format="Camel"$Event
object $name;format="Camel"$Verified {
  implicit val format: Format[$name;format="Camel"$Verified] = Json.format
}

case class $name;format="Camel"$UnVerified($name;format="camel"$Id: UUID) extends $name;format="Camel"$Event
object $name;format="Camel"$UnVerified {
  implicit val format: Format[$name;format="Camel"$UnVerified] = Json.format
}

// change name to fit business model
case class $name;format="Camel"$Deleted($name;format="camel"$Id: UUID) extends $name;format="Camel"$Event
object $name;format="Camel"$Deleted {
  implicit val format: Format[$name;format="Camel"$Deleted] = Json.format
}

/*
import $package$.api._
import $organization$.common.utils.JsonFormats

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import java.time.{Duration, Instant, LocalDateTime}
import java.util.UUID
import play.api.libs.json.{Format, Json}
import scala.collection.immutable.Seq
//A command may cause changes to the entity state, and those changes are stored as events. EVENTS are the immutable facts of things that have happened.

sealed trait $name;format="Camel"$Event extends AggregateEvent[$name;format="Camel"$Event] {
  override def aggregateTag = $name;format="Camel"$Event.Tag
}

object $name;format="Camel"$Event {
  val NumShards = 4
  val Tag = AggregateEventTag.sharded[$name;format="Camel"$Event](NumShards)
}

case class $name;format="Camel"$Created($name;format="camel"$: $name;format="Camel"$) extends $name;format="Camel"$Event

object $name;format="Camel"$Created {
  implicit val format: Format[$name;format="Camel"$Created] = Json.format
}

case class AuctionStarted(startTime: Instant) extends $name;format="Camel"$Event

object AuctionStarted {
  implicit val format: Format[AuctionStarted] = Json.format
}

case class PriceUpdated(price: Int) extends $name;format="Camel"$Event

object PriceUpdated {
  implicit val format: Format[PriceUpdated] = Json.format
}

case class AuctionFinished(winner: Option[UUID], price: Option[Int]) extends $name;format="Camel"$Event

object AuctionFinished {
  implicit val format: Format[AuctionFinished] = Json.format
}
*/