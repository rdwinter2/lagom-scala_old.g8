package $package$.impl

import $organization$.common.authentication.AuthenticationServiceComposition._
import $organization$.common.authentication.TokenContent
import $organization$.common.utils.JsonFormats._
import $organization$.common.response.{
  ErrorResponse,
  ErrorResponses => ER
}
import $organization$.common.utils.Marshaller
//import $organization$.common.validation.ValidationUtil._
import $package$.api._
import $package$.api.$name;format="Camel"$Service
import $package$.impl.ServiceErrors._
import $package$.impl.ServiceErrors.ServiceError

import akka.{Done, NotUsed}
import akka.persistence.query.Offset
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import com.datastax.driver.core._
import com.datastax.driver.core.utils.UUIDs
import com.lightbend.lagom.internal.client.CircuitBreakerMetricsProviderImpl
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.transport.{
  TransportErrorCode,
  TransportException,
  NotFound,
  RequestHeader,
  ResponseHeader
}
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.{
  CassandraReadSide,
  CassandraSession,
  CassandraPersistenceComponents
}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.{
  AggregateEvent,
  AggregateEventShards,
  AggregateEventTag,
  EventStreamElement,
  PersistentEntity,
  PersistentEntityRegistry,
  ReadSideProcessor
}
import com.lightbend.lagom.scaladsl.playjson.{
  JsonSerializer,
  JsonSerializerRegistry
}
import com.lightbend.lagom.scaladsl.pubsub.{
  PubSubComponents,
  PubSubRegistry,
  TopicId
}
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.rp.servicediscovery.lagom.scaladsl.LagomServiceLocatorComponents
import com.softwaremill.macwire._
import com.wix.accord._
import com.wix.accord.dsl._
import com.wix.accord.Descriptions._
import cool.graph.cuid._
import scala.util.Try
import java.util.UUID
import julienrf.json.derived
import org.slf4j.LoggerFactory
import play.api.{Environment, LoggerConfigurator}
import play.api.libs.json._
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.http.HeaderNames
import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}

// $name$ Service Implementation

class $name;format="Camel"$ServiceImpl(
    registry: PersistentEntityRegistry,
    $name;format="camel"$Repository: $name;format="Camel"$Repository,
    pubSubRegistry: PubSubRegistry //,
//    $name;format="camel"$Service: $name;format="Camel"$Service
)(implicit ec: ExecutionContext)
    extends $name;format="Camel"$Service
    with Marshaller {
  private val logger = LoggerFactory.getLogger(classOf[$name;format="Camel"$ServiceImpl])

// $name$ Creation Calls {
  override def post$name;format="Camel"$
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        val $name;format="camel"$Id = Cuid.createCuid()
        val creationId = Cuid.createCuid()
        logger.info(
          s"Posting '$name$' with identifier \$$name;format="camel"$Id...")
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }

  override def create$name;format="Camel"$1
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        val $name;format="camel"$Id = Cuid.createCuid()
        val creationId = Cuid.createCuid()
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }
  override def create$name;format="Camel"$2($name;format="camel"$Id: String)
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        val creationId = Cuid.createCuid()
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }

  override def create$name;format="Camel"$3(creationId: String)
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        val $name;format="camel"$Id = Cuid.createCuid()
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }

  override def create$name;format="Camel"$4($name;format="camel"$Id: String, creationId: String)
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }

  def create$name;format="Camel"$Internal($name;format="camel"$Id: String, creationId: String)
    : ServerServiceCall[Create$name;format="Camel"$Request, Create$name;format="Camel"$Response] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { create$name;format="Camel"$Request =>
        val username = tokenContent.username
        logger.info(s"User \$username is creating a $name$ ")
        logger.info(
          s"Creating '$name$' with input \$create$name;format="Camel"$Request...")
        val validationResult = validate(create$name;format="Camel"$Request)
        validationResult match {
          case failure: Failure =>
            throw new TransportException(TransportErrorCode.BadRequest,
                                         "request validation failure")
          case _ =>
        }
        val $name;format="camel"$Aggregate =
          $name;format="Camel"$Aggregate($name;format="camel"$Id, $name;format="Camel"$Resource(create$name;format="Camel"$Request.$name;format="camel"$))
        val $name;format="camel"$Resource =
          $name;format="Camel"$Resource(create$name;format="Camel"$Request.$name;format="camel"$)
        val $name;format="camel"$EntityRef =
          registry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id.toString)
        logger.info(s"Publishing event \$$name;format="camel"$Aggregate")
        val topic = pubSubRegistry.refFor(TopicId[$name;format="Camel"$Resource])
        topic.publish($name;format="camel"$Resource)
        $name;format="camel"$EntityRef
          .ask(Create$name;format="Camel"$Command($name;format="camel"$Aggregate))
          .map { _ =>
            mapToCreate$name;format="Camel"$Response($name;format="camel"$Id, $name;format="camel"$Resource)
          }
      }
    }

  private def mapToCreate$name;format="Camel"$Response(
      $name;format="camel"$Id: String,
      $name;format="camel"$Resource: $name;format="Camel"$Resource): Create$name;format="Camel"$Response = {
    Create$name;format="Camel"$Response($name;format="camel"$Id,
                             $name;format="camel"$Resource.$name;format="camel"$,
                             None)
  }

  private def mapToCreate$name;format="Camel"$Response(
      $name;format="camel"$State: $name;format="Camel"$State): Create$name;format="Camel"$Response = {
    Create$name;format="Camel"$Response($name;format="camel"$State.$name;format="camel"$Aggregate map { _.$name;format="camel"$Id } getOrElse "No identifier",
                             $name;format="camel"$State.$name;format="camel"$Aggregate map { _.$name;format="camel"$Resource.$name;format="camel"$} getOrElse $name;format="Camel"$("No name", Some("No description")),
                             None)
  }
