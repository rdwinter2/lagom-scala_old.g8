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

class $name;format="Camel"$ServiceImpl(registry: PersistentEntityRegistry,
                      readSideConnector: $name;format="Camel"$ReadSideConnector)
                     (implicit ec: ExecutionContext, mat: Materializer) extends $name;format="Camel"$Service with Marshaller {

  override def $name;format="camel"$Events: Topic[$name;format="Camel"$KafkaEvent] =
    TopicProducer.taggedStreamWithOffset($name;format="Camel"$Event.Tag.allTags.to[immutable.Seq]) { (tag, offset) =>
      registry.eventStream(tag, offset)
        .filter { evt =>
          evt.event match {
            case _: $name;format="Camel"$Created => true
            case _: $name;format="Camel"$Verified => true
            case _: $name;format="Camel"$UnVerified => true
            case _: $name;format="Camel"$Deleted => true
            case _ => false
          }
        }
        .map(ev => ev.event match {
          case $name;format="Camel"$Created(id, $name;format="camel"$name, _, _, email) =>
            ($name;format="Camel"$KafkaEvent($name;format="Camel"$EventTypes.REGISTERED, id, Map("$name;format="camel"$name" -> $name;format="camel"$name, "email" -> email)), ev.offset)
          case $name;format="Camel"$Deleted(id) =>
            ($name;format="Camel"$KafkaEvent($name;format="Camel"$EventTypes.DELETED, id), ev.offset)
          case $name;format="Camel"$Verified(id) =>
            ($name;format="Camel"$KafkaEvent($name;format="Camel"$EventTypes.VERIFIED, id), ev.offset)
          case $name;format="Camel"$UnVerified(id) =>
            ($name;format="Camel"$KafkaEvent($name;format="Camel"$EventTypes.UNVERIFIED, id), ev.offset)
        })
    }

  private def refFor($name;format="camel"$Id: UUID) = registry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id.toString)

  private def get$name;format="Camel"$IdFromHeader(rh: RequestHeader): Future[Either[ErrorResponse, UUID]] =
    rh.getHeader("Authorization")
      .toRight(ER.UnAuthorized("Missing Authorization header"))
      .fold[Future[Either[ErrorResponse, UUID]]](
      e => Future.successful(Left(e)),
      auth =>
        readSideConnector
          .get$name;format="Camel"$IdFromAccessToken(UUID.fromString(auth)))

  override def $name;format="camel"$Login: ServiceCall[AuthRequest, Either[ErrorResponse, AuthResponse]] =
    ServerServiceCall((_, request) =>
      readSideConnector
        .get$name;format="Camel"$IdFrom$name;format="Camel"$name(request.$name;format="camel"$name)
        .flatMap(_.fold[Future[Either[ErrorResponse, AuthResponse]]](
          e => Future.successful(Left(e)),
          $name;format="camel"$Id =>
            refFor($name;format="camel"$Id)
              .ask(GrantAccessToken(request.password))
              .map(_.map(s => AuthResponse(s.access_token, s.expiry, s.refresh_token)))))
        .map(_.marshall)
    )

  override def get$name;format="Camel"$Auth: ServiceCall[String, Either[ErrorResponse, AuthInfo]] =
    ServerServiceCall((rh, req) =>
      get$name;format="Camel"$IdFromHeader(rh)
        .flatMap(_.fold[Future[Either[ErrorResponse, UUID]]](
          e => Future.successful(Left(e)),
          $name;format="camel"$Id =>
            refFor($name;format="camel"$Id)
              .ask(IsSessionExpired)
              .map(isExpired =>
                if (isExpired) {
                  Left(ER.UnAuthorized("Session expired"))
                } else {
                  Right($name;format="camel"$Id)
                })))
        .flatMap(_.fold[Future[Either[ErrorResponse, $name;format="Camel"$Response]]](
          e => Future.successful(Left(e)),
          $name;format="camel"$Id => readSideConnector.get$name;format="Camel"$($name;format="camel"$Id)))
        .map(_.fold[Either[ErrorResponse, AuthInfo]](
          e => Left(e),
          $name;format="camel"$ => Right(AuthInfo($name;format="camel"$))))
        .map(_.marshall)
    )

  override def revokeToken: ServiceCall[NotUsed, Done] =
    ServerServiceCall((rh, _) =>
      get$name;format="Camel"$IdFromHeader(rh)
        .flatMap(_.fold[Future[Done]](
          _ => Future.successful(Done),
          $name;format="camel"$Id => refFor($name;format="camel"$Id).ask(RevokeAccessToken)))
        .map(done => (ResponseHeader.Ok, done))
    )

  override def refreshToken: ServiceCall[String, Either[ErrorResponse, AuthResponse]] =
    ServerServiceCall((_, refresh_token) =>
      readSideConnector
        .get$name;format="Camel"$IdFromRefreshToken(UUID.fromString(refresh_token))
        .flatMap(_.fold[Future[Either[ErrorResponse, AuthResponse]]](
          e => Future.successful(Left(e)),
          $name;format="camel"$Id =>
            refFor($name;format="camel"$Id)
              .ask(ExtendAccessToken(UUID.fromString(refresh_token)))
              .map(_.map(s => AuthResponse(s.access_token, s.expiry, s.refresh_token)))))
        .map(_.marshall)
    )

  override def verify$name;format="Camel"$($name;format="camel"$Id: UUID): ServiceCall[NotUsed, Done] =
    ServiceCall(_ => refFor($name;format="camel"$Id).ask(Verify$name;format="Camel"$))

  override def create$name;format="Camel"$: ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, $name;format="Camel"$Response]] =
    ServerServiceCall((_, req) =>
      readSideConnector
        .get$name;format="Camel"$IdFrom$name;format="Camel"$name(req.$name;format="camel"$name)
        .flatMap(_.fold(
          e => {
            val $name;format="camel"$Id: UUID = UUID.randomUUID()
            refFor($name;format="camel"$Id)
              .ask(Create$name;format="Camel"$($name;format="camel"$Id, req.$name;format="camel"$name, req.password, req.email))
              .map(_ => Right($name;format="Camel"$Response($name;format="camel"$Id, req.$name;format="camel"$name, req.email, verified = false)))},
          _ => Future.successful(Left(ER.Conflict("$name;format="Camel"$name taken")))))
        .map(_.marshall)
    )

  override def get$name;format="Camel"$($name;format="camel"$Id: UUID): ServiceCall[NotUsed, Either[ErrorResponse, $name;format="Camel"$Response]] =
    ServerServiceCall((_, _) =>
      readSideConnector
        .get$name;format="Camel"$($name;format="camel"$Id)
        .map(_.fold[Either[ErrorResponse, $name;format="Camel"$Response]](
          e => Left(e),
          Right(_)))
        .map(_.marshall)
    )

  override def get$name;format="Camel"$s: ServiceCall[NotUsed, Seq[$name;format="Camel"$Response]] =
    ServiceCall(_ =>
      readSideConnector
        .get$name;format="Camel"$s
        .map(_.toSeq)
    )

  override def delete$name;format="Camel"$($name;format="camel"$Id: UUID): ServiceCall[NotUsed, Done] =
    ServiceCall(_ =>
      refFor($name;format="camel"$Id).ask(Delete$name;format="Camel"$)
    )

  override def unVerify$name;format="Camel"$($name;format="camel"$Id: UUID): ServiceCall[NotUsed, Done] =
    ServiceCall(_ =>
      refFor($name;format="camel"$Id).ask(UnVerify$name;format="Camel"$)
    )
}


