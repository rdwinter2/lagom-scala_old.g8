package $package$.impl.service

import $package$.api.aggregate._
import $package$.api.request._
import $package$.api.response._
import $package$.impl.domain._

import com.lightbend.lagom.scaladsl.playjson.{JsonSerializerRegistry, JsonSerializer}

/**
  * Akka serialization, used by both persistence and remoting, needs to have
  * serializers registered for every type serialized or deserialized. While it's
  * possible to use any serializer you want for Akka messages, out of the box
  * Lagom provides support for JSON, via this registry abstraction.
  *
  * The serializers are registered here, and then provided to Lagom in the
  * application loader.
  */
object $name;format="Camel"$SerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    // Aggregates
    JsonSerializer[$name;format="Camel"$],

    // Commands
    JsonSerializer[Create$name;format="Camel"$],
    JsonSerializer[StartAuction],
    JsonSerializer[UpdatePrice],
    JsonSerializer[FinishAuction],

    //Queries
    JsonSerializer[Get$name;format="Camel"$.type],

    // Events
    JsonSerializer[$name;format="Camel"$Created],
    JsonSerializer[AuctionStarted],
    JsonSerializer[PriceUpdated],
    JsonSerializer[AuctionFinished]
  )
}