// }

  val Ok: ResponseHeader =  ResponseHeader.Ok
        .withHeader("Server", "$name$ service")

  override def put$name;format="Camel"$($name;format="camel"$Id: String): ServiceCall[Replace$name;format="Camel"$Request, Either[ErrorResponse, Replace$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, replace$name;format="Camel"$Request) =>
        val replacementId = Cuid.createCuid()
        logger.info(
          s"Putting '$name$' with identifier \$$name;format="camel"$Id...")
        this
          .replace$name;format="Camel"$Internal($name;format="camel"$Id, replacementId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, replace$name;format="Camel"$Request).map {
            case (responseHeader, Right(response)) => (Ok, Right(response))
          }
      }
    }

  def replace$name;format="Camel"$Internal($name;format="camel"$Id: String, replacementId: String)
    : ServerServiceCall[Replace$name;format="Camel"$Request, Either[ErrorResponse, Replace$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { replace$name;format="Camel"$Request =>
        val username = tokenContent.username
        logger.info(s"User \$username is replacing $name$ \$$name;format="camel"$Id with a successor resource ...")
        logger.info(
          s"Replacing '$name$' with input \$replace$name;format="Camel"$Request...")
        val validationResult = validate(replace$name;format="Camel"$Request)
        validationResult match {
          case failure: Failure =>
            throw new TransportException(TransportErrorCode.BadRequest,
                                        "request validation failure")
          case _ =>
        }
//        val $name;format="camel"$Aggregate =
//          $name;format="Camel"$Aggregate($name;format="camel"$Id, replace$name;format="Camel"$Request.$name;format="camel"$)
//        val $name;format="camel"$Resource =
//          $name;format="Camel"$Resource(replace$name;format="Camel"$Request.$name;format="camel"$)
      val $name;format="camel"$EntityRef =
          registry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id.toString)
//        logger.info(s"Publishing event \$$name;format="camel"$Aggregate")
        $name;format="camel"$EntityRef
          .ask(Replace$name;format="Camel"$Command($name;format="camel"$Id, replace$name;format="Camel"$Request))
          .map {
            case Right(replace$name;format="Camel"$Request) =>
              mapToReplace$name;format="Camel"$Response(replace$name;format="Camel"$Request)
//            case Left(errorResponse) => throw CommandFailed(???)
          }
//        val topic = pubSubRegistry.refFor(TopicId[$name;format="Camel"$Resource])
//        topic.publish($name;format="camel"$Resource)
      }
    }
