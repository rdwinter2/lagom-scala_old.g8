package $package$.impl.domain

import $package$.api.aggregate._
import $package$.api.response._

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import java.time.LocalDateTime
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
class $name;format="Camel"$Entity extends PersistentEntity {

  override type Command = $name;format="Camel"$Command[_]
  override type Event = $name;format="Camel"$Event
  override type State = Option[$name;format="Camel"$]

  /**
    * The initial state. This is used if there is no snapshotted state to be found.
    */
  override def initialState: Option[$name;format="Camel"$] = None

  /**
    * An entity can define different behaviours for different states, so the behaviour
    * is a function of the current state to a set of actions.
    */

  override def behavior: Behavior = {
    case None => notCreated
    case Some($name;format="camel"$) if $name;format="camel"$.status == $name;format="Camel"$Status.Created => created($name;format="camel"$)
    case Some($name;format="camel"$) if $name;format="camel"$.status == $name;format="Camel"$Status.Auction => auction($name;format="camel"$)
    case Some($name;format="camel"$) if $name;format="camel"$.status == $name;format="Camel"$Status.Completed => completed
    case Some($name;format="camel"$) if $name;format="camel"$.status == $name;format="Camel"$Status.Cancelled => cancelled
  }

  private val get$name;format="Camel"$Command = Actions().onReadOnlyCommand[Get$name;format="Camel"$.type, Option[$name;format="Camel"$]] {
    case (Get$name;format="Camel"$, ctx, state) => ctx.reply(state)
  }

  private val notCreated = {
    Actions().onCommand[Create$name;format="Camel"$, Done] {
      case (Create$name;format="Camel"$($name;format="camel"$), ctx, state) =>
        ctx.thenPersist($name;format="Camel"$Created($name;format="camel"$))(_ => ctx.reply(Done))
    }.onEvent {
      case ($name;format="Camel"$Created($name;format="camel"$), state) => Some($name;format="camel"$)
    }.orElse(get$name;format="Camel"$Command)
  }

  private def created($name;format="camel"$: $name;format="Camel"$) = {
    Actions().onCommand[StartAuction, Done] {
      case (StartAuction(userId), ctx, _) =>
        if ($name;format="camel"$.creator != userId) {
          ctx.invalidCommand("Only the creator of an auction can start it")
          ctx.done
        } else {
          ctx.thenPersist(AuctionStarted(Instant.now()))(_ => ctx.reply(Done))
        }
    }.onEvent {
      case (AuctionStarted(time), Some($name;format="camel"$)) => Some($name;format="camel"$.start(time))
    }.orElse(get$name;format="Camel"$Command)
  }

  private def auction($name;format="camel"$: $name;format="Camel"$) = {
    Actions().onCommand[UpdatePrice, Done] {
      case (UpdatePrice(price), ctx, _) =>
        ctx.thenPersist(PriceUpdated(price))(_ => ctx.reply(Done))
    }.onCommand[FinishAuction, Done] {
      case (FinishAuction(winner, price), ctx, _) =>
        ctx.thenPersist(AuctionFinished(winner, price))(_ => ctx.reply(Done))
    }.onEvent {
      case (PriceUpdated(price), _) => Some($name;format="camel"$.updatePrice(price))
      case (AuctionFinished(winner, price), _) => Some($name;format="camel"$.end(winner, price))
    }.onReadOnlyCommand[StartAuction, Done] {
      case (_, ctx, _) => ctx.reply(Done)
    }.orElse(get$name;format="Camel"$Command)
  }

  private val completed = {
    Actions().onReadOnlyCommand[UpdatePrice, Done] {
      case (_, ctx, _) => ctx.reply(Done)
    }.onReadOnlyCommand[FinishAuction, Done] {
      case (_, ctx, _) => ctx.reply(Done)
    }.onReadOnlyCommand[StartAuction, Done] {
      case (_, ctx, _) => ctx.reply(Done)
    }.orElse(get$name;format="Camel"$Command)
  }

  private val cancelled = completed

}

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

sealed trait $name;format="Camel"$Command

case object Get$name;format="Camel"$ extends $name;format="Camel"$Command with ReplyType[Option[$name;format="Camel"$]] {
  implicit val format: Format[Get$name;format="Camel"$.type] = singletonFormat(Get$name;format="Camel"$)
}

case class Create$name;format="Camel"$($name;format="camel"$: $name;format="Camel"$) extends $name;format="Camel"$Command with ReplyType[Done]

object Create$name;format="Camel"$ {
  implicit val format: Format[Create$name;format="Camel"$] = Json.format
}

case class StartAuction(userId: UUID) extends $name;format="Camel"$Command with ReplyType[Done]

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

  override def behavior: Behavior = {
    case $name;format="Camel"$State(message, _) => Actions().onCommand[UseGreetingMessage, Done] {

      // Command handler for the UseGreetingMessage command
      case (UseGreetingMessage(newMessage), ctx, state) =>
        // In response to this command, we want to first persist it as a
        // GreetingMessageChanged event
        ctx.thenPersist(
          GreetingMessageChanged(newMessage)
        ) { _ =>
          // Then once the event is successfully persisted, we respond with done.
          ctx.reply(Done)
        }

    }.onReadOnlyCommand[Hello, String] {

      // Command handler for the Hello command
      case (Hello(name), ctx, state) =>
        // Reply with a message built from the current message, and the name of
        // the person we're meant to say hello to.
        ctx.reply(s"\$message, \$name!")

    }.onEvent {

      // Event handler for the GreetingMessageChanged event
      case (GreetingMessageChanged(newMessage), state) =>
        // We simply update the current state to use the greeting message from
        // the event.
        $name;format="Camel"$State(newMessage, LocalDateTime.now().toString)

    }
  }
}

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

/**
  * This interface defines all the events that the $name;format="Camel"$Entity supports.
  */
sealed trait $name;format="Camel"$Event extends AggregateEvent[$name;format="Camel"$Event] {
  def aggregateTag = $name;format="Camel"$Event.Tag
}

object $name;format="Camel"$Event {
  val Tag = AggregateEventTag[$name;format="Camel"$Event]
}

/**
  * An event that represents a change in greeting message.
  */
case class GreetingMessageChanged(message: String) extends $name;format="Camel"$Event

object GreetingMessageChanged {

  /**
    * Format for the greeting message changed event.
    *
    * Events get stored and loaded from the database, hence a JSON format
    * needs to be declared so that they can be serialized and deserialized.
    */
  implicit val format: Format[GreetingMessageChanged] = Json.format
}

