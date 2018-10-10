package $package$.impl

import akka.Done
import $organization$.common.utils.{ErrorResponse, ErrorResponses => ER}
import com.lightbend.lagom.scaladsl.persistence._
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.LoggerFactory

class $name;format="Camel"$Entity extends PersistentEntity {

  //Command
  override type Command = $name;format="Camel"$Command[_]

  //Event
  override type Event = $name;format="Camel"$Event

  //Aggregate
  override type State = Option[$name;format="Camel"$Aggregate]
  override def initialState: Option[$name;format="Camel"$Aggregate] = None

  // Check FSM
  override def behavior: Behavior = {
    case None => unRegistered
    case Some($name;format="Camel"$Aggregate($name;format="Camel"$Status.UNVERIFIED, _, _, _, _, _)) => unVerified
    case Some($name;format="Camel"$Aggregate($name;format="Camel"$Status.VERIFIED, _, _, _, _, _)) => verified
  }

  private val log = LoggerFactory.getLogger(classOf[$name;format="Camel"$Entity])

  private def unRegistered: Actions =
    Actions()
      .onCommand[Create$name;format="Camel"$, Either[ErrorResponse, String]] {
      // Validate command
      case (Create$name;format="Camel"$(id, $name;format="camel"$name, password, email), ctx, _) =>
        log.info(s"Entity \$$name;format="camel"$name: Create$name;format="Camel"$ Command.")
        if (password.length >= 8) {
          // Persist Event and Publish Internal Event
          ctx.thenPersist($name;format="Camel"$Created(
            id,
            $name;format="camel"$name,
            BCrypt.hashpw(password, BCrypt.gensalt),
            $name;format="Camel"$Status.UNVERIFIED,
            email))(_ => ctx.reply(Right("Created")))
        } else {
          ctx.reply(Left(ER.BadRequest("password too short")))
          ctx.done
        }
    }
      .onCommand[Delete$name;format="Camel"$.type, Done] { case (Delete$name;format="Camel"$, ctx, _) => ctx.reply(Done); ctx.done }
      .onCommand[Verify$name;format="Camel"$.type, Done] { case (Verify$name;format="Camel"$, ctx, _) => ctx.reply(Done); ctx.done }
      .onCommand[UnVerify$name;format="Camel"$.type, Done] { case (UnVerify$name;format="Camel"$, ctx, _) => ctx.reply(Done); ctx.done }
      .onCommand[GrantAccessToken, Either[ErrorResponse, $name;format="Camel"$Session]] {
      case (GrantAccessToken(_), ctx, _) =>
        log.info("Entity: GrantAccessToken Command.")
        ctx.reply(Left(ER.BadRequest("$name;format="Camel"$ not verified")))
        ctx.done
    }
      .onEvent {
        case ($name;format="Camel"$Created(id, $name;format="camel"$name, hash, status, email), _) =>
          log.info(s"Entity \$$name;format="camel"$name: $name;format="Camel"$Created Event.")
          Some($name;format="Camel"$Aggregate(
            status,
            id,
            $name;format="camel"$name,
            email,
            hash,
            None))
      }

