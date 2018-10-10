package $package$.impl.domain

import $package$.api.response._
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

case class $name;format="Camel"$(
  id: UUID,
  creator: UUID,
  title: String,
  description: String,
  currencyId: String,
  increment: Int,
  reservePrice: Int,
  price: Option[Int],
  status: $name;format="Camel"$Status.Status,
  auctionDuration: Duration,
  auctionStart: Option[Instant],
  auctionEnd: Option[Instant],
  auctionWinner: Option[UUID]
) {

  def start(startTime: Instant) = {
    assert(status == $name;format="Camel"$Status.Created)
    copy(
      status = $name;format="Camel"$Status.Auction,
      auctionStart = Some(startTime),
      auctionEnd = Some(startTime.plus(auctionDuration))
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

object $name;format="Camel"$ {
  implicit val format: Format[$name;format="Camel"$] = Json.format
}