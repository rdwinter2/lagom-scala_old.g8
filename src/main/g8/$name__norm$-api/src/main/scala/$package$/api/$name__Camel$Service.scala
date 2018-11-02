package $package$.api

import $organization$.common.regex.Matchers
import $organization$.common.utils.ErrorResponse
import $organization$.common.utils.JsonFormats._
import $organization$.common.validation.ValidationViolationKeys._

import ai.x.play.json.Jsonx
import akka.{Done, NotUsed}
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{KafkaProperties, PartitionKeyStrategy}
import com.lightbend.lagom.scaladsl.api.deser.{DefaultExceptionSerializer, PathParamSerializer}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import com.wix.accord._
import com.wix.accord.dsl._
import com.wix.accord.Descriptions._
import java.time.{Duration, Instant}
import java.util.UUID
import julienrf.json.derived
import play.api.{Environment, Mode}
import play.api.libs.json._

//object $name;format="Camel"$Service  {
//  val TOPIC_NAME = "agg.event.$name;format="lower,snake"$"
//}

/**
  * The $name$ service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the $name;format="Camel"$Service.
  */
trait $name;format="Camel"$Service extends Service {

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("$name;format="norm"$").withCalls(
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$", create$name;format="Camel"$ _),
      restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$/:id", get$name;format="Camel"$ _),
      restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$", getAll$plural_name;format="Camel"$ _),
      pathCall("/api/$plural_name;format="lower,hyphen"$/stream", subscribe$name;format="Camel"$Stream _)
    )
      .withAutoAcl(true)
      .withExceptionSerializer(new DefaultExceptionSerializer(Environment.simple(mode = Mode.Prod)))
      .withTopics(
        topic("$name;format="camel"$-$name;format="Camel"$MessageBrokerEvent", this.$name;format="camel"$MessageBrokerEvents)
      )
    // @formatter:on
  }

// $name$ Service Call

  /**
    * Create a "$name$".
    *
    * @return HTTP 200 status code if the "$name$" was created successfully.
    *         HTTP 404 status code if one or more items in the [[Create$name;format="Camel"$Request]] failed vaildation.
    *
    * Example:
    * curl -H "Content-Type: application/json" -X POST -d '{"name": "test", "description": "test description"}' http://localhost:9000/api/$plural_name;format="lower,hyphen"$
    */
  def create$name;format="Camel"$: ServiceCall[Create$name;format="Camel"$Request, Create$name;format="Camel"$Response]

  /**
    * Get a "$name$" with the given surrogate key ID.
    *
    * @param id The ID of the "$name$" to get.
    * @return HTTP 200 status code with the current state of the "$name$" resource.
    *
    * Example:
    * curl http://localhost:9000/api/$plural_name;format="lower,hyphen"$/123e4567-e89b-12d3-a456-426655440000
    */
  def get$name;format="Camel"$(id: UUID): ServiceCall[NotUsed, Get$name;format="Camel"$Response]

  /**
    * Get all "$plural_name$".
    *
    * @return A list of "$name$" resources.
    *
    * Example:
    * curl http://localhost:9000/api/$plural_name;format="lower,hyphen"$
    */
//  def getAll$plural_name;format="Camel"$(page: Option[String]): ServiceCall[NotUsed, utils.PagingState[GetAll$plural_name;format="Camel"$Response]]
  def getAll$plural_name;format="Camel"$: ServiceCall[NotUsed, GetAll$plural_name;format="Camel"$Response]

  def subscribe$name;format="Camel"$Stream: ServiceCall[NotUsed, Source[$name;format="Camel"$Resource, NotUsed]]

// $name$ Topic

  def $name;format="camel"$MessageBrokerEvents: Topic[$name;format="Camel"$MessageBrokerEvent]

}

// Resource

case class $name;format="Camel"$Resource(id: Option[UUID], name: String, description: String) {
  def safeId: UUID = id.getOrElse(UUID.randomUUID())
}

object $name;format="Camel"$Resource {
  implicit val format: Format[$name;format="Camel"$Resource] = Jsonx.formatCaseClass

  def create(name: String, description: String): $name;format="Camel"$Resource = {
    $name;format="Camel"$Resource(None, name, description)
  }
}

// Request

// TODO: include span ID as the unique identity of a Create$name;format="Camel"$Request
case class Create$name;format="Camel"$Request(
  name: String,
  description: String
)

object Create$name;format="Camel"$Request {
  implicit val format: Format[Create$name;format="Camel"$Request] = Jsonx.formatCaseClass

  implicit val Create$name;format="Camel"$RequestValidator = validator[Create$name;format="Camel"$Request] {u =>
    u.name is notEmpty
//    u.name as notEmptyKey("name") is notEmpty
//    u.name as matchRegexFullyKey("name") should matchRegexFully(Matchers.name)
  }
}

// Response

case class Create$name;format="Camel"$Response(id: UUID, name: String, description: String)

object Create$name;format="Camel"$Response {
  implicit val format: Format[Create$name;format="Camel"$Response] = Json.format
}

case class Get$name;format="Camel"$Response(id: UUID, name: String, description: String)

object Get$name;format="Camel"$Response {
  implicit val format: Format[Get$name;format="Camel"$Response] = Json.format
}

case class GetAll$plural_name;format="Camel"$Response($plural_name;format="camel"$: Seq[$name;format="Camel"$Resource])

object GetAll$plural_name;format="Camel"$Response {
  implicit val format: Format[GetAll$plural_name;format="Camel"$Response] = Json.format
}

// Message Broker Event
// One service to many other services

sealed trait $name;format="Camel"$MessageBrokerEvent {
  val id: UUID
}

case class $name;format="Camel"$Created(id: UUID, name: String, description: String) extends $name;format="Camel"$MessageBrokerEvent

object $name;format="Camel"$Created {
  implicit val format: Format[$name;format="Camel"$Created] = Json.format
}

//case class $name;format="Camel"$BrokerEvent(event: $name;format="Camel"$EventType,
//                          id: UUID,
//                          data: Map[String, String] = Map.empty[String, String])

object $name;format="Camel"$MessageBrokerEvent {
  implicit val format: Format[$name;format="Camel"$MessageBrokerEvent] = derived.flat.oformat((__ \ "type").format[String])
}

//object $name;format="Camel"$EventTypes extends Enumeration {
//  type $name;format="Camel"$EventType = Value
//  val REGISTERED, DELETED, VERIFIED, UNVERIFIED = Value
//
//  implicit val format: Format[$name;format="Camel"$EventType] = enumFormat($name;format="Camel"$EventTypes)
//}