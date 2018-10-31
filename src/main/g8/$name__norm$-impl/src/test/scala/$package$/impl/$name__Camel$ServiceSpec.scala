package $package$.impl

import $package$.api
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
      val input$name;format="Camel"$ = api.$name;format="Camel"$(None, "name", "description")
      for {
        created$name;format="Camel"$ <- $name;format="camel"$Service.create$name;format="Camel"$.invoke(input$name;format="Camel"$)
      } yield {
        created$name;format="Camel"$.safeId should not be null
        created$name;format="Camel"$.name should be("name")
        created$name;format="Camel"$.description should be("description")
      }
    }

    "allow looking up a created $name$" in {
      val input$name;format="Camel"$ = api.$name;format="Camel"$(None, "name", "description")
      for {
        created$name;format="Camel"$ <- $name;format="camel"$Service.create$name;format="Camel"$.invoke(input$name;format="Camel"$)
        lookup$name;format="Camel"$ <- $name;format="camel"$Service.get$name;format="Camel"$(created$name;format="Camel"$.safeId).invoke
      } yield {
        created$name;format="Camel"$ should ===(lookup$name;format="Camel"$)
      }
    }

    "allow looking up all created $name$" in {
      val input$name;format="Camel"$1 = api.$name;format="Camel"$(None, "name1", "description1")
      val input$name;format="Camel"$2 = api.$name;format="Camel"$(None, "name2", "description2")
      val input$name;format="Camel"$3 = api.$name;format="Camel"$(None, "name3", "description3")
      (for {
        created$name;format="Camel"$1 <- $name;format="camel"$Service.create$name;format="Camel"$.invoke(input$name;format="Camel"$1)
        created$name;format="Camel"$2 <- $name;format="camel"$Service.create$name;format="Camel"$.invoke(input$name;format="Camel"$2)
        created$name;format="Camel"$3 <- $name;format="camel"$Service.create$name;format="Camel"$.invoke(input$name;format="Camel"$3)
      } yield {
        awaitSuccess() {
          for {
            lookup$name;format="Camel"$sResponse <- $name;format="camel"$Service.get$name;format="Camel"$s.invoke
          } yield {
            lookup$name;format="Camel"$sResponse.$name;format="camel"$s should contain allOf(created$name;format="Camel"$1, created$name;format="Camel"$2, created$name;format="Camel"$3)
          }
        }
      }).flatMap(identity)
    }

    "publish $name;format="camel"$ events on the Kafka topic" in {
      implicit val system = server.actorSystem
      implicit val mat = server.materializer

      for {
        created$name;format="Camel"$ <- $name;format="camel"$Service.create$name;format="Camel"$.invoke(api.$name;format="Camel"$(None, "name", "description"))
        events <- $name;format="camel"$Service.$name;format="camel"$Events.subscribe.atMostOnceSource
          .filter(_.id == created$name;format="Camel"$.safeId)
          .take(1)
          .runWith(Sink.seq)
      } yield {
        events.size shouldBe 1
        events.head shouldBe an[api.$name;format="Camel"$Created]
        val event = events.head.asInstanceOf[api.$name;format="Camel"$Created]
        event.name should be("name")
        event.description should be("description")
      }
    }

    "publish newly created $plural_name$ on the PubSub topic" in {
      $name;format="camel"$Service.$name;format="camel"$Stream.invoke.map { source =>
        implicit val system = server.actorSystem
        implicit val mat = server.materializer

        val $name;format="camel"$1 = api.$name;format="Camel"$(None, "name", "description")
        val $name;format="camel"$2 = api.$name;format="Camel"$(None, "name2", "description2")
        val $name;format="camel"$3 = api.$name;format="Camel"$(None, "name3", "description3")
        val probe = source.runWith(TestSink.probe)
        probe.request(3)

        $name;format="camel"$Service.create$name;format="Camel"$.invoke($name;format="camel"$1)
        $name;format="camel"$Service.create$name;format="Camel"$.invoke($name;format="camel"$2)
        $name;format="camel"$Service.create$name;format="Camel"$.invoke($name;format="camel"$3)

        probe.expectNextUnordered($name;format="camel"$1, $name;format="camel"$2, $name;format="camel"$3)
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