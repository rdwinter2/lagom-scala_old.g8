package $package$.api

import $organization$.common.utils.JsonFormats._
import $package$.api.$name;format="Camel"$EventTypes.$name;format="Camel"$EventType
import $organization$.common.utils.ErrorResponse

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{KafkaProperties, PartitionKeyStrategy}
import com.lightbend.lagom.scaladsl.api.deser.DefaultExceptionSerializer
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import java.util.UUID
import play.api.{Environment, Mode}
import play.api.libs.json.{Format, Json}

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

  /**
    * Create a $name;format="camel"$.
    *
    * @return The created $name;format="camel"$ with its ID populated.
    *
    * Example:
    * curl -H "Content-Type: application/json" -X POST -d '{"creator": "71247ae7-81a2-478d-8895-484e35e75c95", "title": "xyz", "description": "abc", "currencyId": "USD", "increment": 2, "reservePrice": 4, "auctionDuration": 5}' http://localhost:9000/api/$plural_name;format="lower,hyphen"$
    */
  def create$name;format="Camel"$: ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, $name;format="Camel"$Response]]

  /**
    * Get a $name;format="camel"$ with the given ID.
    *
    * @param id The ID of the $name;format="camel"$ to get.
    * @return The $name;format="camel"$.
    *
    * Example:
    * curl http://localhost:9000/api/$plural_name;format="lower,hyphen"$/123e4567-e89b-12d3-a456-426655440000
    */
  def get$name;format="Camel"$($name;format="camel"$Id: UUID): ServiceCall[NotUsed, Either[ErrorResponse, $name;format="Camel"$Response]]

  /**
    * Get a $name;format="camel"$ with the given ID.
    *
    * @param id The ID of the $name;format="camel"$ to get.
    * @return The $name;format="camel"$.
    *
    * Example:
    * curl http://localhost:9000/api/$plural_name;format="lower,hyphen"$
    */
//  def getAll$plural_name;format="Camel"$(page: Option[String]): ServiceCall[NotUsed, utils.PagingState[$name;format="Camel"$Summary]]

  def $name;format="camel"$Events: Topic[$name;format="Camel"$KafkaEvent]
  override final def descriptor = {
    import Service._
    // @formatter:off
    named("$name;format="norm"$")
      .withCalls(
        restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$", create$name;format="Camel"$ _),
        restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$/:id", get$name;format="Camel"$ _)
//        restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$?page", getAll$plural_name;format="Camel"$ _),
        // POST restCall for other domain commands = post to a REST respource
        // restCall(Method.POST, "/api/$plural_name;format="camel"$"/:id/startAuction, startAuction _)
      )
//      .withTopics(
//        topic($name;format="Camel"$Service.TOPIC_NAME, $name;format="lower,snake"$)
          // Kafka partitions messages, messages within the same partition will
          // be delivered in order, to ensure that all messages for the same $name;format="camel"$
          // go to the same partition (and hence are delivered in order with respect
          // to that $name;format="camel"$), we configure a partition key strategy that extracts the
          // name as the partition key.
//          .addProperty(
//            KafkaProperties.partitionKeyStrategy,
//            PartitionKeyStrategy[GreetingMessageChanged](_.name)
//          )
//      )
      .withAutoAcl(true)
      .withExceptionSerializer(new DefaultExceptionSerializer(Environment.simple(mode = Mode.Prod)))
      .withTopics(topic("$name;format="Camel"$Events", $name;format="camel"$Events))
    // @formatter:on
  }

}

case class $name;format="Camel"$Response(id: UUID, $name;format="camel"$name: String, email: String, verified: Boolean)

object $name;format="Camel"$Response {
  implicit val format: Format[$name;format="Camel"$Response] = Json.format
}

case class Create$name;format="Camel"$Request($name;format="camel"$Name: String, email: String, password: String)

object Create$name;format="Camel"$Request {
  implicit val format: Format[Create$name;format="Camel"$Request] = Json.format
}

case class AuthRequest($name;format="camel"$name: String, password: String)

object AuthRequest {
  implicit val format: Format[AuthRequest] = Json.format
}

case class AuthResponse(access_token: UUID,
                        expiry: Long,
                        refresh_token: UUID)

object AuthResponse {
  implicit val format: Format[AuthResponse] = Json.format
}

case class AuthInfo($name;format="camel"$: $name;format="Camel"$Response)
object AuthInfo {
  implicit val format: Format[AuthInfo] = Json.format
}

case class $name;format="Camel"$KafkaEvent(event: $name;format="Camel"$EventType,
                          id: UUID,
                          data: Map[String, String] = Map.empty[String, String])

object $name;format="Camel"$KafkaEvent {
  implicit val format: Format[$name;format="Camel"$KafkaEvent] = Json.format
}

object $name;format="Camel"$EventTypes extends Enumeration {
  type $name;format="Camel"$EventType = Value
  val REGISTERED, DELETED, VERIFIED, UNVERIFIED = Value

  implicit val format: Format[$name;format="Camel"$EventType] = enumFormat($name;format="Camel"$EventTypes)
}
