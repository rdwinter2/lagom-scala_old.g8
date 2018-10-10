package $package$.impl

import $package$.api._

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import java.time.{Duration, Instant, LocalDateTime}
import play.api.libs.json.{Format, Json}
import scala.collection.immutable.Seq

class $name;format="Camel"$Entity extends PersistentEntity {

  override type Command = $name;format="Camel"$Command
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