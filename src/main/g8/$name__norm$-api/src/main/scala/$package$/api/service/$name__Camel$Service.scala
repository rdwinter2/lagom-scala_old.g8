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
    named("$name;format="norm"$")
      .withCalls(
        restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$", create$name;format="Camel"$),
        restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$/:id", get$name;format="Camel"$ _)
//        restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$?page", getAll$plural_name;format="Camel"$ _),
        // POST restCall for other domain commands = post to a REST respource
        // restCall(Method.POST, "/api/$plural_name;format="camel"$"/:id/startAuction, startAuction _)
      )
//      .withTopics(
//        topic($name;format="Camel"$Service.TOPIC_NAME, $name;format="lower,snake"$)
          // Kafka partitions messages, messages within the same partition will
          // be delivered in order, to ensure that all messages for the same user
          // go to the same partition (and hence are delivered in order with respect
          // to that user), we configure a partition key strategy that extracts the
          // name as the partition key.
//          .addProperty(
//            KafkaProperties.partitionKeyStrategy,
//            PartitionKeyStrategy[GreetingMessageChanged](_.name)
//          )
//      )
      .withAutoAcl(true)
    // @formatter:on
  }

  /**
    * Create a $name;format="camel"$.
    *
    * @return The created $name;format="camel"$ with its ID populated.
    *
    * Example:
    * curl -H "Content-Type: application/json" -X POST -d '{"data1": "123", "data2": "xyz"}' http://localhost:9000/api/$plural_name;format="lower,hyphen"$
    */
  def create$name;format="Camel"$: ServiceCall[$name;format="Camel"$, $name;format="Camel"$]

  /**
    * Get a $name;format="camel"$ with the given ID.
    *
    * @param id The ID of the $name;format="camel"$ to get.
    * @return The $name;format="camel"$.
    *
    * Example:
    * curl http://localhost:9000/api/$plural_name;format="lower,hyphen"$/123e4567-e89b-12d3-a456-426655440000
    */
  def get$name;format="Camel"$(id: UUID): ServiceCall[NotUsed, $name;format="Camel"$]

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

}