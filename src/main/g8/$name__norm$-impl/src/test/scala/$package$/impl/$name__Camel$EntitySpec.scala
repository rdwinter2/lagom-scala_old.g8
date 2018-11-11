package $package$.impl

import $package$.api._

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import java.util.UUID
import cool.graph.cuid._
import org.scalatest.{BeforeAndAfterAll, Matchers, OptionValues, WordSpec}

class $name;format="Camel"$EntitySpec extends WordSpec with Matchers with BeforeAndAfterAll with OptionValues {

  private val system = ActorSystem("$name;format="Camel"$EntitySpec",
    JsonSerializerRegistry.actorSystemSetupFor($name;format="Camel"$SerializerRegistry))

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  private val id = Cuid.createCuid()
  private val $name;format="camel"$ = $name;format="Camel"$("name", Some("description"))
  private val create$name;format="Camel"$Request = Create$name;format="Camel"$Request($name;format="camel"$)
  private val $name;format="camel"$Aggregate = $name;format="Camel"$Aggregate(id, $name;format="camel"$)
  private val $name;format="camel"$CreatedEvent = $name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate)

  private def withTestDriver[T](block: PersistentEntityTestDriver[$name;format="Camel"$Command, $name;format="Camel"$Event, Option[$name;format="Camel"$Aggregate]] => T): T = {
    val driver = new PersistentEntityTestDriver(system, new $name;format="Camel"$Entity, id)
    try {
      block(driver)
    } finally {
//      driver.getAll$plural_name;format="Camel"$ shouldBe empty
    }
  }

  "$name$ entity" should {

    "allow creating an $name$" in withTestDriver { driver =>
      val outcome = driver.run(Create$name;format="Camel"$Command($name;format="camel"$Aggregate))
      outcome.events should contain only $name;format="camel"$CreatedEvent
      outcome.state should ===(Some($name;format="camel"$Aggregate))
    }

//    "allow looking up an $name$" in withTestDriver { driver =>
//      driver.run(Create$name;format="Camel"$(create$name;format="Camel"$Request))
//      val outcome = driver.run(create$name;format="Camel"$Request)
//      outcome.events shouldBe empty
//      outcome.replies should contain only Some($name;format="camel"$Aggregate)
//      outcome.state should ===(Some($name;format="camel"$Aggregate))
//    }
  }
}