//  override def improve$name;format="Camel"$Description($name;format="camel"$Id: String)
//    : ServiceCall[Improve$name;format="Camel"$DescriptionRequest, Improve$name;format="Camel"$DescriptionResponse]
//    authenticated { (tokenContent, _) =>
//      ServerServiceCall { ($name;format="camel"$Id, improve$name;format="Camel"$DescriptionRequest: Improve$name;format="Camel"$DescriptionRequest) =>
//      logger.info(
//        s"Improving the description of '$name$' with id \$$name;format="camel"$Id by setting it to \$improve$name;format="Camel"$DescriptionRequest.description...")
//      val validationResult = validate(improve$name;format="Camel"$DescriptionRequest)
//      validationResult match {
//        case failure: Failure =>
//          throw new TransportException(TransportErrorCode.BadRequest,
//                                       "request validation failure")
//        case _ =>
//      }
//      val $name;format="camel"$EntityRef =
//        registry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id.toString)
//      $name;format="camel"$EntityRef.ask(Improve$name;format="Camel"$DescriptionCommand(Improve$name;format="Camel"$DescriptionRequest))
//          .map { _ =>
//            mapToImprove$name;format="Camel"$DescriptionResponse($name;format="camel"$Resource)
//          }
//      }
//    }

  override def get$name;format="Camel"$(
      $name;format="camel"$Id: String): ServiceCall[NotUsed, Either[ErrorResponse, Get$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { _ =>
        logger.info(s"Looking up '$name$' with ID \$$name;format="camel"$Id...")
        val $name;format="camel"$EntityRef =
          registry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id.toString)
        $name;format="camel"$EntityRef.ask(Get$name;format="Camel"$Query).map {
          case $name;format="Camel"$State(_, $name;format="Camel"$Status.NONEXISTENT, _) =>
            throw NotFound(s"$name$ \$$name;format="camel"$Id not found")
          case $name;format="Camel"$State(Some($name;format="camel"$Aggregate), $name;format="Camel"$Status.ACTIVE, _) =>
            Right(mapToGet$name;format="Camel"$Response($name;format="camel"$Aggregate))
          case $name;format="Camel"$State(_, $name;format="Camel"$Status.ARCHIVED, _) =>
            throw NotFound(s"$name$ \$$name;format="camel"$Id archived")
          case $name;format="Camel"$State(_, _, _) =>
            throw NotFound(s"$name$ \$$name;format="camel"$Id in unknown state")
        }
      }
    }

  private def mapToGet$name;format="Camel"$Response(
      $name;format="camel"$Aggregate: $name;format="Camel"$Aggregate): Get$name;format="Camel"$Response = {
    Get$name;format="Camel"$Response($name;format="camel"$Aggregate.$name;format="camel"$Id,
                          $name;format="camel"$Aggregate.$name;format="camel"$Resource.$name;format="camel"$)
  }

  //override def getAll$plural_name;format="Camel"$
  //  : ServiceCall[NotUsed, GetAll$plural_name;format="Camel"$Response] = ServiceCall { _ =>
  //  logger.info("Looking up all '$plural_name$'...")
  //  $name;format="camel"$Repository.selectAll$plural_name;format="Camel"$.map($name;format="camel"$s =>
  //    GetAll$plural_name;format="Camel"$Response($name;format="camel"$s.map(mapTo$name;format="Camel"$Resource)))
  //}

  private def mapTo$name;format="Camel"$Resource(
      $name;format="camel"$Aggregate: $name;format="Camel"$Aggregate): $name;format="Camel"$Resource = {
    $name;format="Camel"$Resource($name;format="camel"$Aggregate.$name;format="camel"$Resource.$name;format="camel"$)
  }

  private def mapToReplace$name;format="Camel"$Response(replace$name;format="Camel"$Request: Replace$name;format="Camel"$Request): Replace$name;format="Camel"$Response = {
    Replace$name;format="Camel"$Response($name;format="camel"$Aggregate.$name;format="camel"$Id,
                              $name;format="camel"$Aggregate.$name;format="camel"$Resource.$name;format="camel"$)
  }

  override def $name;format="camel"$MessageBrokerEvents
    : Topic[$name;format="Camel"$MessageBrokerEvent] =
    TopicProducer.taggedStreamWithOffset($name;format="Camel"$Event.Tag.allTags.toList) {
      (tag, offset) =>
        logger.info("Creating $name;format="Camel"$Event Topic...")
        registry
          .eventStream(tag, offset)
          .filter {
            _.event match {
              case x @ (_: $name;format="Camel"$CreatedEvent) => true
              case _                               => false
            }
          }
          .mapAsync(1)(convertEvent)
    }

  private def convertEvent(
      eventStreamElement: EventStreamElement[$name;format="Camel"$Event])
    : Future[($name;format="Camel"$MessageBrokerEvent, Offset)] = {
    eventStreamElement match {
      case EventStreamElement(id, $name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate), offset) =>
        Future.successful {
          ($name;format="Camel"$Created(
             $name;format="camel"$Aggregate.$name;format="camel"$Id,
             $name;format="camel"$Aggregate.$name;format="camel"$Resource.$name;format="camel"$
           ),
           offset)
        }
    }
  }

//  override def stream$plural_name;format="Camel"$
//    : ServiceCall[NotUsed, Source[$name;format="Camel"$Resource, NotUsed]] = ServiceCall {
//    _ =>
      //val topic = pubSubRegistry.refFor(TopicId[$name;format="Camel"$Resource])
