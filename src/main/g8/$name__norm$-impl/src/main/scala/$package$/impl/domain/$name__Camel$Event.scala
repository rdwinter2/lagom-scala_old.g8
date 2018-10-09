package $package$.impl.domain

import $package$.api.aggregate._
import $package$.api.request._
import $package$.api.response._
import $package$.api.service.$name;format="Camel"$Service
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
