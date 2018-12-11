package $package$.api

//import $organization$.common.regex.Matchers
import $organization$.common.response.ErrorResponse
import $organization$.common.utils.JsonFormats._
import $organization$.common.validation.ValidationViolationKeys._

import ai.x.play.json.Jsonx
import akka.{Done, NotUsed}
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{
  KafkaProperties,
  PartitionKeyStrategy
}
import com.lightbend.lagom.scaladsl.api.deser.{
  DefaultExceptionSerializer,
  PathParamSerializer
}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wix.accord.Descriptions._
import java.time.{Duration, Instant}
import java.util.UUID
import julienrf.json.derived
import play.api.{Environment, Mode}
import play.api.libs.json._

//object $name;format="Camel"$Service  {
//  val TOPIC_NAME = "agg.event.$name;format="lower,snake,word"$"
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
      restCall(Method.POST,    "/api/$plural_name;format="lower,hyphen"$",                     create$name;format="Camel"$WithSystemGeneratedId _),
      restCall(Method.POST,    "/$plural_name;format="lower,hyphen"$/:id/$name;format="lower,hyphen"$-creation/:creationId",   create$name;format="Camel"$ _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id/destroy-$name;format="norm"$", destroy$name;format="Camel"$ _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id/improve-$name;format="norm"$-description", improve$name;format="Camel"$Description _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id/archive-$name;format="norm"$", archive$name;format="Camel"$ _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id/unarchive-$name;format="norm"$", unarchive$name;format="Camel"$ _),
//      pathCall("/api/$plural_name;format="lower,hyphen"$/stream", stream$plural_name;format="Camel"$ _),
      //restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id", get$name;format="Camel"$ _),
      //restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$", getAll$plural_name;format="Camel"$ _)
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
    * Rest api allowing an authenticated user to create a "$name$" aggregate.
    *
    * @return HTTP 200 status code if the "$name$" was created successfully.
    *         HTTP 404 status code if one or more items in the [[Create$name;format="Camel"$Request]] failed vaildation.
    *         HTTP 409 status code if the "$name$" already exists with the same identity.
    *
    * Example:
    * curl -H "Content-Type: application/json" -X POST -d '{"$name;format="camel"$": {"name": "test", "description": "test description"}}' http://localhost:9000/api/$plural_name;format="lower,hyphen"$
    */
  def create$name;format="Camel"$WithSystemGeneratedId
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]

  /**
    * Rest api allowing an authenticated user to create a "$name$" aggregate.
    *
    * @param $name;format="camel"$Id unique identifier of the "$name$" to be created.
    * @return HTTP 200 status code if the "$name$" was created successfully.
    *         HTTP 404 status code if one or more items in the [[Create$name;format="Camel"$Request]] failed vaildation.
    *
    * Example:
    * curl -H "Content-Type: application/json" -X POST -d '{"$name;format="camel"$": {"name": "test", "description": "test description"}}' http://localhost:9000/api/$plural_name;format="lower,hyphen"$/{$name;format="camel"$Id}/create-$name;format="norm"$
    */
  def create$name;format="Camel"$($name;format="camel"$Id: String, commandId: String)
    : ServiceCall[Create$name;format="Camel"$Request, Create$name;format="Camel"$Response]

  //def destroy$name;format="Camel"$($name;format="camel"$Id: String)
  //  : ServiceCall[NotUsed, Done]

//  def improve$name;format="Camel"$Description($name;format="camel"$Id: String)
//    : ServiceCall[Improve$name;format="Camel"$DescriptionRequest, Improve$name;format="Camel"$DescriptionResponse]

  /**
    * Get a "$name$" with the given surrogate key ID.
    *
    * @param $name;format="camel"$Id The ID of the "$name$" to get.
    * @return HTTP 200 status code with the current state of the "$name$" resource.
    *
    * Example:
    * curl http://localhost:9000/api/$plural_name;format="lower,hyphen"$/123e4567-e89b-12d3-a456-426655440000
    */
  //def get$name;format="Camel"$(
  //    $name;format="camel"$Id: String): ServiceCall[NotUsed, Get$name;format="Camel"$Response]

  /**
    * Get all "$plural_name$".
    *
    * @return A list of "$name$" resources.
    *
    * Example:
    * curl http://localhost:9000/api/$plural_name;format="lower,hyphen"$
    */
//  def getAll$plural_name;format="Camel"$(page: Option[String]): ServiceCall[NotUsed, utils.PagingState[GetAll$plural_name;format="Camel"$Response]]
  //def getAll$plural_name;format="Camel"$: ServiceCall[NotUsed, GetAll$plural_name;format="Camel"$Response]

//  def stream$plural_name;format="Camel"$
//    : ServiceCall[NotUsed, Source[$name;format="Camel"$Resource, NotUsed]]

// $name$ Topic

  def $name;format="camel"$MessageBrokerEvents: Topic[$name;format="Camel"$MessageBrokerEvent]

}

// $name$ regex matchers

object Matchers {
  val Email =
    """^[a-zA-Z0-9\.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"""
  val Id = """^[a-zA-Z0-9\-\.\_\~]{1,64}\$"""
  val Name = """^[a-zA-Z0-9\-\.\_\~]{1,128}\$"""
  val Description = """^.{1,2048}\$"""
}

// $name$ algebraic data type {
//
// An algebraic data type is a kind of composite type.
// They are built up from Product types and Sum types.
//
// Product types - a tuple or record (this and that)
//   class ScalaPerson(val name: String, val age: Int)
//
// Sum types - a disjoint union or variant type (this or that)
//   sealed trait Pet
//   case class Cat(name: String) extends Pet
//   case class Fish(name: String, color: String) extends Pet
//   case class Squid(name: String, age: Int) extends Pet