//      Future.successful(topic$name;format="Camel"$CreatedEvent.subscriber)
//  }
}

// $name$ Entity

final class $name;format="Camel"$Entity extends PersistentEntity {

  //private val published$name;format="Camel"$CreatedEvent = pubSubRegistry.refFor(TopicId[$name;format="Camel"$CreatedEvent])

  override type Command = $name;format="Camel"$Command[_]
  override type Event = $name;format="Camel"$Event
  override type State = Option[$name;format="Camel"$State]

  type OnCommandHandler[M] = PartialFunction[(Command, CommandContext[M], State), Persist]
  type ReadOnlyHandler[M] = PartialFunction[(Command, ReadOnlyCommandContext[M], State), Unit]

  override def initialState: Option[$name;format="Camel"$State] = None

  // Finite State Machine (FSM)
  override def behavior: Behavior = {
    case None => nonexistent$name;format="Camel"$
    case Some(state) if state.status == $name;format="Camel"$Status.ACTIVE => active$name;format="Camel"$
    case Some(state) if state.status == $name;format="Camel"$Status.ARCHIVED => archived$name;format="Camel"$
    case Some(state) => unknown$name;format="Camel"$
  }

  private val nonexistent$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { create$name;format="Camel"$Command }
        .onCommand[Replace$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { reply$name;format="Camel"$DoesNotExist }
        .onEvent {
          case ($name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate), state) => $name;format="Camel"$State(Some($name;format="camel"$Aggregate), $name;format="Camel"$Status.ACTIVE, 1)
          case (_, state) => state
        }
    }
  }

  private val active$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
        .onCommand[Replace$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replace$name;format="Camel"$Command }
        .onEvent {
          case ($name;format="Camel"$ReplacedEvent($name;format="camel"$Id, replacement$name;format="Camel"$Resource, motivation), state) =>
            $name;format="Camel"$State(Some($name;format="Camel"$Aggregate($name;format="camel"$Id, replacement$name;format="Camel"$Resource)), $name;format="Camel"$Status.ACTIVE, 1)
          case (_, state) => state
        }
    }
  }

  private val archived$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
        .onEvent {
          case (_, state) => state
        }
    }
  }

  private val unknown$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
        .onCommand[Replace$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
    }
  }

  private def get$name;format="Camel"$Action = Actions()
    .onReadOnlyCommand[Get$name;format="Camel"$Query.type, $name;format="Camel"$State] {
      case (Get$name;format="Camel"$Query, ctx, state) => ctx.reply(state)
    }

  private def create$name;format="Camel"$Command: OnCommandHandler[Either[ServiceError, $name;format="Camel"$Aggregate]] = {
    case (Create$name;format="Camel"$Command($name;format="camel"$Aggregate), ctx, state) =>
      ctx.thenPersist($name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate)) { evt =>
        ctx.reply(Right($name;format="camel"$Aggregate))
      }
  }

  private def replace$name;format="Camel"$Command: OnCommandHandler[Either[ServiceError, $name;format="Camel"$Aggregate]] = {
    case (Replace$name;format="Camel"$Command(replace$name;format="Camel"$Request), ctx, state) =>
      ctx.thenPersist($name;format="Camel"$ReplacedEvent(replace$name;format="Camel"$Request)) { evt =>
        ctx.reply(Right($name;format="Camel"$Aggregate("3",replace$name;format="Camel"$Request.replacement$name;format="Camel"$Resource)))
      }
  }

  private val notCreated = {
    get$name;format="Camel"$Action orElse {
    Actions()
      .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] {
        case (Create$name;format="Camel"$Command($name;format="camel"$Aggregate), ctx, state) =>
          ctx.thenPersist($name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate)) { evt =>
            ctx.reply(Right($name;format="camel"$Aggregate))
          }
      }
    }
  }

  private def replyConflict[R]: OnCommandHandler[Either[ServiceError, R]] = {
    case (_, ctx, _) =>
      ctx.reply(Left($name;format="Camel"$Conflict))
      ctx.done
  }

  private def created($name;format="camel"$Aggregate: $name;format="Camel"$Aggregate) = {
    get$name;format="Camel"$Action orElse {
    Actions()
//      .onCommand[Destroy$name;format="Camel"$Command.type, Done] {
//        case (Destroy$name;format="Camel"$Command, ctx, Some(u)) =>
//          ctx.thenPersist($name;format="Camel"$DestroyedEvent(u.id))(_ => ctx.reply(Done))
//      }
//      .onCommand[Improve$name;format="Camel"$DescripionCommand.type, Done] {
//        case (Improve$name;format="Camel"$DescripionCommand, ctx, Some(u)) =>
//          ctx.thenPersist($name;format="Camel"$DescripionImprovedEvent(improve$name;format="Camel"$DescripionRequest))(_ => ctx.reply(Done))
//      }
//      .onEvent {
//        case ($name;format="Camel"$DestroyedEvent(_), Some(u)) =>
//          None
//      }
//      .onEvent {
//        case ($name;format="Camel"$DescripionImprovedEvent(_), Some(u)) =>
//          None
//      }
    }
  }

  private def reply$name;format="Camel"$DoesNotExist[R]: OnCommandHandler[Either[ServiceError, R]] = {
    case (_, ctx, _) =>
      ctx.reply(Left($name;format="Camel"$DoesNotExist))
      ctx.done
  }

}

