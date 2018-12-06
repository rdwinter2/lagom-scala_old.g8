<!--- to transclude content use the following syntax at the beginning of a line "transclude::file_name.ext::[an optional regular expression]" -->
<!--- Never directly edit transcluded content, always edit the source file -->
# $name$

This project has been generated using the rdwinter2/lagom-scala.g8 template.

For instructions on running and testing the project, see https://www.lagomframework.com/get-started-scala.html.

To generate a new project execute the following and supply values for (name, plural_name, organization, version, and package):

```bash
sbt new rdwinter2/lagom-scala.g8
```

The REST call identifiers for the $name$ project are defined as:
<!--- transclude::api/$name;format="Camel"$Service.scala::[override final def descriptor = {] cjpckxxdz00001xn3lki5t2ps -->

```scala
  override final def descriptor = {
    import Service._
    // @formatter:off
    named("$name;format="norm"$").withCalls(
      restCall(Method.POST,    "/api/$plural_name;format="lower,hyphen"$",                                    create$name;format="Camel"$WithSystemGeneratedId _),
      restCall(Method.POST,    "/$plural_name;format="lower,hyphen"$/:id/create/:commandId",   create$name;format="Camel"$ _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id/destroy-$name;format="norm"$", destroy$name;format="Camel"$ _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id/improve-$name;format="norm"$-description", improve$name;format="Camel"$Description _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id/archive-$name;format="norm"$", archive$name;format="Camel"$ _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id/unarchive-$name;format="norm"$", unarchive$name;format="Camel"$ _),
//      pathCall("/api/$plural_name;format="lower,hyphen"$/stream", stream$plural_name;format="Camel"$ _),
      //restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$/:$name;format="camel"$Id", get$name;format="Camel"$ _),
      //restCall(Method.GET, "/api/$plural_name;format="lower,hyphen"$", getAll$plural_name;format="Camel"$ _)
    )
      .withAutoAcl(true)
      .withExceptionSerializer(new DefaultExceptionSerializer(Environment.simple(mode = Mode.Prod)))
      .withTopics(
        topic("$name;format="camel"$-$name;format="Camel"$MessageBrokerEvent", this.$name;format="camel"$MessageBrokerEvents)
      )
    // @formatter:on
  }
```

<!--- transclude cjpckxxdz00001xn3lki5t2ps -->

The algebraic data type for $name$ is defined as:
<!--- transclude::api/$name;format="Camel"$Service.scala::[$name$ algebraic data type {] cjpckxxf400011xn3kjp99lrd -->

```scala
// $name$ algebraic data type {
//
// An algebraic data type is a kind of composite type.
// They are built up from Product types and Sum types.
//
// Product types - a tuple or record (this and that)
//   class ScalaPerson(val name: String, val age: Int)
//
// Sum types - a disjoint union or variant type (this or that)
//   sealed trait Pet
//   case class Cat(name: String) extends Pet
//   case class Fish(name: String, color: String) extends Pet
//   case class Squid(name: String, age: Int) extends Pet

case class $name;format="Camel"$(
  name: String,
  description: Option[String])

object $name;format="Camel"$ {
  implicit val format: Format[$name;format="Camel"$] = Jsonx.formatCaseClass

  val $name;format="camel"$Validator: Validator[$name;format="Camel"$] =
    validator[$name;format="Camel"$] { $name;format="camel"$ =>
      $name;format="camel"$.name is notEmpty
      $name;format="camel"$.name should matchRegexFully(Matchers.Name)
      $name;format="camel"$.description.each should matchRegexFully(Matchers.Description)
    }
}

case class Identity(
  identifier: String,
  transactionClock: Int)

object Identity {
  implicit val format: Format[Identity] = Jsonx.formatCaseClass

  val $name;format="camel"$Validator: Validator[Identity] =
    validator[Identity] { identity =>
      identity.identifier is notEmpty
      identity.identifier should matchRegexFully(Matchers.Id)
      identity.transactionClock should be >= 0
    }
}
// }
```

<!--- transclude cjpckxxf400011xn3kjp99lrd -->

With regular expression validation matchers:
<!--- transclude::api/$name;format="Camel"$Service.scala::[object Matchers {] cjpckxxgd00021xn3vhe5kpl0 -->

```scala
object Matchers {
  val Email =
    """^[a-zA-Z0-9\.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"""
  val Id = """^[a-zA-Z0-9\-\.\_\~]{1,64}\$"""
  val Name = """^[a-zA-Z0-9\-\.\_\~]{1,128}\$"""
  val Description = """^.{1,2048}\$"""
}
```

<!--- transclude cjpckxxgd00021xn3vhe5kpl0 -->

The REST resource for $name$ is defined as:

<!--- transclude::api/$name;format="Camel"$Service.scala::[case class $name;format="Camel"$Resource(] cjpckxxhq00031xn3nmjxe741 -->

```scala
case class $name;format="Camel"$Resource(
  $name;format="camel"$Id: String,
  $name;format="camel"$: $name;format="Camel"$
)
```

<!--- transclude cjpckxxhq00031xn3nmjxe741 -->

The DDD aggregate for $name$ is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[case class $name;format="Camel"$Aggregate(] cjpckxxjf00041xn3b0fgji0a -->

```scala
case class $name;format="Camel"$Aggregate(
    $name;format="camel"$Id: String,
    $name;format="camel"$: $name;format="Camel"$
)
```

<!--- transclude cjpckxxjf00041xn3b0fgji0a -->

The state for $name$ is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[case class $name;format="Camel"$State(] cjpckxxlg00051xn39upu5e7a -->

```scala
case class $name;format="Camel"$State(
  $name;format="camel"$Aggregate: Option[$name;format="Camel"$Aggregate],
  status: $name;format="Camel"$Status.Status = $name;format="Camel"$Status.NONEXISTENT,
  transactionClock: Int
)
```

<!--- transclude cjpckxxlg00051xn39upu5e7a -->

The possible statuses for the $name$ aggregate are defined to be:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[object $name;format="Camel"$Status extends Enumeration {] cjpckxxnn00061xn3h89gss4o -->

```scala
object $name;format="Camel"$Status extends Enumeration {
  val NONEXISTENT, ACTIVE, ARCHIVED = Value
  type Status = Value

  implicit val format: Format[Value] = enumFormat(this)
//  implicit val pathParamSerializer: PathParamSerializer[Status] =
//    PathParamSerializer.required("$name;format="camel"$Status")(withName)(_.toString)
}
```

<!--- transclude cjpckxxnn00061xn3h89gss4o -->

The entity for $name$ is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[final class $name;format="Camel"$Entity extends PersistentEntity {] cjpckxxpx00071xn3wirl3gwi -->

```scala
final class $name;format="Camel"$Entity extends PersistentEntity {

  //private val published$name;format="Camel"$CreatedEvent = pubSubRegistry.refFor(TopicId[$name;format="Camel"$CreatedEvent])

  override type Command = $name;format="Camel"$Command[_]
  override type Event = $name;format="Camel"$Event
  override type State = $name;format="Camel"$State

  type OnCommandHandler[M] = PartialFunction[(Command, CommandContext[M], State), Persist]
  type ReadOnlyHandler[M] = PartialFunction[(Command, ReadOnlyCommandContext[M], State), Unit]

  override def initialState: $name;format="Camel"$State = $name;format="Camel"$State.nonexistent

  // FSM
  override def behavior: Behavior = {
    case $name;format="Camel"$State(_, $name;format="Camel"$Status.NONEXISTENT, _) => nonexistent$name;format="Camel"$
    case $name;format="Camel"$State(_, $name;format="Camel"$Status.ACTIVE, _) => active$name;format="Camel"$
    case $name;format="Camel"$State(_, $name;format="Camel"$Status.ARCHIVED, _) => archived$name;format="Camel"$
    case $name;format="Camel"$State(_, _, _) => unknown$name;format="Camel"$
  }

  private def get$name;format="Camel"$Action = Actions()
    .onReadOnlyCommand[Get$name;format="Camel"$Query.type, $name;format="Camel"$State] {
      case (Get$name;format="Camel"$Query, ctx, state) => ctx.reply(state)
    }

  private val nonexistent$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { create$name;format="Camel"$Command }
        .onEvent {
          case ($name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate), state) => $name;format="Camel"$State(Some($name;format="camel"$Aggregate), $name;format="Camel"$Status.ACTIVE, 1)
        }
    }
  }

  private val active$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
    }
  }

  private val archived$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
    }
  }

  private val unknown$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
    }
  }

  private def create$name;format="Camel"$Command: OnCommandHandler[Either[ServiceError, $name;format="Camel"$Aggregate]] = {
    case (Create$name;format="Camel"$Command($name;format="camel"$Aggregate), ctx, state) =>
      ctx.thenPersist($name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate)) { evt =>
        ctx.reply(Right($name;format="camel"$Aggregate))
      }
  }

  private val notCreated = {
    get$name;format="Camel"$Action orElse {
    Actions()
      .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] {
        case (Create$name;format="Camel"$Command($name;format="camel"$Aggregate), ctx, state) =>
          ctx.thenPersist($name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate)) { evt =>
            ctx.reply(Right($name;format="camel"$Aggregate))
          }
      }
    }
  }

  private def replyConflict[R]: OnCommandHandler[Either[ServiceError, R]] = {
    case (_, ctx, _) =>
      ctx.reply(Left($name;format="Camel"$Conflict))
      ctx.done
  }

  private def created($name;format="camel"$Aggregate: $name;format="Camel"$Aggregate) = {
    get$name;format="Camel"$Action orElse {
    Actions()
//      .onCommand[Destroy$name;format="Camel"$Command.type, Done] {
//        case (Destroy$name;format="Camel"$Command, ctx, Some(u)) =>
//          ctx.thenPersist($name;format="Camel"$DestroyedEvent(u.id))(_ => ctx.reply(Done))
//      }
//      .onCommand[Improve$name;format="Camel"$DescripionCommand.type, Done] {
//        case (Improve$name;format="Camel"$DescripionCommand, ctx, Some(u)) =>
//          ctx.thenPersist($name;format="Camel"$DescripionImprovedEvent(improve$name;format="Camel"$DescripionRequest))(_ => ctx.reply(Done))
//      }
//      .onEvent {
//        case ($name;format="Camel"$DestroyedEvent(_), Some(u)) =>
//          None
//      }
//      .onEvent {
//        case ($name;format="Camel"$DescripionImprovedEvent(_), Some(u)) =>
//          None
//      }
    }
  }

}
```

<!--- transclude cjpckxxpx00071xn3wirl3gwi -->



```bash
-- With Bearer Auth Token
export AT=`./get-auth-token.sh`
sed 's/\r//'  $plural_name;format="lower,hyphen"$.csv | perl -MText::CSV -MJSON::MaybeXS=encode_json -lne '\$c=Text::CSV->new;\$c->parse(\$_);@C=\$c->fields if \$.==1;@F=\$c->fields;@L{@C}=@F;\$J{$name;format="camel"$}=\%L;\$l=encode_json \%J;`curl --show-error --header \"Authorization: Bearer \${'AT'}\" -H \"Content-Type: application/json\" -X POST -d \047\$l\047 http://localhost:9000/api/$plural_name;format="lower,hyphen"$/\$F[0]/create-$name;format="norm"$`unless \$.==1;'

```

```
For REST calls with DDD/CQRS/ES only use GET and POST
GET for queries
  pagination and expand for large resources
 POST for commands
   "Use POST APIs to create new subordinate resources" https://restfulapi.net/http-methods/
   A DDD command can be thought of as a subordinate resource to the DDD aggregate entity
   The command "could" have an identity and be queryable, for instance an async req/resp.
   A Saga needs to be implemented in this manner
   Command body should include a unique identifier, can be a span id
```