case class $name;format="Camel"$(
  name: String,
  description: Option[String])

object $name;format="Camel"$ {
  implicit val format: Format[$name;format="Camel"$] = Jsonx.formatCaseClass

  val $name;format="camel"$Validator: Validator[$name;format="Camel"$] =
    validator[$name;format="Camel"$] { $name;format="camel"$ =>
      $name;format="camel"$.name is notEmpty
      $name;format="camel"$.name should matchRegexFully(Matchers.Name)
      $name;format="camel"$.description.each should matchRegexFully(Matchers.Description)
    }
}

case class Identity(
  identifier: String,
  transactionClock: Option[Int])

object Identity {
  implicit val format: Format[Identity] = Jsonx.formatCaseClass

  val $name;format="camel"$Validator: Validator[Identity] =
    validator[Identity] { identity =>
      identity.identifier is notEmpty
      identity.identifier should matchRegexFully(Matchers.Id)
      // need Option[Int]
      //identity.transactionClock should be >= 0
    }
}
// }

// Resource

case class $name;format="Camel"$Resource(
  $name;format="camel"$Id: String,
  $name;format="camel"$: $name;format="Camel"$
)

object $name;format="Camel"$Resource {
  implicit val format: Format[$name;format="Camel"$Resource] = Jsonx.formatCaseClass

  val $name;format="camel"$ResourceValidator: Validator[$name;format="Camel"$Resource] =
    validator[$name;format="Camel"$Resource] { $name;format="camel"$Resource =>
      $name;format="camel"$Resource.$name;format="camel"$Id is notEmpty
      $name;format="camel"$Resource.$name;format="camel"$Id should matchRegexFully(Matchers.Id)
      $name;format="camel"$Resource.$name;format="camel"$ is valid($name;format="Camel"$.$name;format="camel"$Validator)
    }
}

// Request

// TODO: include span ID as the unique identity of a Create$name;format="Camel"$Request
case class Create$name;format="Camel"$Request(
    $name;format="camel"$: $name;format="Camel"$
) {}

case object Create$name;format="Camel"$Request {
  implicit val format: Format[Create$name;format="Camel"$Request] = Jsonx.formatCaseClass

  implicit val create$name;format="Camel"$RequestValidator
    : Validator[Create$name;format="Camel"$Request] =
    validator[Create$name;format="Camel"$Request] { create$name;format="Camel"$Request =>
      create$name;format="Camel"$Request.$name;format="camel"$ is valid($name;format="Camel"$.$name;format="camel"$Validator)
    }
}

case class Improve$name;format="Camel"$DescriptionRequest(
    description: Option[String]
) {}

case object Improve$name;format="Camel"$DescriptionRequest {
  implicit val format: Format[Improve$name;format="Camel"$DescriptionRequest] = Jsonx.formatCaseClass

  implicit val mutate$name;format="Camel"$RequestValidator
    : Validator[Improve$name;format="Camel"$DescriptionRequest] =
    validator[Improve$name;format="Camel"$DescriptionRequest] { improve$name;format="Camel"$DescriptionRequest =>
      improve$name;format="Camel"$DescriptionRequest.description.each should matchRegexFully(Matchers.Description)
    }
}

// Response

case class Improve$name;format="Camel"$DescriptionResponse(
    $name;format="camel"$Id: String,
    description: Option[String]
)

object Improve$name;format="Camel"$DescriptionResponse {
  implicit val format: Format[Improve$name;format="Camel"$DescriptionResponse] = Json.format
}

case class Create$name;format="Camel"$Response(
    $name;format="camel"$Id: String,
    $name;format="camel"$: $name;format="Camel"$
)

object Create$name;format="Camel"$Response {
  implicit val format: Format[Create$name;format="Camel"$Response] = Json.format
}

case class Get$name;format="Camel"$Response(
    $name;format="camel"$Id: String,
    $name;format="camel"$: $name;format="Camel"$
)

object Get$name;format="Camel"$Response {
  implicit val format: Format[Get$name;format="Camel"$Response] = Json.format
}

case class GetAll$plural_name;format="Camel"$Response($name;format="camel"$s: Seq[$name;format="Camel"$Resource])

object GetAll$plural_name;format="Camel"$Response {
  implicit val format: Format[GetAll$plural_name;format="Camel"$Response] = Json.format
}

// Message Broker Event
// One service to many other services

sealed trait $name;format="Camel"$MessageBrokerEvent {
  val $name;format="camel"$Id: String
}

case class $name;format="Camel"$Created(
    $name;format="camel"$Id: String,
    $name;format="camel"$: $name;format="Camel"$
) extends $name;format="Camel"$MessageBrokerEvent

object $name;format="Camel"$Created {
  implicit val format: Format[$name;format="Camel"$Created] = Json.format
}

//case class $name;format="Camel"$BrokerEvent(event: $name;format="Camel"$EventType,
//                          id: String,
//                          data: Map[String, String] = Map.empty[String, String])

object $name;format="Camel"$MessageBrokerEvent {
  implicit val format: Format[$name;format="Camel"$MessageBrokerEvent] =
    derived.flat.oformat((__ \ "type").format[String])
}

//object $name;format="Camel"$EventTypes extends Enumeration {
//  type $name;format="Camel"$EventType = Value
//  val REGISTERED, DELETED, VERIFIED, UNVERIFIED = Value
//
//  implicit val format: Format[$name;format="Camel"$EventType] = enumFormat($name;format="Camel"$EventTypes)
//}