// $name$ State
final case class $name;format="Camel"$State(
  $name;format="camel"$Aggregate: $name;format="Camel"$Aggregate,
  status: $name;format="Camel"$Status.Status = $name;format="Camel"$Status.NONEXISTENT
) {
  def withStatus (status: $name;format="Camel"$Status.Status) = copy(status = status)
}

object $name;format="Camel"$State {
  implicit val format: Format[$name;format="Camel"$State] = Json.format
  val nonexistent = $name;format="Camel"$State(None, $name;format="Camel"$Status.NONEXISTENT, 0)
}

// $name$ Status
object $name;format="Camel"$Status extends Enumeration {
  val NONEXISTENT, ACTIVE, ARCHIVED, UNKNOWN = Value
  type Status = Value

  implicit val format: Format[Value] = enumFormat(this)
//  implicit val pathParamSerializer: PathParamSerializer[Status] =
//    PathParamSerializer.required("$name;format="camel"$Status")(withName)(_.toString)
}

// $name$ Aggregate
final case class $name;format="Camel"$Aggregate(
  $name;format="camel"$Identity: Identity,
  $name;format="camel"$Resource: $name;format="Camel"$Resource
)

object $name;format="Camel"$Aggregate {
  implicit val format: Format[$name;format="Camel"$Aggregate] = Json.format
}

sealed trait $name;format="Camel"$Command[R] extends ReplyType[R]

case object Get$name;format="Camel"$Query
    extends $name;format="Camel"$Command[$name;format="Camel"$State] {
  implicit val format: Format[Get$name;format="Camel"$Query.type] = singletonFormat(
    Get$name;format="Camel"$Query)
}

// The create $name$ reply {
final case class Create$name;format="Camel"$Reply(
  $name;format="camel"$Aggregate: $name;format="Camel"$Aggregate)

object Create$name;format="Camel"$Reply {
  implicit val format: Format[Create$name;format="Camel"$Reply] = Json.format
}
// }

// The create $name$ command {
final case class Create$name;format="Camel"$Command(
  $name;format="camel"$Aggregate: $name;format="Camel"$Aggregate)
    extends $name;format="Camel"$Command[Either[ServiceError, Create$name;format="Camel"$Reply]]

object Create$name;format="Camel"$Command {
  implicit val format: Format[Create$name;format="Camel"$Command] = Json.format
}
// }

//case object Destroy$name;format="Camel"$Command
//    extends $name;format="Camel"$Command
//    with ReplyType[Done] {
//  implicit val format: Format[Destroy$name;format="Camel"$Command.type] = singletonFormat(Destroy$name;format="Camel"$Command)
//}

final case class Replace$name;format="Camel"$Command(Replace$name;format="Camel"$Command: String, replace$name;format="Camel"$Request: Replace$name;format="Camel"$Request)
    extends $name;format="Camel"$Command[Either[ServiceError, $name;format="Camel"$Aggregate]]

object Replace$name;format="Camel"$Command {
  implicit val format: Format[Replace$name;format="Camel"$Command] = Json.format
}

sealed trait $name;format="Camel"$Event extends AggregateEvent[$name;format="Camel"$Event] {
  override def aggregateTag = $name;format="Camel"$Event.Tag
}

object $name;format="Camel"$Event {
  val NumShards = 4
  val Tag: AggregateEventShards[$name;format="Camel"$Event] =
    AggregateEventTag.sharded[$name;format="Camel"$Event](NumShards)

  implicit val format: Format[$name;format="Camel"$Event] =
    derived.flat.oformat((__ \ "type").format[String])
}

final case class $name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate: $name;format="Camel"$Aggregate)
    extends $name;format="Camel"$Event

