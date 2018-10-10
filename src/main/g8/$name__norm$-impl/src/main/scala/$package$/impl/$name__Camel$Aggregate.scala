package $package$.impl

import java.util.UUID

import $organization$.common.utils.JsonFormats._
import $package$.impl.$name;format="Camel"$Status.$name;format="Camel"$Status
import play.api.libs.json.{Format, Json}

case class $name;format="Camel"$Aggregate(status: $name;format="Camel"$Status = $name;format="Camel"$Status.UNVERIFIED,
                         id: UUID,
                         $name;format="camel"$name: String,
                         email: String,
                         hashed_salted_pwd: String,
                         currentSession: Option[$name;format="Camel"$Session] = None)
object $name;format="Camel"$Aggregate {
  implicit val format: Format[$name;format="Camel"$Aggregate] = Json.format[$name;format="Camel"$Aggregate]
}


object $name;format="Camel"$Status extends Enumeration {
  type $name;format="Camel"$Status = Value
  val VERIFIED,
  UNVERIFIED = Value

  implicit val format: Format[$name;format="Camel"$Status] = enumFormat($name;format="Camel"$Status)
}

case class $name;format="Camel"$Session(access_token: UUID,
                       createdOn: Long,
                       expiry: Long,
                       refresh_token: UUID)
object $name;format="Camel"$Session {
  final val EXPIRY: Long = 3600000
  def apply(): $name;format="Camel"$Session = $name;format="Camel"$Session(UUID.randomUUID(), System.currentTimeMillis(), EXPIRY, UUID.randomUUID())
  implicit val format: Format[$name;format="Camel"$Session] = Json.format[$name;format="Camel"$Session]
}

/*
import $package$.api._
import $organization$.common.utils.JsonFormats._

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import java.time.{Duration, Instant, LocalDateTime}
import java.util.UUID
import play.api.libs.json.{Format, Json}
import scala.collection.immutable.Seq

object $name;format="Camel"$Status extends Enumeration {
  val Created, Auction, Completed, Cancelled = Value
  type Status = Value

  implicit val format: Format[Status] = enumFormat($name;format="Camel"$Status)
}

case class $name;format="Camel"$Aggregate(
  id: UUID,
  creator: UUID,
  title: String,
  description: String,
  currencyId: String,
  increment: Int,
  reservePrice: Int,
  price: Option[Int],
  status: $name;format="Camel"$Status.Status,
  auctionStart: Option[Instant],
  auctionEnd: Option[Instant],
  auctionWinner: Option[UUID]
) {

  def start(startTime: Instant) = {
    assert(status == $name;format="Camel"$Status.Created)
    copy(
      status = $name;format="Camel"$Status.Auction,
      auctionStart = Some(startTime),
      auctionEnd = Some(startTime)
    )
  }

  def end(winner: Option[UUID], price: Option[Int]) = {
    assert(status == $name;format="Camel"$Status.Auction)
    copy(
      status = $name;format="Camel"$Status.Completed,
      price = price,
      auctionWinner = winner
    )
  }

  def updatePrice(price: Int) = {
    assert(status == $name;format="Camel"$Status.Auction)
    copy(
      price = Some(price)
    )
  }

  def cancel = {
    assert(status == $name;format="Camel"$Status.Auction || status == $name;format="Camel"$Status.Completed)
    copy(
      status = $name;format="Camel"$Status.Cancelled
    )
  }
}

object $name;format="Camel"$Aggregate {
  implicit val format: Format[$name;format="Camel"$Aggregate] = Json.format[$name;format="Camel"$Aggregate]
}
*/