  private def unVerified: Actions =
    Actions()
      .onCommand[Verify$name;format="Camel"$.type, Done] {
      case (Verify$name;format="Camel"$, ctx, Some(u)) =>
        log.info(s"Entity \${u.$name;format="camel"$name}: Verify$name;format="Camel"$ Command.")
        ctx.thenPersist($name;format="Camel"$Verified(u.id))(_ => ctx.reply(Done))
    }.onCommand[GrantAccessToken, Either[ErrorResponse, $name;format="Camel"$Session]] {
      case (GrantAccessToken(_), ctx, _) =>
        ctx.reply(Left(ER.BadRequest("$name;format="Camel"$ not verified")))
        ctx.done
    }.onCommand[ExtendAccessToken, Either[ErrorResponse, $name;format="Camel"$Session]] {
      case (ExtendAccessToken(_), ctx, _) =>
        ctx.reply(Left(ER.BadRequest("$name;format="Camel"$ not verified")))
        ctx.done
    }
      .onCommand[Delete$name;format="Camel"$.type , Done] {
      case (Delete$name;format="Camel"$, ctx, Some(u)) =>
        log.info(s"Entity \${u.username}: Delete$name;format="Camel"$ Command.")
        u match {
          case $name;format="Camel"$Aggregate(_, id, _, _, _, None) =>
            ctx.thenPersist($name;format="Camel"$Deleted(id))(_ => ctx.reply(Done))
          case $name;format="Camel"$Aggregate(_, id, _, _, _, Some(session)) =>
            ctx.thenPersistAll(
              AccessTokenRevoked(session.access_token),
              $name;format="Camel"$Deleted(id)
            ){() => ctx.reply(Done) }
        }
    }
      .onEvent {
        case ($name;format="Camel"$Verified(_), state @ Some(u)) =>
          log.info(s"Entity \${u.$name;format="camel"$name}: $name;format="Camel"$Verified Event.")
          state.map($name;format="camel"$ => $name;format="camel"$.copy(status = $name;format="Camel"$Status.VERIFIED))
        case (AccessTokenRevoked(_), state @ Some(u)) =>
          log.info(s"Entity \${u.$name;format="camel"$name}: AccessTokenRevoked Event.")
          state.map($name;format="camel"$ => $name;format="camel"$.copy(currentSession = None))
        case ($name;format="Camel"$Deleted(_), Some(u)) =>
          log.info(s"Entity \${u.$name;format="camel"$name}: $name;format="Camel"$Deleted Event.")
          None
      }

