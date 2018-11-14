package $package$.impl

import $package$.api._
import $package$.api.$name;format="Camel"$Service
import $organization$.common.authentication.TokenContent
import pdi.jwt.{JwtAlgorithm, JwtClaim, JwtJson}
import com.typesafe.config.ConfigFactory
import java.util.UUID
import java.net.URI
import play.api.libs.json.{Format, Json}
import com.lightbend.lagom.scaladsl.api.transport.{ Method, MessageProtocol }
//import com.lightbend.lagom.scaladsl.api.transport.RequestHeader.RequestHeaderImpl

import akka.{ Done, NotUsed }
import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.TestKit
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.{ RequestHeader, ResponseHeader }
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import com.lightbend.lagom.scaladsl.server.{ LagomApplication, LocalServiceLocator, ServerServiceCall }
import com.lightbend.lagom.scaladsl.testkit.{ PersistentEntityTestDriver, ServiceTest, TestTopicComponents }
import cool.graph.cuid._
import org.scalatest.{ AsyncWordSpec, BeforeAndAfterAll, Matchers }
import play.api.http.Status
import play.api.libs.ws.ahc.AhcWSComponents
import scala.concurrent.duration._
//import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ ExecutionContext, Future, Promise }

class $name;format="Camel"$ServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {


  val secret = ConfigFactory.load().getString("jwt.secret")
  val algorithm = JwtAlgorithm.HS512
  val authExpiration = ConfigFactory.load().getInt("jwt.token.auth.expirationInSeconds")
  val tokenContent = TokenContent(
            clientId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            username = "testuser"
          )
  val authClaim = JwtClaim(Json.toJson(tokenContent).toString())
    .expiresIn(authExpiration)
    .issuedNow
  val authToken = JwtJson.encode(authClaim, secret, algorithm)
  //println(authToken)
  val requestHeader = RequestHeader(Method.POST, URI.create("/"), MessageProtocol.empty, Nil, None, Nil)

  private val server = ServiceTest.startServer(
    ServiceTest.defaultSetup.withCassandra(true)) { ctx =>
    new LagomApplication(ctx) with $name;format="Camel"$Components with LocalServiceLocator with AhcWSComponents with TestTopicComponents
  }

  val $name;format="camel"$Service: $name;format="Camel"$Service = server.serviceClient.implement[$name;format="Camel"$Service]

  override protected def afterAll(): Unit = server.stop()

