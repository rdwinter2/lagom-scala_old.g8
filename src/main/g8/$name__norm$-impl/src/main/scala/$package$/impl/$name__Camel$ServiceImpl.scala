package $package$.impl

import $organization$.common.authentication.AuthenticationServiceComposition._
import $organization$.common.authentication.TokenContent
import $organization$.common.utils.JsonFormats._
import $organization$.common.utils.{ErrorResponse, Marshaller, ErrorResponses => ER}
import $organization$.common.validation.ValidationUtil._
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
import com.lightbend.lagom.scaladsl.api.transport.NotFound
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

  override def create$name;format="Camel"$: ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] = ServerServiceCall { request =>
    logger.info(s"Creating '$name$' with input \$request...")
    validate(request)
    val id = UUIDs.timeBased()
    val $name;format="camel"$ = $name;format="Camel"$Aggregate(id, request.name, request.description)
    val $name;format="camel"$EntityRef = registry.refFor[$name;format="Camel"$Entity](id.toString)
    logger.info(s"Publishing event \$$name;format="camel"$")
    val topic = pubSubRegistry.refFor(TopicId[$name;format="Camel"$Resource])
    topic.publish(request)
    $name;format="camel"$EntityRef.ask(Create$name;format="Camel"$Command($name;format="camel"$)).map { _ =>
      map$name;format="Camel"$($name;format="camel"$)
    }
  }

  override def get$name;format="Camel"$(id: UUID): ServiceCall[NotUsed, Either[ErrorResponse, Get$name;format="Camel"$Response]] = ServerServiceCall { _ =>
    logger.info(s"Looking up '$name$' with ID \$id...")
    val $name;format="camel"$EntityRef = registry.refFor[$name;format="Camel"$Entity](id.toString)
    $name;format="camel"$EntityRef.ask(Get$name;format="Camel"$).map {
      case Some($name;format="camel"$) => map$name;format="Camel"$($name;format="camel"$)
      case None => throw NotFound(s"$name$ \$id not found")
    }
  }

  override def getAll$plural_name;format="Camel"$: ServiceCall[NotUsed, GetAll$plural_name;format="Camel"$Response] = ServiceCall { _ =>
    logger.info("Looking up all '$plural_name$'...")
    $name;format="camel"$Repository.selectAll$name;format="Camel"$s.map($name;format="camel"$s => GetAll$plural_name;format="Camel"$Response($name;format="camel"$.map(map$name;format="Camel"$)))
  }

  private def map$name;format="Camel"$($name;format="camel"$: $name;format="Camel"$): $name;format="Camel"$Resource = {
    $name;format="Camel"$Resource(Some($name;format="camel"$.id), $name;format="camel"$.name, $name;format="camel"$.description)
  }

  override def $name;format="camel"$MessageBrokerEvents: Topic[$name;format="Camel"$MessageBrokerEvent] =
    TopicProducer.taggedStreamWithOffset($name;format="Camel"$Event.Tag.allTags.toList) { (tag, offset) =>
      logger.info("Creating $name;format="Camel"$Event Topic...")
      registry.eventStream(tag, offset)
        .filter {
          _.event match {
            case x@(_: $name;format="Camel"$Created) => true
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
            name = $name;format="camel"$.name,
            description = $name;format="camel"$.description
          ), offset)
        }
    }
  }

  override def get$name;format="Camel"$Stream: ServiceCall[NotUsed, Source[$name;format="Camel"$Resource, NotUsed]] = ServiceCall { _ =>
    val topic = pubSubRegistry.refFor(TopicId[$name;format="Camel"$Resource])
    Future.successful(topic.subscriber)
  }
}

// $name$ Entity

class $name;format="Camel"$Entity extends PersistentEntity {
  override type Command = $name;format="Camel"$Command
  override type Event = $name;format="Camel"$Event
  override type State = Option[$name;format="Camel"$]

  override def initialState: Option[$name;format="Camel"$] = None

  override def behavior: Behavior = {
    case None => notCreated
    case Some($name;format="camel"$) => created($name;format="camel"$)
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
    Actions().orElse(get$name;format="Camel"$Command)
  }
}

