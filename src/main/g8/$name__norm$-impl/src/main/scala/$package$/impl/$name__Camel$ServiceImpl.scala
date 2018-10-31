package $package$.impl

import $organization$.common.authentication.AuthenticationServiceComposition._
import $organization$.common.authentication.TokenContent
import $organization$.common.utils.JsonFormats._
import $organization$.common.utils.{ErrorResponse, Marshaller, ErrorResponses => ER}
import $organization$.common.validation.ValidationUtil._
import $package$.api._

import akka.{Done, NotUsed}
import akka.stream.Materializer
import com.datastax.driver.core.utils.UUIDs
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.transport._
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import com.lightbend.lagom.scaladsl.server.ServerServiceCall
import java.util.UUID
import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}

class $name;format="Camel"$ServiceImpl(
  persistentEntityRegistry: PersistentEntityRegistry,
  tdsTargetRepository: $name;format="Camel"$Repository
)(implicit ec: ExecutionContext) extends $name;format="Camel"$Service {

  override def create$name;format="Camel"$: ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] = ServiceCall { request =>
    validate(request)

    refFor($name;format="camel"$Id).ask(
      Create$name;format="Camel"$Command(
        naturalKey = request.naturalKey,
        data = request.data
        )
      )
  }

  override def get$name;format="Camel"$($name;format="camel"$Id: String): ServiceCall[NotUsed, Either[ErrorResponse, Get$name;format="Camel"$Response]] = ServiceCall { _ =>
    tdsTargetRepository.get$name;format="Camel"$($name;format="camel"$Id)
  }

  private def refFor($name;format="camel"$Id: String) = persistentEntityRegistry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id)
}