  "$name$ service" should {
    "allow creating an $name$" in {
      val $name;format="camel"$ = $name;format="Camel"$("name", Some("description"))
      val create$name;format="Camel"$Request = Create$name;format="Camel"$Request($name;format="camel"$)
      for {
        created$name;format="Camel"$ <- $name;format="camel"$Service.create$name;format="Camel"$WithSystemGeneratedId.handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke(create$name;format="Camel"$Request)
      } yield {
        created$name;format="Camel"$.id should not be null
        created$name;format="Camel"$.$name;format="camel"$.name should be("name")
        created$name;format="Camel"$.$name;format="camel"$.description should be(Some("description"))
      }
    }

    "allow looking up a created $name$" in {
      val $name;format="camel"$ = $name;format="Camel"$("name", Some("description"))
      val create$name;format="Camel"$Request = Create$name;format="Camel"$Request($name;format="camel"$)
      for {
        created$name;format="Camel"$ <- $name;format="camel"$Service.create$name;format="Camel"$WithSystemGeneratedId.handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke(create$name;format="Camel"$Request)
        lookup$name;format="Camel"$ <- $name;format="camel"$Service.get$name;format="Camel"$(created$name;format="Camel"$.id).handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke
      } yield {
        created$name;format="Camel"$.id should ===(lookup$name;format="Camel"$.id)
        created$name;format="Camel"$.$name;format="camel"$ should ===(lookup$name;format="Camel"$.$name;format="camel"$)
      }
    }

    "allow looking up all created $name$" in {
      val id1 = Cuid.createCuid()
      val id2 = Cuid.createCuid()
      val id3 = Cuid.createCuid()
      val $name;format="camel"$1 = $name;format="Camel"$("name", Some("description1"))
      val $name;format="camel"$2 = $name;format="Camel"$("name", Some("description2"))
      val $name;format="camel"$3 = $name;format="Camel"$("name", Some("description3"))
      (for {
        created$name;format="Camel"$1 <- $name;format="camel"$Service.create$name;format="Camel"$(id1).handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke(Create$name;format="Camel"$Request($name;format="camel"$1))
        created$name;format="Camel"$2 <- $name;format="camel"$Service.create$name;format="Camel"$(id2).handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke(Create$name;format="Camel"$Request($name;format="camel"$2))
        created$name;format="Camel"$3 <- $name;format="camel"$Service.create$name;format="Camel"$(id3).handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke(Create$name;format="Camel"$Request($name;format="camel"$3))
      } yield {
        awaitSuccess() {
          for {
            lookup$plural_name;format="Camel"$Response <- $name;format="camel"$Service.getAll$plural_name;format="Camel"$.handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke
          } yield {
            lookup$plural_name;format="Camel"$Response.$name;format="camel"$s should contain allOf($name;format="Camel"$Resource(id1, created$name;format="Camel"$1.$name;format="camel"$), $name;format="Camel"$Resource(id2, created$name;format="Camel"$2.$name;format="camel"$), $name;format="Camel"$Resource(id3, created$name;format="Camel"$3.$name;format="camel"$))
          }
        }
      }).flatMap(identity)
    }

    "publish $name;format="camel"$ events on the Kafka topic" in {
      implicit val system = server.actorSystem
      implicit val mat = server.materializer
      val $name;format="camel"$ = $name;format="Camel"$("name", Some("description"))
      val create$name;format="Camel"$Request = Create$name;format="Camel"$Request($name;format="camel"$)
      val id = Cuid.createCuid()

      for {
        created$name;format="Camel"$ <- $name;format="camel"$Service.create$name;format="Camel"$(id).handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke(create$name;format="Camel"$Request)
        events <- $name;format="camel"$Service.$name;format="camel"$MessageBrokerEvents.subscribe.atMostOnceSource
          .filter(_.id == created$name;format="Camel"$.id)
          .take(1)
          .runWith(Sink.seq)
      } yield {
        events.size shouldBe 1
        events.head shouldBe an[$name;format="Camel"$Created]
        val event = events.head.asInstanceOf[$name;format="Camel"$Created]
        event.$name;format="camel"$.name should be("name")
        event.$name;format="camel"$.description should be(Some("description"))
      }
    }

    "publish newly created $plural_name$ on the PubSub topic" in {
      $name;format="camel"$Service.stream$plural_name;format="Camel"$.invoke.map { source =>
        implicit val system = server.actorSystem
        implicit val mat = server.materializer

        val id1 = Cuid.createCuid()
        val id2 = Cuid.createCuid()
        val id3 = Cuid.createCuid()
        val $name;format="camel"$1 = $name;format="Camel"$("name", Some("description"))
        val $name;format="camel"$2 = $name;format="Camel"$("name2", Some("description2"))
        val $name;format="camel"$3 = $name;format="Camel"$("name3", Some("description3"))
//println("before create")
        val probe = source.runWith(TestSink.probe)
        probe.request(3)

        $name;format="camel"$Service.create$name;format="Camel"$(id1).handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke(Create$name;format="Camel"$Request($name;format="camel"$1))
        $name;format="camel"$Service.create$name;format="Camel"$(id2).handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke(Create$name;format="Camel"$Request($name;format="camel"$2))
        $name;format="camel"$Service.create$name;format="Camel"$(id3).handleRequestHeader(requestHeader => requestHeader.withHeader("Authorization", "Bearer " + authToken.toString)).invoke(Create$name;format="Camel"$Request($name;format="camel"$3))

//println("after create")
        probe.expectNextUnordered($name;format="Camel"$Resource(id1, $name;format="camel"$1), $name;format="Camel"$Resource(id1, $name;format="camel"$2), $name;format="Camel"$Resource(id1, $name;format="camel"$3))
//println("after probe")
        probe.cancel()
        succeed
      }
    }
  }

  def awaitSuccess[T](maxDuration: FiniteDuration = 10.seconds, checkEvery: FiniteDuration = 100.milliseconds)(block: => Future[T]): Future[T] = {
    val checkUntil = System.currentTimeMillis() + maxDuration.toMillis

    def doCheck(): Future[T] = {
      block.recoverWith {
        case recheck if checkUntil > System.currentTimeMillis() =>
          val timeout = Promise[T]()
          server.application.actorSystem.scheduler.scheduleOnce(checkEvery) {
            timeout.completeWith(doCheck())
          }(server.executionContext)
          timeout.future
      }
    }

    doCheck()
  }
}