object $name;format="Camel"$CreatedEvent {
  implicit val format: Format[$name;format="Camel"$CreatedEvent] = Json.format
}

//final case class $name;format="Camel"$DestroyedEvent($name;format="camel"$Id: String)
//    extends $name;format="Camel"$Event
//
//object $name;format="Camel"$DestroyedEvent {
//  implicit val format: Format[$name;format="Camel"$DestroyedEvent] = Json.format
//}

final case class $name;format="Camel"$ReplacedEvent(
  $name;format="camel"$Id: String,
  replacement$name;format="Camel"$Resource: $name;format="Camel"$Resource,
  motivation: Option[String])
    extends $name;format="Camel"$Event

object $name;format="Camel"$ReplacedEvent {
  implicit val format: Format[$name;format="Camel"$ReplacedEvent] = Json.format
}

// $name$ Application Loader

trait $name;format="Camel"$Components
    extends LagomServerComponents
    with CassandraPersistenceComponents
    with PubSubComponents {
  implicit def executionContext: ExecutionContext

  def environment: Environment

  override lazy val lagomServer: LagomServer =
    serverFor[$name;format="Camel"$Service](wire[$name;format="Camel"$ServiceImpl])
  lazy val $name;format="camel"$Repository: $name;format="Camel"$Repository =
    wire[$name;format="Camel"$Repository]
  override lazy val jsonSerializerRegistry: $name;format="Camel"$SerializerRegistry.type =
    $name;format="Camel"$SerializerRegistry

  persistentEntityRegistry.register(wire[$name;format="Camel"$Entity])
  readSide.register(wire[$name;format="Camel"$EventProcessor])
}

abstract class $name;format="Camel"$Application(context: LagomApplicationContext)
    extends LagomApplication(context)
    with $name;format="Camel"$Components
    with AhcWSComponents
    with LagomKafkaComponents {

  // To bind to another Lagom service
  // lazy val otherService = serviceClient.implement[OtherService]
  //lazy val $name;format="camel"$Service: $name;format="Camel"$Service = serviceClient.implement[$name;format="Camel"$Service]
}

class $name;format="Camel"$ApplicationLoader extends LagomApplicationLoader {
  override def loadDevMode(
      context: LagomApplicationContext): LagomApplication = {
    // Workaround for logback.xml not being detected, see https://github.com/lagom/lagom/issues/534
    val environment = context.playContext.environment
    LoggerConfigurator(environment.classLoader).foreach {
      _.configure(environment)
    }
    // end workaround
    new $name;format="Camel"$Application(context) with LagomDevModeComponents
  }

  override def load(context: LagomApplicationContext): LagomApplication =
    new $name;format="Camel"$Application(context) with LagomServiceLocatorComponents {
      override lazy val circuitBreakerMetricsProvider =
        new CircuitBreakerMetricsProviderImpl(actorSystem)
    }

  override def describeService = Some(readDescriptor[$name;format="Camel"$Service])
}

// $name$ Repository

private[impl] class $name;format="Camel"$Repository(session: CassandraSession)(
    implicit ec: ExecutionContext) {
  private val logger = LoggerFactory.getLogger(classOf[$name;format="Camel"$Repository])

  def selectAll$plural_name;format="Camel"$: Future[Seq[$name;format="Camel"$Aggregate]] = {
    logger.info("Querying all '$plural_name$'...")
    session.selectAll("""
      SELECT id, $name;format="lower,snake,word"$ FROM $name;format="lower,snake,word"$
    """).map(rows => rows.map(row => convertTo$name;format="Camel"$Aggregate(row)))
  }

  def select$name;format="Camel"$(id: String) = {
    logger.info(s"Querying '$name$' with ID \$id...")
    session.selectOne("SELECT id, $name;format="lower,snake,word"$ FROM $name;format="lower,snake,word"$ WHERE id = ?",
                      id)
  }

  private def convertTo$name;format="Camel"$Aggregate(
      $name;format="camel"$Row: Row): $name;format="Camel"$Aggregate = {
    $name;format="Camel"$Aggregate(
      $name;format="camel"$Row.getString("id"),
      $name;format="Camel"$Resource(
      Json
        .fromJson[$name;format="Camel"$](
          Json.parse($name;format="camel"$Row.getString("$name;format="lower,snake,word"$")))
        .get
      )
//      implicitly[Format[$name;format="Camel"$]].reads(Json.parse($name;format="camel"$Row.getString("$name;format="norm"$")))
//          .toOption
//          .flatten
//          .getOrElse(Set.empty[$name;format="Camel"$])
    )
  }
}

