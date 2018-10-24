package com.example.circusAnimal.impl

import com.example.common.authentication.AuthenticationServiceComposition._
import com.example.common.authentication.TokenContent
import com.example.common.utils.JsonFormats._
import com.example.common.utils.{ErrorResponse, Marshaller, ErrorResponses => ER}
import com.example.common.validation.ValidationUtil._
import com.example.circusAnimal.api._

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

class CircusAnimalServiceImpl(registry: PersistentEntityRegistry,
                      readSideConnector: CircusAnimalReadSideConnector)
                     (implicit ec: ExecutionContext, mat: Materializer) extends CircusAnimalService with Marshaller {

  override def circusAnimalEvents: Topic[CircusAnimalKafkaEvent] =
    TopicProducer.taggedStreamWithOffset(CircusAnimalEvent.Tag.allTags.to[immutable.Seq]) { (tag, offset) =>
      registry.eventStream(tag, offset)
        .filter { evt =>
          evt.event match {
            case _: CircusAnimalCreated => true
            case _: CircusAnimalVerified => true
            case _: CircusAnimalUnVerified => true
            case _: CircusAnimalDeleted => true
            case _ => false
          }
        }
        .map(ev => ev.event match {
          case CircusAnimalCreated(id, circusAnimalname, _, _, email) =>
            (CircusAnimalKafkaEvent(CircusAnimalEventTypes.REGISTERED, id, Map("circusAnimalname" -> circusAnimalname, "email" -> email)), ev.offset)
          case CircusAnimalDeleted(id) =>
            (CircusAnimalKafkaEvent(CircusAnimalEventTypes.DELETED, id), ev.offset)
          case CircusAnimalVerified(id) =>
            (CircusAnimalKafkaEvent(CircusAnimalEventTypes.VERIFIED, id), ev.offset)
          case CircusAnimalUnVerified(id) =>
            (CircusAnimalKafkaEvent(CircusAnimalEventTypes.UNVERIFIED, id), ev.offset)
        })
    }

  private def refFor(circusAnimalId: UUID) = registry.refFor[CircusAnimalEntity](circusAnimalId.toString)

  private def getCircusAnimalIdFromHeader(rh: RequestHeader): Future[Either[ErrorResponse, UUID]] =
    rh.getHeader("Authorization")
      .toRight(ER.UnAuthorized("Missing Authorization header"))
      .fold[Future[Either[ErrorResponse, UUID]]](
      e => Future.successful(Left(e)),
      auth =>
        readSideConnector
          .getCircusAnimalIdFromAccessToken(UUID.fromString(auth)))

  override def circusAnimalLogin: ServiceCall[AuthRequest, Either[ErrorResponse, AuthResponse]] =
    ServerServiceCall((_, request) =>
      readSideConnector
        .getCircusAnimalIdFromCircusAnimalname(request.circusAnimalname)
        .flatMap(_.fold[Future[Either[ErrorResponse, AuthResponse]]](
          e => Future.successful(Left(e)),
          circusAnimalId =>
            refFor(circusAnimalId)
              .ask(GrantAccessToken(request.password))
              .map(_.map(s => AuthResponse(s.access_token, s.expiry, s.refresh_token)))))
        .map(_.marshall)
    )

  override def getCircusAnimalAuth: ServiceCall[String, Either[ErrorResponse, AuthInfo]] =
    ServerServiceCall((rh, req) =>
      getCircusAnimalIdFromHeader(rh)
        .flatMap(_.fold[Future[Either[ErrorResponse, UUID]]](
          e => Future.successful(Left(e)),
          circusAnimalId =>
            refFor(circusAnimalId)
              .ask(IsSessionExpired)
              .map(isExpired =>
                if (isExpired) {
                  Left(ER.UnAuthorized("Session expired"))
                } else {
                  Right(circusAnimalId)
                })))
        .flatMap(_.fold[Future[Either[ErrorResponse, CircusAnimalResponse]]](
          e => Future.successful(Left(e)),
          circusAnimalId => readSideConnector.getCircusAnimal(circusAnimalId)))
        .map(_.fold[Either[ErrorResponse, AuthInfo]](
          e => Left(e),
          circusAnimal => Right(AuthInfo(circusAnimal))))
        .map(_.marshall)
    )

  override def revokeToken: ServiceCall[NotUsed, Done] =
    ServerServiceCall((rh, _) =>
      getCircusAnimalIdFromHeader(rh)
        .flatMap(_.fold[Future[Done]](
          _ => Future.successful(Done),
          circusAnimalId => refFor(circusAnimalId).ask(RevokeAccessToken)))
        .map(done => (ResponseHeader.Ok, done))
    )

  override def refreshToken: ServiceCall[String, Either[ErrorResponse, AuthResponse]] =
    ServerServiceCall((_, refresh_token) =>
      readSideConnector
        .getCircusAnimalIdFromRefreshToken(UUID.fromString(refresh_token))
        .flatMap(_.fold[Future[Either[ErrorResponse, AuthResponse]]](
          e => Future.successful(Left(e)),
          circusAnimalId =>
            refFor(circusAnimalId)
              .ask(ExtendAccessToken(UUID.fromString(refresh_token)))
              .map(_.map(s => AuthResponse(s.access_token, s.expiry, s.refresh_token)))))
        .map(_.marshall)
    )

  override def verifyCircusAnimal(circusAnimalId: UUID): ServiceCall[NotUsed, Done] =
    ServiceCall(_ => refFor(circusAnimalId).ask(VerifyCircusAnimal))

  override def createCircusAnimal: ServiceCall[CreateCircusAnimalRequest, Either[ErrorResponse, CircusAnimalResponse]] =
    ServerServiceCall((_, req) =>
      readSideConnector
        .getCircusAnimalIdFromCircusAnimalname(req.circusAnimalname)
        .flatMap(_.fold(
          e => {
            val circusAnimalId: UUID = UUID.randomUUID()
            refFor(circusAnimalId)
              .ask(CreateCircusAnimal(circusAnimalId, req.circusAnimalname, req.password, req.email))
              .map(_ => Right(CircusAnimalResponse(circusAnimalId, req.circusAnimalname, req.email, verified = false)))},
          _ => Future.successful(Left(ER.Conflict("CircusAnimalname taken")))))
        .map(_.marshall)
    )

  override def getCircusAnimal(circusAnimalId: UUID): ServiceCall[NotUsed, Either[ErrorResponse, CircusAnimalResponse]] =
    ServerServiceCall((_, _) =>
      readSideConnector
        .getCircusAnimal(circusAnimalId)
        .map(_.fold[Either[ErrorResponse, CircusAnimalResponse]](
          e => Left(e),
          Right(_)))
        .map(_.marshall)
    )

  override def getCircusAnimals: ServiceCall[NotUsed, Seq[CircusAnimalResponse]] =
    ServiceCall(_ =>
      readSideConnector
        .getCircusAnimals
        .map(_.toSeq)
    )

  override def deleteCircusAnimal(circusAnimalId: UUID): ServiceCall[NotUsed, Done] =
    ServiceCall(_ =>
      refFor(circusAnimalId).ask(DeleteCircusAnimal)
    )

  override def unVerifyCircusAnimal(circusAnimalId: UUID): ServiceCall[NotUsed, Done] =
    ServiceCall(_ =>
      refFor(circusAnimalId).ask(UnVerifyCircusAnimal)
    )
}


