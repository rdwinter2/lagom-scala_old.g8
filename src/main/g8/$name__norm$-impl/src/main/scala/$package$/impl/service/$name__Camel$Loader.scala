package $package$.impl.service

import $package$.api.service.{$name;format="Camel"$Service}
import $package$.impl.domain._

import akka.stream.Materializer
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.Environment
import play.api.libs.ws.ahc.AhcWSComponents
import scala.concurrent.ExecutionContext

trait $name;format="Camel"$Components extends LagomServerComponents
  with CassandraPersistenceComponents {

  implicit def executionContext: ExecutionContext
  def environment: Environment

  implicit def materializer: Materializer

  override lazy val lagomServer = serverFor[$name;format="Camel"$Service](wire[$name;format="Camel"$ServiceImpl])
  lazy val $name;format="camel"$Repository = wire[$name;format="Camel"$Repository]
  lazy val jsonSerializerRegistry = $name;format="Camel"$SerializerRegistry

  persistentEntityRegistry.register(wire[$name;format="Camel"$Entity])
  readSide.register(wire[$name;format="Camel"$EventProcessor])
}

abstract class $name;format="Camel"$Application(context: LagomApplicationContext)
  extends LagomApplication(context)
    with $name;format="Camel"$Components
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[$name;format="Camel"$Service](wire[$name;format="Camel"$ServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = $name;format="Camel"$SerializerRegistry

  // Register the $name$ persistent entity
  persistentEntityRegistry.register(wire[$name;format="Camel"$Entity])
}

class $name;format="Camel"$Loader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new $name;format="Camel"$Application(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new $name;format="Camel"$Application(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[$name;format="Camel"$Service])
}