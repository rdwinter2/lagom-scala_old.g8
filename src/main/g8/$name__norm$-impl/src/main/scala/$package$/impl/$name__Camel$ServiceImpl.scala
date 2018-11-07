package $package$.impl

import $organization$.common.authentication.AuthenticationServiceComposition._
import $organization$.common.authentication.TokenContent
import $organization$.common.utils.JsonFormats._
import $organization$.common.utils.{ErrorResponse, Marshaller, ErrorResponses => ER}
//import $organization$.common.validation.ValidationUtil._
import $package$.api._

import akka.{Done, NotUsed}
import akka.persistence.query.Offset
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import com.datastax.driver.core._
import com.datastax.driver.core.utils.UUIDs
import com.lightbend.lagom.internal.client.CircuitBreakerMetricsProviderImpl
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.transport.{TransportErrorCode, TransportException, NotFound}
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.{CassandraReadSide, CassandraSession, CassandraPersistenceComponents}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventShards, AggregateEventTag, EventStreamElement, PersistentEntity, PersistentEntityRegistry, ReadSideProcessor}
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import com.lightbend.lagom.scaladsl.pubsub.{PubSubComponents, PubSubRegistry, TopicId}
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.rp.servicediscovery.lagom.scaladsl.LagomServiceLocatorComponents
import com.softwaremill.macwire._
import com.wix.accord._
import com.wix.accord.dsl._
import com.wix.accord.Descriptions._
import cool.graph.cuid._
import scala.util.Try
import java.util.UUID
import org.slf4j.LoggerFactory
import play.api.{Environment, LoggerConfigurator}
import play.api.libs.json.{Format, Json}
import play.api.libs.ws.ahc.AhcWSComponents
import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}

// $name$ Service Implementation

