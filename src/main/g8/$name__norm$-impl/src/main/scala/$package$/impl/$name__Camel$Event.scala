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