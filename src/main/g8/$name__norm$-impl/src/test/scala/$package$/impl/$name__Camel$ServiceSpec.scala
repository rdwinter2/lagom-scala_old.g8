package $package$.impl

import $package$.api._
import $package$.api.$name;format="Camel"$Service


import akka.stream.scaladsl.Sink
import akka.stream.testkit.scaladsl.TestSink
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LocalServiceLocator}
import com.lightbend.lagom.scaladsl.testkit.{ServiceTest, TestTopicComponents}
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}
import play.api.libs.ws.ahc.AhcWSComponents
import scala.concurrent.duration._
import scala.concurrent.{Future, Promise}

class $name;format="Camel"$ServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

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
        created$name;format="Camel"$ <- $name;format="camel"$Service.create$name;format="Camel"$WithSystemGeneratedId.invoke(create$name;format="Camel"$Request)
      } yield {
        created$name;format="Camel"$.id should not be null
        created$name;format="Camel"$.$name;format="camel"$.name should be("name")
        created$name;format="Camel"$.$name;format="camel"$.description should be("description")
      }
    }

    "allow looking up a created $name$" in {
      val $name;format="camel"$ = $name;format="Camel"$("name", Some("description"))
      val create$name;format="Camel"$Request = Create$name;format="Camel"$Request($name;format="camel"$)
      for {
        created$name;format="Camel"$ <- $name;format="camel"$Service.create$name;format="Camel"$WithSystemGeneratedId.invoke(create$name;format="Camel"$Request)
        lookup$name;format="Camel"$ <- $name;format="camel"$Service.get$name;format="Camel"$(created$name;format="Camel"$.id).invoke
      } yield {
        created$name;format="Camel"$ should ===(lookup$name;format="Camel"$)
      }
    }

    "allow looking up all created $name$" in {
      val $name;format="camel"$1 = $name;format="Camel"$("name", Some("description1"))
      val $name;format="camel"$2 = $name;format="Camel"$("name", Some("description2"))
      val $name;format="camel"$3 = $name;format="Camel"$("name", Some("description3"))
      (for {
        created$name;format="Camel"$1 <- $name;format="camel"$Service.create$name;format="Camel"$.invoke(Create$name;format="Camel"$Request($name;format="camel"$1))
        created$name;format="Camel"$2 <- $name;format="camel"$Service.create$name;format="Camel"$.invoke(Create$name;format="Camel"$Request($name;format="camel"$2))
        created$name;format="Camel"$3 <- $name;format="camel"$Service.create$name;format="Camel"$.invoke(Create$name;format="Camel"$Request($name;format="camel"$3))
      } yield {
        awaitSuccess() {
          for {
            lookup$plural_name;format="Camel"$Response <- $name;format="camel"$Service.get$plural_name;format="Camel"$.invoke
          } yield {
            lookup$plural_name;format="Camel"$Response.$name;format="camel"$s should contain allOf(created$name;format="Camel"$1, created$name;format="Camel"$2, created$name;format="Camel"$3)
          }
        }
      }).flatMap(identity)
    }

    "publish $name;format="camel"$ events on the Kafka topic" in {
      implicit val system = server.actorSystem
      implicit val mat = server.materializer

      for {
        created$name;format="Camel"$ <- $name;format="camel"$Service.create$name;format="Camel"$.invoke($name;format="Camel"$("name", Some("description")))
        events <- $name;format="camel"$Service.$name;format="camel"$Events.subscribe.atMostOnceSource
          .filter(_.id == created$name;format="Camel"$.safeId)
          .take(1)
          .runWith(Sink.seq)
      } yield {
        events.size shouldBe 1
        events.head shouldBe an[$name;format="Camel"$Created]
        val event = events.head.asInstanceOf[$name;format="Camel"$Created]
        event.name should be("name")
        event.description should be("description")
      }
    }

    //"publish newly created $plural_name$ on the PubSub topic" in {
    //  $name;format="camel"$Service.$name;format="camel"$Stream.invoke.map { source =>
    //    implicit val system = server.actorSystem
    //    implicit val mat = server.materializer
    //
    //    val $name;format="camel"$1 = $name;format="Camel"$(None, "name", Some("description"))
    //    val $name;format="camel"$2 = $name;format="Camel"$(None, "name2", Some("description2"))
    //    val $name;format="camel"$3 = $name;format="Camel"$(None, "name3", Some("description3"))
    //    val probe = source.runWith(TestSink.probe)
    //    probe.request(3)
    //
    //    $name;format="camel"$Service.create$name;format="Camel"$.invoke($name;format="camel"$1)
    //    $name;format="camel"$Service.create$name;format="Camel"$.invoke($name;format="camel"$2)
    //    $name;format="camel"$Service.create$name;format="Camel"$.invoke($name;format="camel"$3)
    //
    //    probe.expectNextUnordered($name;format="camel"$1, $name;format="camel"$2, $name;format="camel"$3)
    //    probe.cancel()
    //    succeed
    //  }
    //}
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