class $name;format="Camel"$ServiceImpl(
  registry: PersistentEntityRegistry,
  $name;format="camel"$Repository: $name;format="Camel"$Repository,
  pubSubRegistry: PubSubRegistry
)(implicit ec: ExecutionContext) extends $name;format="Camel"$Service {
  private val logger = LoggerFactory.getLogger(classOf[$name;format="Camel"$ServiceImpl])

  override def create$name;format="Camel"$WithSystemGeneratedId: ServiceCall[Create$name;format="Camel"$Request, Create$name;format="Camel"$Response] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { create$name;format="Camel"$Request =>
      logger.info(s"User $tokenContent.username is ... ")
      logger.info(s"Creating '$name$' with a system generated identifier and input \$create$name;format="Camel"$Request...")
      val validationResult = validate(create$name;format="Camel"$Request)
      validationResult match {
        case failure: Failure =>  throw new TransportException(TransportErrorCode.BadRequest, "request validation failure")
        case _ =>
      }
      val id = Cuid.createCuid()
      val $name;format="camel"$Aggregate = $name;format="Camel"$Aggregate(id, create$name;format="Camel"$Request.$name;format="camel"$)
      val $name;format="camel"$Resource = $name;format="Camel"$Resource(id, $name;format="Camel"$(create$name;format="Camel"$Request.$name;format="camel"$.name, create$name;format="Camel"$Request.$name;format="camel"$.description))
      val $name;format="camel"$EntityRef = registry.refFor[$name;format="Camel"$Entity](id)
      logger.info(s"Publishing event \$$name;format="camel"$Aggregate")
      val topic = pubSubRegistry.refFor(TopicId[$name;format="Camel"$Resource])
      topic.publish($name;format="camel"$Resource)
      $name;format="camel"$EntityRef.ask(Create$name;format="Camel"$Command($name;format="camel"$Aggregate)).map { _ =>
        mapToCreate$name;format="Camel"$Response($name;format="camel"$Resource)
      }
    }
  }

  override def create$name;format="Camel"$($name;format="camel"$Id: String): ServiceCall[Create$name;format="Camel"$Request, Create$name;format="Camel"$Response] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { create$name;format="Camel"$Request =>
      logger.info(s"Creating '$name$' with input \$create$name;format="Camel"$Request...")
      val validationResult = validate(create$name;format="Camel"$Request)
      validationResult match {
        case failure: Failure =>  throw new TransportException(TransportErrorCode.BadRequest, "request validation failure")
        case _ =>
      }
      val $name;format="camel"$Aggregate = $name;format="Camel"$Aggregate($name;format="camel"$Id, create$name;format="Camel"$Request.$name;format="camel"$)
      val $name;format="camel"$Resource = $name;format="Camel"$Resource($name;format="camel"$Id, $name;format="Camel"$(create$name;format="Camel"$Request.$name;format="camel"$.name, create$name;format="Camel"$Request.$name;format="camel"$.description))
      val $name;format="camel"$EntityRef = registry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id.toString)
      logger.info(s"Publishing event \$$name;format="camel"$Aggregate")
      val topic = pubSubRegistry.refFor(TopicId[$name;format="Camel"$Resource])
      topic.publish($name;format="camel"$Resource)
      $name;format="camel"$EntityRef.ask(Create$name;format="Camel"$Command($name;format="camel"$Aggregate)).map { _ =>
        mapToCreate$name;format="Camel"$Response($name;format="camel"$Resource)
      }
    }
  }

  override def get$name;format="Camel"$($name;format="camel"$Id: String): ServiceCall[NotUsed, Get$name;format="Camel"$Response] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { _ =>
      logger.info(s"Looking up '$name$' with ID \$$name;format="camel"$Id...")
      val $name;format="camel"$EntityRef = registry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id.toString)
      $name;format="camel"$EntityRef.ask(Get$name;format="Camel"$Query).map {
        case Some($name;format="camel"$Aggregate) => mapToGet$name;format="Camel"$Response($name;format="camel"$Aggregate)
        case None => throw NotFound(s"$name$ \$$name;format="camel"$Id not found")
      }
    }
  }

  override def getAll$plural_name;format="Camel"$: ServiceCall[NotUsed, GetAll$plural_name;format="Camel"$Response] = ServiceCall { _ =>
    logger.info("Looking up all '$plural_name$'...")
    $name;format="camel"$Repository.selectAll$plural_name;format="Camel"$.map($name;format="camel"$s => GetAll$plural_name;format="Camel"$Response($name;format="camel"$s.map(mapTo$name;format="Camel"$Resource)))
  }

  private def mapTo$name;format="Camel"$Resource($name;format="camel"$Aggregate: $name;format="Camel"$Aggregate): $name;format="Camel"$Resource = {
    $name;format="Camel"$Resource($name;format="camel"$Aggregate.id, $name;format="camel"$Aggregate.$name;format="camel"$)
  }

  private def mapToGet$name;format="Camel"$Response($name;format="camel"$Aggregate: $name;format="Camel"$Aggregate): Get$name;format="Camel"$Response = {
    Get$name;format="Camel"$Response($name;format="camel"$Aggregate.id, $name;format="camel"$Aggregate.$name;format="camel"$)
  }

  private def mapToCreate$name;format="Camel"$Response($name;format="camel"$Resource: $name;format="Camel"$Resource): Create$name;format="Camel"$Response = {
    Create$name;format="Camel"$Response($name;format="camel"$Resource.id, $name;format="camel"$Resource.$name;format="camel"$)
  }

  override def $name;format="camel"$MessageBrokerEvents: Topic[$name;format="Camel"$MessageBrokerEvent] =
    TopicProducer.taggedStreamWithOffset($name;format="Camel"$Event.Tag.allTags.toList) { (tag, offset) =>
      logger.info("Creating $name;format="Camel"$Event Topic...")
      registry.eventStream(tag, offset)
        .filter {
          _.event match {
            case x@(_: $name;format="Camel"$CreatedEvent) => true
            case _ => false
          }
        }.mapAsync(1)(convertEvent)
    }

  private def convertEvent(eventStreamElement: EventStreamElement[$name;format="Camel"$Event]): Future[($name;format="Camel"$MessageBrokerEvent, Offset)] = {
    eventStreamElement match {
      case EventStreamElement(id, $name;format="Camel"$CreatedEvent($name;format="camel"$), offset) =>
        Future.successful {
          ($name;format="Camel"$Created(
            id = $name;format="camel"$.id,
            $name;format="camel"$ = $name;format="camel"$.$name;format="camel"$
          ), offset)
        }
    }
  }

  override def subscribe$name;format="Camel"$Stream: ServiceCall[NotUsed, Source[$name;format="Camel"$Resource, NotUsed]] = ServiceCall { _ =>
    val topic = pubSubRegistry.refFor(TopicId[$name;format="Camel"$Resource])
    Future.successful(topic.subscriber)
  }
}