case class $name;format="Camel"$Aggregate(id: UUID, name: String, description: String)

object $name;format="Camel"$Aggregate {
  implicit val format: Format[$name;format="Camel"$Aggregate] = Json.format
}

sealed trait $name;format="Camel"$Command

case object Get$name;format="Camel"$Query extends $name;format="Camel"$Command with ReplyType[Option[$name;format="Camel"$Resource]] {
  implicit val format: Format[Get$name;format="Camel"$Query.type] = singletonFormat(Get$name;format="Camel"$Query)
}

case class Create$name;format="Camel"$Command($name;format="camel"$: $name;format="Camel"$Request) extends $name;format="Camel"$Command with ReplyType[Done]

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

case class $name;format="Camel"$CreatedEvent($name;format="camel"$: $name;format="Camel"$AggregateEntity) extends $name;format="Camel"$Event

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

  def selectAll$name;format="Camel"$s: Future[Seq[$name;format="Camel"$]] = {
    logger.info("Querying all $plural_name$...")
    session.selectAll(
      """
      SELECT * FROM $name;format="camel"$
    """).map(rows => rows.map(row => convert$name;format="Camel"$(row)))
  }

  def select$name;format="Camel"$(id: UUID) = {
    logger.info(s"Querying '$name$' with ID \$id...")
    session.selectOne("SELECT * FROM $name;format="camel"$ WHERE id = ?", id)
  }

  private def convert$name;format="Camel"$($name;format="camel"$Row: Row): $name;format="Camel"$ = {
    $name;format="Camel"$(
      $name;format="camel"$Row.getUUID("id"),
      $name;format="camel"$Row.getString("name"),
      $name;format="camel"$Row.getString("description"))
  }
}

private[impl] class $name;format="Camel"$EventProcessor(session: CassandraSession, readSide: CassandraReadSide)(implicit ec: ExecutionContext)
  extends ReadSideProcessor[$name;format="Camel"$Event] {
  private val logger = LoggerFactory.getLogger(classOf[$name;format="Camel"$EventProcessor])

  private var insert$name;format="Camel"$Statement: PreparedStatement = _

  override def buildHandler: ReadSideProcessor.ReadSideHandler[$name;format="Camel"$Event] = {
    readSide.builder[$name;format="Camel"$Event]("$name;format="camel"$EventOffset")
      .setGlobalPrepare(createTables)
      .setPrepare(_ => prepareStatements())
      .setEventHandler[$name;format="Camel"$Created](e => {
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
        CREATE TABLE IF NOT EXISTS $name;format="camel"$ (
          id timeuuid PRIMARY KEY,
          name text,
          description text
        )
      """)
    } yield Done
  }

  private def prepareStatements() = {
    logger.info("Preparing statements...")
    for {
      insert$name;format="Camel"$ <- session.prepare(
        """
        INSERT INTO $name;format="camel"$(
          id,
          name,
          description
        ) VALUES (?, ?, ?)
      """)
    } yield {
      insert$name;format="Camel"$Statement = insert$name;format="Camel"$
      Done
    }
  }

  private def insert$name;format="Camel"$($name;format="camel"$: $name;format="Camel"$) = {
    logger.info(s"Inserting \$$name$...")
    Future.successful(List(
      insert$name;format="Camel"$Statement.bind($name;format="camel"$.id, $name;format="camel"$.name, $name;format="camel"$.description)
    ))
  }
}

// $name$ Serializer Registry

object $name;format="Camel"$SerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    JsonSerializer[$name;format="Camel"$Resource],
    JsonSerializer[Create$name;format="Camel"$Request],
    JsonSerializer[Create$name;format="Camel"$Response],
    JsonSerializer[Get$name;format="Camel"$Response],
    JsonSerializer[GetAll$plural_name;format="Camel"$Response],
    JsonSerializer[$name;format="Camel"$],
    JsonSerializer[$name;format="Camel"$],
    JsonSerializer[$name;format="Camel"$],

    JsonSerializer[Create$name;format="Camel"$],
    JsonSerializer[Get$name;format="Camel"$.type],

    JsonSerializer[$name;format="Camel"$Created]
  )
}