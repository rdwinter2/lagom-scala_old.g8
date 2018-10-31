package $package$.impl

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import java.util.UUID
import org.scalatest.{BeforeAndAfterAll, Matchers, OptionValues, WordSpec}

class $name;format="Camel"$EntitySpec extends WordSpec with Matchers with BeforeAndAfterAll with OptionValues {

  private val system = ActorSystem("$name;format="Camel"$EntitySpec",
    JsonSerializerRegistry.actorSystemSetupFor($name;format="Camel"$SerializerRegistry))

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  private val id = UUID.randomUUID
  private val $name;format="camel"$ = $name;format="Camel"$(id, "name", "description")

  private def withTestDriver[T](block: PersistentEntityTestDriver[$name;format="Camel"$Command, $name;format="Camel"$Event, $name;format="Camel"$State] => T): T = {
    val driver = new PersistentEntityTestDriver(system, new $name;format="Camel"$Entity, id.toString)
    try {
      block(driver)
    } finally {
      driver.getAll$plural_name;format="Camel"$ shouldBe empty
    }
  }

  "$name$ entity" should {

    "allow creating an $name$" in withTestDriver { driver =>
      val outcome = driver.run(Create$name;format="Camel"$($name;format="camel"$))
      outcome.events should contain only $name;format="Camel"$Created($name;format="camel"$)
      outcome.state should ===(Some($name;format="camel"$))
    }

    "allow looking up an $name$" in withTestDriver { driver =>
      driver.run(Create$name;format="Camel"$($name;format="camel"$))
      val outcome = driver.run(Get$name;format="Camel"$)
      outcome.events shouldBe empty
      outcome.replies should contain only Some($name;format="camel"$)
      outcome.state should ===(Some($name;format="camel"$))
    }
  }
}