// $name$ Entity

class $name;format="Camel"$Entity extends PersistentEntity {
  override type Command = $name;format="Camel"$Command
  override type Event = $name;format="Camel"$Event
  override type State = Option[$name;format="Camel"$Aggregate]

  override def initialState: Option[$name;format="Camel"$Aggregate] = None

  override def behavior: Behavior = {
    case None => notCreated
    case Some($name;format="camel"$) => created($name;format="camel"$)
  }

  private val get$name;format="Camel"$Query = Actions().onReadOnlyCommand[Get$name;format="Camel"$Query.type, Option[$name;format="Camel"$Aggregate]] {
    case (Get$name;format="Camel"$Query, ctx, state) => ctx.reply(state)
  }

  private val notCreated = {
    Actions().onCommand[Create$name;format="Camel"$Command, Done] {
      case (Create$name;format="Camel"$Command($name;format="camel"$), ctx, state) =>
        ctx.thenPersist($name;format="Camel"$CreatedEvent($name;format="camel"$))(_ => ctx.reply(Done))
    }.onEvent {
      case ($name;format="Camel"$CreatedEvent($name;format="camel"$), state) => Some($name;format="camel"$)
    }.orElse(get$name;format="Camel"$Query)
  }

  private def created($name;format="camel"$: $name;format="Camel"$Aggregate) = {
    Actions().orElse(get$name;format="Camel"$Query)
  }
}

case class $name;format="Camel"$Aggregate(
  id: String,
  $name;format="camel"$: $name;format="Camel"$
)

object $name;format="Camel"$Aggregate {
  implicit val format: Format[$name;format="Camel"$Aggregate] = Json.format
}

sealed trait $name;format="Camel"$Command

case object Get$name;format="Camel"$Query extends $name;format="Camel"$Command with ReplyType[Option[$name;format="Camel"$Aggregate]] {
  implicit val format: Format[Get$name;format="Camel"$Query.type] = singletonFormat(Get$name;format="Camel"$Query)
}

case class Create$name;format="Camel"$Command($name;format="camel"$Aggregate: $name;format="Camel"$Aggregate) extends $name;format="Camel"$Command with ReplyType[Done]

object Create$name;format="Camel"$Command {
  implicit val format: Format[Create$name;format="Camel"$Command] = Json.format
}

sealed trait $name;format="Camel"$Event extends AggregateEvent[$name;format="Camel"$Event] {
  override def aggregateTag = $name;format="Camel"$Event.Tag
}

object $name;format="Camel"$Event {
  val NumShards = 4
  val Tag: AggregateEventShards[$name;format="Camel"$Event] = AggregateEventTag.sharded[$name;format="Camel"$Event](NumShards)
}

case class $name;format="Camel"$CreatedEvent($name;format="camel"$: $name;format="Camel"$Aggregate) extends $name;format="Camel"$Event

object $name;format="Camel"$CreatedEvent {
  implicit val format: Format[$name;format="Camel"$CreatedEvent] = Json.format
}