  private def verified: Actions =
    Actions()
      .onCommand[Verify$name;format="Camel"$.type, Done] {
      case (Verify$name;format="Camel"$, ctx, Some(u)) =>
        log.info(s"Entity \${u.$name;format="camel"$name}: Verify$name;format="Camel"$ Command.")
        ctx.reply(Done)
        ctx.done
    }
      .onCommand[GrantAccessToken, Either[ErrorResponse, $name;format="Camel"$Session]] {
      case (GrantAccessToken(password), ctx, state @ Some(u)) =>
        log.info(s"Entity \${u.$name;format="camel"$name}: GrantAccessToken Command.")
        state match {
          case Some($name;format="Camel"$Aggregate(_, $name;format="camel"$Id, _, _, hash, Some(session))) if BCrypt.checkpw(password, hash) =>
            if (session.createdOn + $name;format="Camel"$Session.EXPIRY < System.currentTimeMillis()) {
              val newSession = $name;format="Camel"$Session()
              ctx.thenPersistAll(
                AccessTokenRevoked(session.access_token),
                AccessTokenGranted($name;format="camel"$Id, newSession)){() => ctx.reply(Right(newSession)) }
              } else {
              ctx.reply(Right(session.copy(expiry = session.createdOn + session.expiry - System.currentTimeMillis())))
              ctx.done
            }

          case Some($name;format="Camel"$Aggregate(_, $name;format="camel"$Id, _, _, hash, None)) if BCrypt.checkpw(password, hash) =>
            val newSession = $name;format="Camel"$Session()
            ctx.thenPersist(AccessTokenGranted($name;format="camel"$Id, newSession))(_ => ctx.reply(Right(newSession)))

          case _ =>
            ctx.invalidCommand("Authentication failed")
            ctx.done
      }
    }.onCommand[ExtendAccessToken, Either[ErrorResponse, $name;format="Camel"$Session]] {
      case (ExtendAccessToken(refresh_token), ctx, state @ Some(u)) =>
        log.info(s"Entity \${u.$name;format="camel"$name}: ExtendAccessToken Command.")
        state match {
          case Some($name;format="Camel"$Aggregate(_, $name;format="camel"$Id, _, _, _, Some(session))) if session.refresh_token == refresh_token =>
            val newSession = $name;format="Camel"$Session()
            ctx.thenPersistAll(
              AccessTokenRevoked(session.access_token),
              AccessTokenGranted($name;format="camel"$Id, newSession)){() => ctx.reply(Right(newSession)) }

          case _ =>
            ctx.reply(Left(ER.BadRequest("Cannot refresh session")))
            ctx.done
        }
    }.onCommand[RevokeAccessToken.type , Done] {
      case (RevokeAccessToken, ctx, state @ Some(u)) =>
        log.info(s"Entity \${u.$name;format="camel"$name}: RevokeAccessToken Command.")
        state match {
          case Some($name;format="Camel"$Aggregate(_, _, _, _, _, None)) =>
            ctx.reply(Done)
            ctx.done

          case Some($name;format="Camel"$Aggregate(_, _, _, _, _, Some(session))) =>
            ctx.thenPersist(AccessTokenRevoked(session.access_token))(_ => ctx.reply(Done))

          case _ =>
            ctx.reply(Done)
            ctx.done
        }
    }.onCommand[IsSessionExpired.type , Boolean] {
      case (IsSessionExpired, ctx, state) =>
        state match {
          case Some($name;format="Camel"$Aggregate(_, _, _, _, _, None)) =>
            ctx.reply(true)
            ctx.done
          case Some($name;format="Camel"$Aggregate(_, _, _, _, _, Some(session))) if session.createdOn + session.expiry >= System.currentTimeMillis() =>
            ctx.reply(false)
            ctx.done
          case Some($name;format="Camel"$Aggregate(_, _, _, _, _, Some(session))) if session.createdOn + session.expiry < System.currentTimeMillis() =>
            ctx.thenPersist(AccessTokenRevoked(session.access_token))(_ => ctx.reply(true))
        }
    }.onCommand[Delete$name;format="Camel"$.type , Done] {
      case (Delete$name;format="Camel"$, ctx, state @ Some(u)) =>
        log.info(s"Entity \${u.$name;format="camel"$name}: Delete$name;format="Camel"$ Command.")
        state match {
          case Some($name;format="Camel"$Aggregate(_, id, _, _, _, None)) =>
            ctx.thenPersist($name;format="Camel"$Deleted(id))(_ => ctx.reply(Done))
          case Some($name;format="Camel"$Aggregate(_, id, _, _, _, Some(session))) =>
            ctx.thenPersistAll(
              AccessTokenRevoked(session.access_token),
              $name;format="Camel"$Deleted(id)
            ){() => ctx.reply(Done) }
        }
    }.onCommand[UnVerify$name;format="Camel"$.type , Done] {
      case (UnVerify$name;format="Camel"$, ctx, state @ Some(u)) =>
        log.info(s"Entity \${u.$name;format="camel"$name}: UnVerify$name;format="Camel"$ Command.")
        state match {
          case Some($name;format="Camel"$Aggregate(_, id, _, _, _, _)) =>
            ctx.thenPersist($name;format="Camel"$UnVerified(id))(_ => ctx.reply(Done))
          case _ =>
            ctx.reply(Done)
            ctx.done
        }
    }
      .onEvent {
        case (AccessTokenGranted(_, session), state @ Some(u)) =>
          log.info(s"Entity \${u.$name;format="camel"$name}: AccessTokenGranted Event.")
          state.map($name;format="camel"$ => $name;format="camel"$.copy(currentSession = Some(session)))
        case (AccessTokenRevoked(_), state @ Some(u)) =>
          log.info(s"Entity \${u.$name;format="camel"$name}: AccessTokenRevoked Event.")
          state.map($name;format="camel"$ => $name;format="camel"$.copy(currentSession = None))
        case ($name;format="Camel"$Deleted(_), state @ Some(u)) =>
          log.info(s"Entity \${u.$name;format="camel"$name}: $name;format="Camel"$Deleted Event.")
          None
        case ($name;format="Camel"$UnVerified(_), state @ Some(u)) =>
          log.info(s"Entity \${u.$name;format="camel"$name}: $name;format="Camel"$UnVerified Event.")
          state.map($name;format="camel"$ => $name;format="camel"$.copy(status = $name;format="Camel"$Status.UNVERIFIED))
      }
}

/*
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
*/
  /**
    * The initial state. This is used if there is no snapshotted state to be found.
    */
/*
  override def initialState: Option[$name;format="Camel"$] = None
*/
  /**
    * An entity can define different behaviours for different states, so the behaviour
    * is a function of the current state to a set of actions.
    */
/*
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
      case (StartAuction($name;format="camel"$Id), ctx, _) =>
        if ($name;format="camel"$.creator != $name;format="camel"$Id) {
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
*/