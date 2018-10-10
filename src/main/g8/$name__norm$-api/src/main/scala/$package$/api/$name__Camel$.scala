package $package$.api

import $organization$.common.utils.JsonFormats._

import com.lightbend.lagom.scaladsl.api.deser.PathParamSerializer
import java.time.{Duration, Instant}
import java.util.UUID
import play.api.libs.json.{Format, Json}

case class $name;format="Camel"$(
  id: Option[UUID],
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
  def safeId = id.getOrElse(UUID.randomUUID())
}

object $name;format="Camel"$ {
  implicit val format: Format[$name;format="Camel"$] = Json.format

  def create(
    creator: UUID,
    title: String,
    description: String,
    currencyId: String,
    increment: Int,
    reservePrice: Int,
    auctionDuration: Duration
  ) = $name;format="Camel"$(None, creator, title, description, currencyId, increment, reservePrice, None, $name;format="Camel"$Status.Created, auctionDuration,
    None, None, None)
}

object $name;format="Camel"$Status extends Enumeration {
  val Created, Auction, Completed, Cancelled = Value
  type Status = Value

  implicit val format: Format[Value] = enumFormat(this)
  implicit val pathParamSerializer: PathParamSerializer[Status] =
    PathParamSerializer.required("$name;format="camel"$Status")(withName)(_.toString)
}

case class $name;format="Camel"$Summary(
  id: UUID,
  title: String,
  currencyId: String,
  reservePrice: Int,
  status: $name;format="Camel"$Status.Status
)

object $name;format="Camel"$Summary {
  implicit val format: Format[$name;format="Camel"$Summary] = Json.format
}