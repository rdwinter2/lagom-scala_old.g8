package $package$.impl

import $package$.impl.$name;format="Camel"$Status.$name;format="Camel"$Status

import $organization$.common.utils.JsonFormats._
import com.lightbend.lagom.scaladsl.playjson.{JsonMigration, JsonSerializerRegistry, JsonSerializer}
import play.api.libs.json.{JsObject, JsString}

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
    // models
    JsonSerializer[$name;format="Camel"$Aggregate],
    JsonSerializer[$name;format="Camel"$Session],
    JsonSerializer[$name;format="Camel"$Status],

    // Commands
    JsonSerializer[Create$name;format="Camel"$],
    JsonSerializer[Verify$name;format="Camel"$.type],
    JsonSerializer[GrantAccessToken],
    JsonSerializer[ExtendAccessToken],
    JsonSerializer[RevokeAccessToken.type],
    JsonSerializer[IsSessionExpired.type],
    JsonSerializer[UnVerify$name;format="Camel"$.type],
    JsonSerializer[Delete$name;format="Camel"$.type],

    //Queries
    JsonSerializer[Get$name;format="Camel"$.type],

    // Events
    JsonSerializer[$name;format="Camel"$Verified],
    JsonSerializer[$name;format="Camel"$UnVerified],
    JsonSerializer[AccessTokenGranted],
    JsonSerializer[AccessTokenRevoked],
    JsonSerializer[$name;format="Camel"$Created],
    JsonSerializer[$name;format="Camel"$Deleted]
  )

  private val emailAdded = new JsonMigration(2) {
    override def transform(fromVersion: Int, json: JsObject): JsObject = {
      if (fromVersion < 2) {
        json + ("email" -> JsString("example@company.ca"))
      } else {
        json
      }
    }
  }

  override def migrations = Map[String, JsonMigration](
    classOf[Create$name;format="Camel"$].getName -> emailAdded,
    classOf[$name;format="Camel"$Created].getName -> emailAdded,
    classOf[$name;format="Camel"$Aggregate].getName -> emailAdded
  )
}