private[impl] class $name;format="Camel"$EventProcessor(
    session: CassandraSession,
    readSide: CassandraReadSide)(implicit ec: ExecutionContext)
    extends ReadSideProcessor[$name;format="Camel"$Event] {
  private val logger =
    LoggerFactory.getLogger(classOf[$name;format="Camel"$EventProcessor])

  private var insert$name;format="Camel"$Statement: PreparedStatement = _
  private var destroy$name;format="Camel"$Statement: PreparedStatement = _
  private var insert$name;format="Camel"$ByNameStatement: PreparedStatement = _
  private var insert$name;format="Camel"$SummaryStatement: PreparedStatement = _
  private var insert$name;format="Camel"$HistoryStatement: PreparedStatement = _

  override def buildHandler
    : ReadSideProcessor.ReadSideHandler[$name;format="Camel"$Event] = {
    readSide
      .builder[$name;format="Camel"$Event]("$name;format="camel"$EventOffset")
      .setGlobalPrepare(createTables)
      .setPrepare(_ => prepareStatements())
      .setEventHandler[$name;format="Camel"$CreatedEvent](e => {
        insert$name;format="Camel"$(e.event.$name;format="camel"$Aggregate)
      })
//      .setEventHandler[$name;format="Camel"$DestroyedEvent](e => {
//        destroy$name;format="Camel"$(e.event.$name;format="camel"$Id)
//      })
      .build
  }

  override def aggregateTags: Set[AggregateEventTag[$name;format="Camel"$Event]] =
    $name;format="Camel"$Event.Tag.allTags

  private def createTables() = {
    logger.info("Creating tables...")
    for {
      _ <- session.executeCreateTable("""
          |CREATE TABLE IF NOT EXISTS $name;format="lower,snake,word"$_current (
          | object_identifier text PRIMARY KEY,
          | $name;format="lower,snake,word"$ text
          |);
        """.stripMargin)
      _ <- session.executeCreateTable(
        """
          |CREATE TABLE IF NOT EXISTS $name;format="lower,snake,word"$_summary (
          | object_identifier text PRIMARY KEY,
          | name text
          |);
        """.stripMargin)
      _ <- session.executeCreateTable(
        """
          |CREATE TABLE IF NOT EXISTS $name;format="lower,snake,word"$_by_name (
          | object_identifier text,
          | name text PRIMARY KEY
          |);
        """.stripMargin)
        _ <- session.executeCreateTable(
          """
            |CREATE TABLE IF NOT EXISTS $name;format="lower,snake,word"$_history (
              | identity text PRIMARY KEY,
              | $name;format="lower,snake,word"$ text
            |);
          """.stripMargin)
    } yield Done
  }

  private def prepareStatements() = {
    logger.info("Preparing statements...")
    for {
      insert$name;format="Camel"$ <- session.prepare("""
          |INSERT INTO $name;format="lower,snake,word"$_current(
          | object_identifier,
          | $name;format="lower,snake,word"$
          | ) VALUES (
          | ?, ?);
        """.stripMargin)
      destroy$name;format="Camel"$ <- session.prepare("""
          |DELETE FROM $name;format="lower,snake,word"$_current
          |WHERE object_identifier = ?;
        """.stripMargin)
      insert$name;format="Camel"$Summary <- session.prepare(
        """
          |INSERT INTO $name;format="lower,snake,word"$_summary(
          | object_identifier,
          | name
          | ) VALUES (
          | ?, ?);
        """.stripMargin)
      insert$name;format="Camel"$ByName <- session.prepare(
        """
          |INSERT INTO $name;format="lower,snake,word"$_by_name(
          | object_identifier,
          | name
          | ) VALUES (
          | ?, ?);
        """.stripMargin)
        insert$name;format="Camel"$History <- session.prepare(
          """
            |INSERT INTO $name;format="lower,snake,word"$_history(
            | identity,
            | $name;format="lower,snake,word"$
            | ) VALUES (
            | ?, ?);
          """.stripMargin)
    } yield {
      insert$name;format="Camel"$Statement = insert$name;format="Camel"$
      destroy$name;format="Camel"$Statement = destroy$name;format="Camel"$
      insert$name;format="Camel"$SummaryStatement = insert$name;format="Camel"$Summary
      insert$name;format="Camel"$ByNameStatement = insert$name;format="Camel"$ByName
      insert$name;format="Camel"$HistoryStatement = insert$name;format="Camel"$History
      Done
    }
  }

  private def insert$name;format="Camel"$($name;format="camel"$Aggregate: $name;format="Camel"$Aggregate) = {
    logger.info(s"Inserting \$$name;format="camel"$Aggregate...")
    Future.successful(
      List(
        insert$name;format="Camel"$Statement.bind($name;format="camel"$Aggregate.$name;format="camel"$Identity.identifier,
                                       implicitly[Format[$name;format="Camel"$]]
                                         .writes($name;format="camel"$Aggregate.$name;format="camel"$Resource.$name;format="camel"$)
                                         .toString)
        //insert$name;format="Camel"$SummaryStatement
        //  .bind($name;format="camel"$Aggregate.id, $name;format="camel"$Aggregate.$name;format="camel"$.name),
        //insert$name;format="Camel"$ByNameStatement
        //  .bind($name;format="camel"$Aggregate.id, $name;format="camel"$Aggregate.$name;format="camel"$.name)
      ))
  }

  private def destroy$name;format="Camel"$($name;format="camel"$IdentityIdentifier: String) = {
    logger.info(s"Deleting \$$name;format="camel"$Id...")
    Future.successful(
      List(
        destroy$name;format="Camel"$Statement.bind($name;format="camel"$IdentityIdentifier)
        //insert$name;format="Camel"$SummaryStatement
        //  .bind($name;format="camel"$Aggregate.id, $name;format="camel"$Aggregate.$name;format="camel"$.name),
        //insert$name;format="Camel"$ByNameStatement
        //  .bind($name;format="camel"$Aggregate.id, $name;format="camel"$Aggregate.$name;format="camel"$.name)
      ))
  }

}