/**
  * Implementation of the $name;format="Camel"$Service.

class $name;format="Camel"$ServiceImpl(
  persistentEntityRegistry: PersistentEntityRegistry,
  $name;format="camel"$Repository: $name;format="Camel"$Repository
  )(implicit ec: ExecutionContext)
  extends $name;format="Camel"$Service {

  override def create$name;format="Camel"$: ServiceCall[api.$name;format="Camel"$, api.$name;format="Camel"$] =  ServerServiceCall { $name;format="camel"$ =>
    val $name;format="camel"$Id = UUIDs.timeBased()
    val p$name;format="Camel"$ = $name;format="Camel"$($name;format="camel"$Id, $name;format="camel"$.creator, $name;format="camel"$.title, $name;format="camel"$.description, $name;format="camel"$.currencyId, $name;format="camel"$.increment,
      $name;format="camel"$.reservePrice, None, $name;format="Camel"$Status.Created, None, None, None)
    entityRef($name;format="camel"$Id).ask(Create$name;format="Camel"$(p$name;format="Camel"$)).map { _ =>
      convert$name;format="Camel"$(p$name;format="Camel"$)
    }
  }

  override def get$name;format="Camel"$(id: UUID): ServiceCall[NotUsed, api.$name;format="Camel"$] = ServerServiceCall { _ =>
    entityRef(id).ask(Get$name;format="Camel"$).map {
      case Some($name;format="camel"$) => convert$name;format="Camel"$($name;format="camel"$)
      case None => throw NotFound("$name$ " + id + " not found");
    }
  }

//  override def getAll$plural_name;format="Camel"$(page: Option[String]): ServiceCall[NotUsed, utils.PagingState[$name;format="Camel"$Summary]] = ServiceCall { _ =>
//    $name;format="camel"$Repository.get$name;format="Camel"$sFor$name;format="Camel"$(id, status, page, DefaultFetchSize)
//  }

  private def convert$name;format="Camel"$($name;format="camel"$: $name;format="Camel"$): api.$name;format="Camel"$ = {
    api.$name;format="Camel"$(Some($name;format="camel"$.id), $name;format="camel"$.creator, $name;format="camel"$.title, $name;format="camel"$.description, $name;format="camel"$.currencyId, $name;format="camel"$.increment,
      $name;format="camel"$.reservePrice, $name;format="camel"$.price, convertStatus($name;format="camel"$.status), $name;format="camel"$.auctionStart, $name;format="camel"$.auctionEnd,
      $name;format="camel"$.auctionWinner)
  }

  private def convertStatus(status: $name;format="Camel"$Status.Status): api.$name;format="Camel"$Status.Status = {
    status match {
      case $name;format="Camel"$Status.Created => api.$name;format="Camel"$Status.Created
      case $name;format="Camel"$Status.Auction => api.$name;format="Camel"$Status.Auction
      case $name;format="Camel"$Status.Completed => api.$name;format="Camel"$Status.Completed
      case $name;format="Camel"$Status.Cancelled => api.$name;format="Camel"$Status.Cancelled
    }
  }

  private def entityRef($name;format="camel"$Id: UUID) = entityRefString($name;format="camel"$Id.toString)

  private def entityRefString($name;format="camel"$Id: String) = persistentEntityRegistry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id)
}
*/