// $name$ Application Loader

trait $name;format="Camel"$Components extends LagomServerComponents with CassandraPersistenceComponents with PubSubComponents {
  implicit def executionContext: ExecutionContext

  def environment: Environment

  override lazy val lagomServer: LagomServer = serverFor[$name;format="Camel"$Service](wire[$name;format="Camel"$ServiceImpl])
  lazy val $name;format="camel"$Repository: $name;format="Camel"$Repository = wire[$name;format="Camel"$Repository]
  override lazy val jsonSerializerRegistry: $name;format="Camel"$SerializerRegistry.type = $name;format="Camel"$SerializerRegistry

  persistentEntityRegistry.register(wire[$name;format="Camel"$Entity])
  readSide.register(wire[$name;format="Camel"$EventProcessor])
}

abstract class $name;format="Camel"$Application(context: LagomApplicationContext) extends LagomApplication(context)
  with $name;format="Camel"$Components
  with AhcWSComponents
  with LagomKafkaComponents {}

class $name;format="Camel"$ApplicationLoader extends LagomApplicationLoader {
  override def loadDevMode(context: LagomApplicationContext): LagomApplication = {
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
      override lazy val circuitBreakerMetricsProvider = new CircuitBreakerMetricsProviderImpl(actorSystem)
    }

  override def describeService = Some(readDescriptor[$name;format="Camel"$Service])
}

// $name$ Repository

private[impl] class $name;format="Camel"$Repository(session: CassandraSession)(implicit ec: ExecutionContext) {
  private val logger = LoggerFactory.getLogger(classOf[$name;format="Camel"$Repository])

  def selectAll$plural_name;format="Camel"$: Future[Seq[$name;format="Camel"$Aggregate]] = {
    logger.info("Querying all '$plural_name$'...")
    session.selectAll(
      """
      SELECT id, $name;format="lower,snake,word"$ FROM $name;format="lower,snake,word"$
    """).map(rows => rows.map(row => convertTo$name;format="Camel"$Aggregate(row)))
  }

  def select$name;format="Camel"$(id: String) = {
    logger.info(s"Querying '$name$' with ID \$id...")
    session.selectOne("SELECT id, $name;format="lower,snake,word"$ FROM $name;format="lower,snake,word"$ WHERE id = ?", id)
  }

  private def convertTo$name;format="Camel"$Aggregate($name;format="camel"$Row: Row): $name;format="Camel"$Aggregate = {
    $name;format="Camel"$Aggregate(
      $name;format="camel"$Row.getString("id"),
      Json.fromJson[$name;format="Camel"$](Json.parse($name;format="camel"$Row.getString("$name;format="lower,snake,word"$"))).get
//      implicitly[Format[$name;format="Camel"$]].reads(Json.parse($name;format="camel"$Row.getString("$name;format="norm"$")))
//          .toOption
//          .flatten
//          .getOrElse(Set.empty[$name;format="Camel"$])
    )
  }
}