// $name$ Serializer Registry

object $name;format="Camel"$SerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    // Data structures
    JsonSerializer[$name;format="Camel"$],
    JsonSerializer[$name;format="Camel"$Resource],
    JsonSerializer[$name;format="Camel"$Aggregate],
    // Create
    //JsonSerializer[Create$name;format="Camel"$Request],
    JsonSerializer[Create$name;format="Camel"$Command],
    JsonSerializer[Create$name;format="Camel"$Reply],
    JsonSerializer[$name;format="Camel"$CreatedEvent],
    //JsonSerializer[Create$name;format="Camel"$Response],
    // Replace
    //JsonSerializer[Replace$name;format="Camel"$Request],
    //JsonSerializer[Replace$name;format="Camel"$Command],
    //JsonSerializer[Replace$name;format="Camel"$Reply],
    //JsonSerializer[$name;format="Camel"$ReplacedEvent],
    //JsonSerializer[Replace$name;format="Camel"$Response],
    // Mutate
    // Deactivate
    // Reactivate
    //

//    JsonSerializer[$name;format="Camel"$Status],
    // Get One
    //JsonSerializer[$name;format="Camel"$Created],
    JsonSerializer[Get$name;format="Camel"$Query.type],
    //JsonSerializer[Get$name;format="Camel"$Response],
    // Get All
    //JsonSerializer[GetAll$plural_name;format="Camel"$Response]
  )
}

/**
  * ServiceErrors object acts as a enumeration of pre-defined errors that can be used as response for the public REST api.
  *
  * Internally these errors can be created by a read action to the Read-side or a message sent to the persistent entities.
  * It defines all errors related to [[Cart]], [[Bundle]] and [[Item]].
  */
object ServiceErrors {
  type ServiceError = ErrorResponse

  final val CartNotFound: ServiceError = ErrorResponse(404, "Not found", "Cart not found.")
  final val $name;format="Camel"$Conflict: ServiceError = ErrorResponse(409, "Conflict", "$name$ already exists for this user.")
  final val $name;format="Camel"$DoesNotExist: ServiceError = ErrorResponse(404, "Not found", "$name$ does not exist.")
  final val CartCannotBeUpdated: ServiceError = ErrorResponse(400, "Bad request", "Cart cannot be updated.")

  final val BundleNotFound: ServiceError = ErrorResponse(404, "Not Found", "Bundle not found.")
  final val BundleConflict: ServiceError = ErrorResponse(409, "Conflict", "Bundle already exists with this name.")

  final val ItemsNotFoundInInventory: ServiceError = ErrorResponse(404, "Not Found", "One or more items were not found in the inventory.")
  final val ItemCannotBeRemoved: ServiceError = ErrorResponse(400, "Bad request", "Item is being used by a bundle, remove bundle first.")
  final val ItemNotFound: ServiceError = ErrorResponse(404, "Not Found", "Item not found.")
  final val ItemConflict: ServiceError = ErrorResponse(409, "Conflict", "Item already exists with this name.")
  final val ItemNegativeQuantity: ServiceError = ErrorResponse(400, "Bad request", "Item quantity cannot be negative.")
}