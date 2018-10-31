package $package$.impl

import $package$.api.$name;format="Camel"$Service
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents

class $name;format="Camel"$Loader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): $name;format="Camel"$Application =
    new $name;format="Camel"$Application(context) {
      override def serviceLocator: ServiceLocator = ServiceLocator.NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext) =
    new $name;format="Camel"$Application(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[$name;format="Camel"$Service])
}

abstract class $name;format="Camel"$Application(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents
    with CassandraPersistenceComponents
    with LagomKafkaComponents {

  override lazy val lagomServer: LagomServer = serverFor[$name;format="Camel"$Service](wire[$name;format="Camel"$ServiceImpl])
  override lazy val jsonSerializerRegistry: JsonSerializerRegistry = $name;format="Camel"$SerializerRegistry

  readSide.register(wire[$name;format="Camel"$ReadSideProcessor])
  persistentEntityRegistry.register(wire[$name;format="Camel"$Entity])
  lazy val readSideConnector: $name;format="Camel"$ReadSideConnector = wire[$name;format="Camel"$ReadSideConnector]
//  lazy val emailClient: EmailService = serviceClient.implement[EmailService]
//  wire[EmailEventListener]
}
