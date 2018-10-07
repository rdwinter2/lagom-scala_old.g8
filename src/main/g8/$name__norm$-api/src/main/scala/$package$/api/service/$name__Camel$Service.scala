package $package$.api.service

import $package$.api.aggregate.$name;format="Camel"$
import $package$.api.request._
import $package$.api.response._

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{KafkaProperties, PartitionKeyStrategy}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import java.util.UUID
import play.api.libs.json.{Format, Json}

object $name;format="Camel"$Service  {
  val TOPIC_NAME = "$name;format="camel"$"
}

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
    named("$name;format="norm"$")
      .withCalls(
        restCall(Method.GET, "/api/$plural_name;format="packaged"$/:$name;format="camel"$Id", get$name;format="Camel"$ _),
        restCall(Method.GET, "/api/$plural_name;format="packaged"$", getAll$plural_name;format="Camel"$ _),
        restCall(Method.POST, "/api/$plural_name;format="packaged"$", create$name;format="Camel"$ _)
        // POST restCall for other domain commands = post to a REST respource
        // restCall(Method.POST, "/api/$plural_name;format="packaged"$"/:$name;format="camel"$Id/startAuction, startAuction _)
      )
      .withTopics(
        topic($name;format="Camel"$Service.TOPIC_NAME, $name;format="camel"$)
          // Kafka partitions messages, messages within the same partition will
          // be delivered in order, to ensure that all messages for the same user
          // go to the same partition (and hence are delivered in order with respect
          // to that user), we configure a partition key strategy that extracts the
          // name as the partition key.
          .addProperty(
            KafkaProperties.partitionKeyStrategy,
            PartitionKeyStrategy[GreetingMessageChanged](_.name)
          )
      )
      .withAutoAcl(true)
    // @formatter:on
  }

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  def hello(id: String): ServiceCall[NotUsed, String]

  /**
    * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
    * "Hi"}' http://localhost:9000/api/hello/Alice
    */
  def useGreeting(id: String): ServiceCall[GreetingMessage, Done]


  /**
    * This gets published to Kafka.
    */
  def greetingsTopic(): Topic[GreetingMessageChanged]

}

/**
  * The greeting message class.
  */
case class GreetingMessage(message: String)

object GreetingMessage {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[GreetingMessage] = Json.format[GreetingMessage]
}



/**
  * The greeting message class used by the topic stream.
  * Different than [[GreetingMessage]], this message includes the name (id).
  */
case class GreetingMessageChanged(name: String, message: String)

object GreetingMessageChanged {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[GreetingMessageChanged] = Json.format[GreetingMessageChanged]
}
