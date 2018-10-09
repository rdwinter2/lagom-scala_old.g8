package $package$.impl.service

import $organization$.common.authentication.AuthenticationServiceComposition._
import $organization$.common.authentication.TokenContent
import $organization$.common.utils.JsonFormats._
import $organization$.common.validation.ValidationUtil._
//import $package$.api.aggregate._
import $package$.api.request._
import $package$.api.response._
import $package$.api.service.{$name;format="Camel"$Service}
import $package$.impl.domain._

import akka.{Done, NotUsed}
import com.datastax.driver.core.utils.UUIDs
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.{NotFound, PolicyViolation, TransportErrorCode, TransportException, BadRequest, Forbidden}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import com.lightbend.lagom.scaladsl.server.ServerServiceCall
import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

/**
  * Implementation of the $name;format="Camel"$Service.
  */
class $name;format="Camel"$ServiceImpl(
  persistentEntityRegistry: PersistentEntityRegistry,
  $name;format="camel"$Repository: $name;format="Camel"$Repository
  )(implicit ec: ExecutionContext)
  extends $name;format="Camel"$Service {

  override def create$name;format="Camel"$: ServiceCall[create$name;format="Camel"$, create$name;format="Camel"$] =  ServerServiceCall { $name;format="camel"$ =>
    val $name;format="camel"$Id = UUIDs.timeBased()
    val p$name;format="Camel"$ = $name;format="Camel"$($name;format="camel"$Id, $name;format="camel"$.creator, $name;format="camel"$.title, $name;format="camel"$.description, $name;format="camel"$.currencyId, $name;format="camel"$.increment,
      $name;format="camel"$.reservePrice, None, $name;format="Camel"$Status.Created, $name;format="camel"$.auctionDuration, None, None, None)
    entityRef($name;format="camel"$Id).ask(Create$name;format="Camel"$(p$name;format="Camel"$)).map { _ =>
      convert$name;format="Camel"$(p$name;format="Camel"$)
    }
  }

  override def get$name;format="Camel"$(id: UUID): ServiceCall[NotUsed, $name;format="Camel"$] = ServerServiceCall { _ =>
    entityRef(id).ask(Get$name;format="Camel"$).map {
      case Some(item) => convert$name;format="Camel"$(item)
      case None => throw NotFound("$name$ " + id + " not found");
    }
  }

//  override def getAll$plural_name;format="Camel"$(page: Option[String]): ServiceCall[NotUsed, utils.PagingState[$name;format="Camel"$Summary]] = ServiceCall { _ =>
//    $name;format="camel"$Repository.getItemsForUser(id, status, page, DefaultFetchSize)
//  }

  private def entityRef(itemId: UUID) = entityRefString(itemId.toString)

  private def entityRefString(itemId: String) = persistentEntityRegistry.refFor[ItemEntity](itemId)
}
