package $package$.impl

import $organization$.common.utils.{ErrorResponse, ErrorResponses => ER}
import $package$.api._

import akka.Done
import com.lightbend.lagom.scaladsl.persistence._
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import play.api.libs.json.{Format, Json}
import java.time.LocalDateTime
import java.util.UUID
import scala.collection.immutable.Seq
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.LoggerFactory

class $name;format="Camel"$Entity extends PersistentEntity {

  //Command
  override type Command = $name;format="Camel"$Command[_]

  //Event
  override type Event = $name;format="Camel"$Event

  //Aggregate
  override type State = $name;format="Camel"$State
  override def initialState: $name;format="Camel"$Aggregate = $name;format="Camel"$State.newEntity

  //FSM
  override def behavior: Behavior = {
    case $name;format="Camel"$State.None => newEntity
    case $name;format="Camel"$State(Some($name;format="camel"$)) => existingEntity
  }

//  private val log = LoggerFactory.getLogger(classOf[$name;format="Camel"$Entity])

  def newEntity: Actions = {
    Actions()

      .onCommand[Create$name;format="Camel"$Command, Create$name;format="Camel"$Response] {
        case (Create$name;format="Camel"$Command(
          $name;format="camel"$Id,
          naturalKey,
          data
          ), ctx, state) =>
          ctx.thenPersist(
            $name;format="Camel"$CreatedEvent(
              $name;format="camel"$Id = $name;format="camel"$Id,
              naturalKey = naturalKey,
              data = data
              )
          ) { _ =>
            ctx.reply(Create$name;format="Camel"$Response(entityId))
          }
      }
      .onEvent {
       case ($name;format="Camel"$CreatedEvent(
         $name;format="camel"$Id,
         naturalKey,
         data
         ), _) =>
         $name;format="Camel"$State(Some(
           $name;format="Camel"$(
             $name;format="camel"$Id = $name;format="camel"$Id,
             naturalKey = naturalKey,
             data = data
             )))
     }
     .onReadOnlyCommand[Get$name;format="Camel"$Command, $name;format="Camel"$State] {
       case (Get$name;format="Camel"$Command(id), ctx, state) =>
        ctx.reply(state)
    }
  }

  def existingEntity: Actions = {
    Actions()
      .onCommand[Create$name;format="Camel"$Command, Create$name;format="Camel"$Response] {
        case (Create$name;format="Camel"$Command(
          $name;format="camel"$Id,
          naturalKey,
          data
          ), ctx, state) =>
          ctx.invalidCommand(s"$name$ with id \${entityId} is already exists")
          ctx.done
      }
      .onReadOnlyCommand[Get$name;format="Camel"$Command, $name;format="Camel"$State] {
       case (Get$name;format="Camel"$Command(id), ctx, state) =>
        ctx.reply(state)
      }
  }

}