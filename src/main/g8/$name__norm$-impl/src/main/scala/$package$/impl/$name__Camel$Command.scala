package $package$.impl

import java.util.UUID

import akka.Done
import $organization$.common.utils.JsonFormats.singletonFormat
import $organization$.common.utils.ErrorResponse
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.{Format, Json}

trait $name;format="Camel"$Command[R] extends ReplyType[R]

case class GrantAccessToken(password: String) extends $name;format="Camel"$Command[Either[ErrorResponse, $name;format="Camel"$Session]]
object GrantAccessToken {
  implicit val format: Format[GrantAccessToken] = Json.format[GrantAccessToken]
}
case object RevokeAccessToken extends $name;format="Camel"$Command[Done] {
  implicit val format: Format[RevokeAccessToken.type] = singletonFormat(RevokeAccessToken)
}
case class ExtendAccessToken(refresh_token: UUID) extends $name;format="Camel"$Command[Either[ErrorResponse, $name;format="Camel"$Session]]
object ExtendAccessToken {
  implicit val format: Format[ExtendAccessToken] = Json.format[ExtendAccessToken]
}
case class Create$name;format="Camel"$(id: UUID, $name;format="camel"$name: String, password: String, email: String) extends $name;format="Camel"$Command[Either[ErrorResponse, String]]
object Create$name;format="Camel"$ {
  implicit val format: Format[Create$name;format="Camel"$] = Json.format[Create$name;format="Camel"$]
}
case object Verify$name;format="Camel"$ extends $name;format="Camel"$Command[Done] {
  implicit val format: Format[Verify$name;format="Camel"$.type] = singletonFormat(Verify$name;format="Camel"$)
}
case object UnVerify$name;format="Camel"$ extends $name;format="Camel"$Command[Done] {
  implicit val format: Format[UnVerify$name;format="Camel"$.type] = singletonFormat(UnVerify$name;format="Camel"$)
}
case object IsSessionExpired extends $name;format="Camel"$Command[Boolean] {
  implicit val format: Format[IsSessionExpired.type] = singletonFormat(IsSessionExpired)
}
case object Delete$name;format="Camel"$ extends $name;format="Camel"$Command[Done] {
  implicit val format: Format[Delete$name;format="Camel"$.type] = singletonFormat(Delete$name;format="Camel"$)
}

/*
import $package$.api._
import $organization$.common.utils.JsonFormats._

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import java.time.LocalDateTime
import java.util.UUID
import play.api.libs.json.{Format, Json}
import scala.collection.immutable.Seq

/**
  * This is an event sourced entity. It has a state, [[$name;format="Camel"$State]], which
  * stores what the greeting should be (eg, "Hello").
  *
  * Event sourced entities are interacted with by sending them commands. This
  * entity supports two commands, a [[UseGreetingMessage]] command, which is
  * used to change the greeting, and a [[Hello]] command, which is a read
  * only command which returns a greeting to the name specified by the command.
  *
  * Commands get translated to events, and it's the events that get persisted by
  * the entity. Each event will have an event handler registered for it, and an
  * event handler simply applies an event to the current state. This will be done
  * when the event is first created, and it will also be done when the entity is
  * loaded from the database - each event will be replayed to recreate the state
  * of the entity.
  *
  * This entity defines one event, the [[GreetingMessageChanged]] event,
  * which is emitted when a [[UseGreetingMessage]] command is received.
  */
//You can interact with PersistentEntity by sending command messages to it. COMMANDS are instructions to do something.

/**
  * This interface defines all the commands that the HelloWorld entity supports.
  */
/*
sealed trait $name;format="Camel"$Command

case object Get$name;format="Camel"$ extends $name;format="Camel"$Command with ReplyType[Option[$name;format="Camel"$]] {
  implicit val format: Format[Get$name;format="Camel"$.type] = singletonFormat(Get$name;format="Camel"$)
}

case class Create$name;format="Camel"$($name;format="camel"$: $name;format="Camel"$) extends $name;format="Camel"$Command with ReplyType[Done]

object Create$name;format="Camel"$ {
  implicit val format: Format[Create$name;format="Camel"$] = Json.format
}

case class StartAuction($name;format="camel"$Id: UUID) extends $name;format="Camel"$Command with ReplyType[Done]

object StartAuction {
  implicit val format: Format[StartAuction] = Json.format
}

case class UpdatePrice(price: Int) extends $name;format="Camel"$Command with ReplyType[Done]

object UpdatePrice {
  implicit val format: Format[UpdatePrice] = Json.format
}

case class FinishAuction(winner: Option[UUID], price: Option[Int]) extends $name;format="Camel"$Command with ReplyType[Done]

object FinishAuction {
  implicit val format: Format[FinishAuction] = Json.format
}

//sealed trait $name;format="Camel"$Command[R] extends ReplyType[R]
*/