private[impl] class $name;format="Camel"$EventProcessor(session: CassandraSession, readSide: CassandraReadSide)(implicit ec: ExecutionContext)
  extends ReadSideProcessor[$name;format="Camel"$Event] {
  private val logger = LoggerFactory.getLogger(classOf[$name;format="Camel"$EventProcessor])

  private var insert$name;format="Camel"$Statement: PreparedStatement = _
  private var insert$name;format="Camel"$ByNameStatement: PreparedStatement = _
  private var insert$name;format="Camel"$SummaryStatement: PreparedStatement = _

  override def buildHandler: ReadSideProcessor.ReadSideHandler[$name;format="Camel"$Event] = {
    readSide.builder[$name;format="Camel"$Event]("$name;format="camel"$EventOffset")
      .setGlobalPrepare(createTables)
      .setPrepare(_ => prepareStatements())
      .setEventHandler[$name;format="Camel"$CreatedEvent](e => {
        insert$name;format="Camel"$(e.event.$name;format="camel"$)
      })
      .build
  }

  override def aggregateTags: Set[AggregateEventTag[$name;format="Camel"$Event]] = $name;format="Camel"$Event.Tag.allTags

  private def createTables() = {
    logger.info("Creating tables...")
    for {
      _ <- session.executeCreateTable(
        """
          |CREATE TABLE IF NOT EXISTS $name;format="lower,snake,word"$ (
          | id text PRIMARY KEY,
          | $name;format="lower,snake,word"$ text
          |);
        """.stripMargin)
      _ <- session.executeCreateTable(
        """
          |CREATE TABLE IF NOT EXISTS $name;format="lower,snake,word"$_summary (
          | id text PRIMARY KEY,
          | name text
          |);
        """.stripMargin)
      _ <- session.executeCreateTable(
        """
          |CREATE TABLE IF NOT EXISTS $name;format="lower,snake,word"$_by_name (
          | id text,
          | name text PRIMARY KEY
          |);
        """.stripMargin)
    } yield Done
  }

  private def prepareStatements() = {
    logger.info("Preparing statements...")
    for {
      insert$name;format="Camel"$ <- session.prepare(
        """
          |INSERT INTO $name;format="lower,snake,word"$(
          | id,
          | $name;format="lower,snake,word"$
          | ) VALUES (
          | ?, ?);
        """.stripMargin)
      insert$name;format="Camel"$Summary <- session.prepare(
        """
          |INSERT INTO $name;format="lower,snake,word"$_summary(
          | id,
          | name
          | ) VALUES (
          | ?, ?);
        """.stripMargin)
      insert$name;format="Camel"$ByName <- session.prepare(
        """
          |INSERT INTO $name;format="lower,snake,word"$_by_name(
          | id,
          | name
          | ) VALUES (
          | ?, ?);
        """.stripMargin)
    } yield {
      insert$name;format="Camel"$Statement = insert$name;format="Camel"$
      insert$name;format="Camel"$SummaryStatement = insert$name;format="Camel"$Summary
      insert$name;format="Camel"$ByNameStatement = insert$name;format="Camel"$ByName
      Done
    }
  }

  private def insert$name;format="Camel"$($name;format="camel"$Aggregate: $name;format="Camel"$Aggregate) = {
    logger.info(s"Inserting \$$name;format="camel"$Aggregate...")
    Future.successful(List(
      insert$name;format="Camel"$Statement.bind(
        $name;format="camel"$Aggregate.id,
        implicitly[Format[$name;format="Camel"$]].writes($name;format="camel"$Aggregate.$name;format="camel"$).toString),
      insert$name;format="Camel"$SummaryStatement.bind(
        $name;format="camel"$Aggregate.id,
        $name;format="camel"$Aggregate.$name;format="camel"$.name),
      insert$name;format="Camel"$ByNameStatement.bind(
        $name;format="camel"$Aggregate.id,
        $name;format="camel"$Aggregate.$name;format="camel"$.name)
    ))
  }
}

// $name$ Serializer Registry

object $name;format="Camel"$SerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    JsonSerializer[$name;format="Camel"$],
    JsonSerializer[$name;format="Camel"$Resource],
    JsonSerializer[Create$name;format="Camel"$Request],
    JsonSerializer[Create$name;format="Camel"$Response],
    JsonSerializer[Get$name;format="Camel"$Response],
    JsonSerializer[GetAll$plural_name;format="Camel"$Response],
    JsonSerializer[$name;format="Camel"$Aggregate],
    JsonSerializer[$name;format="Camel"$CreatedEvent],

    JsonSerializer[Create$name;format="Camel"$Command],
    JsonSerializer[Get$name;format="Camel"$Query.type],

    JsonSerializer[$name;format="Camel"$Created]
  )
}