/**
  * Implementation of the CircusAnimalService.

class CircusAnimalServiceImpl(
  persistentEntityRegistry: PersistentEntityRegistry,
  circusAnimalRepository: CircusAnimalRepository
  )(implicit ec: ExecutionContext)
  extends CircusAnimalService {

  override def createCircusAnimal: ServiceCall[api.CircusAnimal, api.CircusAnimal] =  ServerServiceCall { circusAnimal =>
    val circusAnimalId = UUIDs.timeBased()
    val pCircusAnimal = CircusAnimal(circusAnimalId, circusAnimal.creator, circusAnimal.title, circusAnimal.description, circusAnimal.currencyId, circusAnimal.increment,
      circusAnimal.reservePrice, None, CircusAnimalStatus.Created, None, None, None)
    entityRef(circusAnimalId).ask(CreateCircusAnimal(pCircusAnimal)).map { _ =>
      convertCircusAnimal(pCircusAnimal)
    }
  }

  override def getCircusAnimal(id: UUID): ServiceCall[NotUsed, api.CircusAnimal] = ServerServiceCall { _ =>
    entityRef(id).ask(GetCircusAnimal).map {
      case Some(circusAnimal) => convertCircusAnimal(circusAnimal)
      case None => throw NotFound("Circus Animal " + id + " not found");
    }
  }

//  override def getAllCircusAnimals(page: Option[String]): ServiceCall[NotUsed, utils.PagingState[CircusAnimalSummary]] = ServiceCall { _ =>
//    circusAnimalRepository.getCircusAnimalsForCircusAnimal(id, status, page, DefaultFetchSize)
//  }

  private def convertCircusAnimal(circusAnimal: CircusAnimal): api.CircusAnimal = {
    api.CircusAnimal(Some(circusAnimal.id), circusAnimal.creator, circusAnimal.title, circusAnimal.description, circusAnimal.currencyId, circusAnimal.increment,
      circusAnimal.reservePrice, circusAnimal.price, convertStatus(circusAnimal.status), circusAnimal.auctionStart, circusAnimal.auctionEnd,
      circusAnimal.auctionWinner)
  }

  private def convertStatus(status: CircusAnimalStatus.Status): api.CircusAnimalStatus.Status = {
    status match {
      case CircusAnimalStatus.Created => api.CircusAnimalStatus.Created
      case CircusAnimalStatus.Auction => api.CircusAnimalStatus.Auction
      case CircusAnimalStatus.Completed => api.CircusAnimalStatus.Completed
      case CircusAnimalStatus.Cancelled => api.CircusAnimalStatus.Cancelled
    }
  }

  private def entityRef(circusAnimalId: UUID) = entityRefString(circusAnimalId.toString)

  private def entityRefString(circusAnimalId: String) = persistentEntityRegistry.refFor[CircusAnimalEntity](circusAnimalId)
}
*/