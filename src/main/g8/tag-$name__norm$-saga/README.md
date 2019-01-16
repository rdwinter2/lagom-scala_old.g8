<!--- to transclude content use the following syntax at the beginning of a line "transclude::file_name.ext::[an optional regular expression]" -->
<!--- Never directly edit transcluded content, always edit the source file -->
$name$ Tag
===============

## <a name="overview"></a>Overview

This microservice handles the tagging of a $name$ aggregate.

During tagging it first verifies the $name$ aggregate exists. Then it verifies that the $name$ Tag aggregate exists. If the $name$ Tag aggregate does not exist, it creates it. Then it adds the $name$ Tag to the $name$ aggregate. Then it adds the $name$ identifier to the $name$ Tag aggregate. If either fails it rolls back the changes using compensating actions.

## Table of Contents
1. [Overview](#overview)
2. [Description](#description)
3. [System Design](#systemdesign)
3. [Domain Model](#domainmodel)
   * [External Event Flow](#externaleventflow)
   * [Internal Event Flow](#internaleventflow)
   * [Conceptual Data Model](#conceptualdatamodel)
2. [Microservices](#microservices)
   * [$name$](#helloworldmicroservice)
   * [Tag](#tagmicroservice)
   * [Tag $name$ Saga](#taghelloworldsagamicroservice)
   * [Authorization](#authorizationmicroservice)
   * [Authentication](#authenticationmicroservice)
2. [Glossary - Domain](#glossarydomain)
2. [Glossary - Technical](#glossarytechnical)
3. [References](#references)
4. [Notes](#notes)

## <a name="description"></a>Description

<!--- transclude::api/$name;format="Camel"$Service.scala::[override final def descriptor = {] cjqnn2ydl00002iyayttq5w4j -->

```scala
  override final def descriptor = {
    import Service._
    // @formatter:off
    named("$name;format="norm"$").withCalls(
      // CRUDy Bulk Data Administration
      restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-creation",     bulkCreate$name;format="Camel"$ _),
      restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-replacement",  bulkReplace$name;format="Camel"$ _),
      restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-mutation",     bulkMutate$name;format="Camel"$ _),
      restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-deactivation", bulkDeactivate$name;format="Camel"$ _),
      restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-reactivation", bulkReactivate$name;format="Camel"$ _),
      restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-excision",     bulkExcise$name;format="Camel"$ _),
      // CRUDy REST
      restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$",     post$name;format="Camel"$1 _),
      restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/:id", post$name;format="Camel"$2 _),
      restCall(Method.PUT,    "/api/$plural_name;format="lower,hyphen"$/:id", put$name;format="Camel"$ _),
      restCall(Method.PATCH,  "/api/$plural_name;format="lower,hyphen"$/:id", patch$name;format="Camel"$ _),
      restCall(Method.DELETE, "/api/$plural_name;format="lower,hyphen"$/:id", delete$name;format="Camel"$ _),
      restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$/:id", get$name;format="Camel"$ _),
      restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$",     getAll$plural_name;format="Camel"$ _),
      // CRUDy DDDified REST without a proper ubiquitious language
      // Create
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/creation",                                create$name;format="Camel"$1 _),
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/creation",                            create$name;format="Camel"$2 _),
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/creation/:creationId",                    create$name;format="Camel"$3 _),
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/creation/:creationId",                create$name;format="Camel"$4 _),
      restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/creation/:creationId",                getCreation$name;format="Camel"$ _),
      pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/creation/:creationId/stream",         streamCreation$name;format="Camel"$ _),
      // Read
      // Update
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/replacement",                         replace$name;format="Camel"$1 _),
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/replacement/:replacementId",          replace$name;format="Camel"$2 _),
      restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/replacement/:replacementId",          getReplacement$name;format="Camel"$ _),
      pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/replacement/:replacementId/stream",   streamReplacement$name;format="Camel"$ _),
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/mutation",                            mutate$name;format="Camel"$1 _),
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/mutation/:mutationId",                mutate$name;format="Camel"$2 _),
      restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/mutation/:mutationId",                getMutation$name;format="Camel"$ _),
      pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/mutation/:mutationId/stream",         streamMutation$name;format="Camel"$ _),
      // Delete
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/deactivation",                        deactivate$name;format="Camel"$1 _),
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/deactivation/:deactivationId",        deactivate$name;format="Camel"$2 _),
      restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/deactivation/:deactivationId",        getDeactivation$name;format="Camel"$ _),
      pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/deactivation/:deactivationId/stream", streamDeactivation$name;format="Camel"$ _),
      // Undelete
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/reactivation",                        reactivate$name;format="Camel"$1 _),
      restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/reactivation/:reactivationId",        reactivate$name;format="Camel"$2 _),
      restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/reactivation/:reactivationId",        getReactivation$name;format="Camel"$ _),
      pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/reactivation/:reactivationId/stream", streamReactivation$name;format="Camel"$ _),
      // DDDified REST using the bounded context's ubiquitious language
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/description-enhancement/:enhancementId", enhanceDescription$name;format="Camel"$ _),
//      pathCall("/api/ff $plural_name;format="lower,hyphen"$/stream", stream$plural_name;format="Camel"$ _),
    )
      .withAutoAcl(true)
      .withExceptionSerializer(new DefaultExceptionSerializer(Environment.simple(mode = Mode.Prod)))
      .withTopics(
        topic("$name;format="camel"$-$name;format="Camel"$MessageBrokerEvent", this.$name;format="camel"$MessageBrokerEvents)
      )
    // @formatter:on
  }
```

<!--- transclude cjqnn2ydl00002iyayttq5w4j -->
NOTE: For naming resources in a domain driven design (DDD) manner, focus on domain events not low-level create, read, update, and delete (CRUD) operations.

From [Roy Fielding's dissertation](https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm#sec_5_2_1_1):
> The key abstraction of information in REST is a resource. Any information that can be named can be a resource: a document or image, a temporal service (e.g. "today's weather in Los Angeles"), a collection of other resources, a non-virtual object (e.g. a person), and so on. In other words, any concept that might be the target of an author's hypertext reference must fit within the definition of a resource. A resource is a conceptual mapping to a set of entities, not the entity that corresponds to the mapping at any particular point in time.
From [REST API Design - Resource Modeling](https://www.thoughtworks.com/insights/blog/rest-api-design-resource-modeling):
> The way to escape low-level CRUD is to create business operation or business process resources, or what we can call as "intent" resources that express a business/domain level "state of wanting something" or "state of the process towards the end result". But to do this you need to ensure you identify the true owners of all your state. In a world of four-verb (AtomPub-style) CRUD, it's as if you allow random external parties to mess around with your resource state, through PUT and DELETE, as if the service were just a low-level database. PUT puts too much internal domain knowledge into the client. The client shouldn't be manipulating internal representation; it should be a source of user intent.
> HTTP verb PUT can be used for idempotent resource updates (or resource creations in some cases) by the API consumer. However, use of PUT for complex state transitions can lead to synchronous cruddy CRUD. It also usually throws away a lot of information that was available at the time the update was triggered - what was the real business domain event that triggered this update? With “[REST without PUT](https://www.thoughtworks.com/radar/techniques/rest-without-put)” technique, the idea is that consumers are forced to post new 'nounified' request resources.
From HTTP/1.1 Spec:
> The POST method is used to request that the origin server accept the entity enclosed in the request as a new subordinate of the resource identified by the Request-URI in the Request-Line.
From [DDD & REST - Domain-Driven APIs for the web](https://speakerdeck.com/olivergierke/ddd-and-rest-domain-driven-apis-for-the-web?slide=42)
> Prefer explicit state transitions over poking at your resources using PATCH.
> |-----------|----------|
> | Aggregate Root / Repository | Collection / Item Resources |
> | Relations | Links |
> | IDs | URIs |
> | @Version | ETags |
> | Last Modified Property | Last Modified Header |
The algebraic data type for $name$ is defined as:

<!--- transclude::api/$name;format="Camel"$Service.scala::[$name$ algebraic data type {] cjqnn2yob00012iyasowb9vtl -->

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
// }
```

<!--- transclude cjqnn2yob00012iyasowb9vtl -->

With regular expression validation matchers:

<!--- transclude::api/$name;format="Camel"$Service.scala::[object Matchers {] cjqnn2yyp00022iyaoxbsvs0q -->

```scala
object Matchers {
  val Email =
    """^[a-zA-Z0-9\.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"""
  val Id = """^[a-zA-Z0-9\-\.\_\~]{1,64}\$"""
  val Name = """^[a-zA-Z0-9\-\.\_\~]{1,128}\$"""
  val Description = """^.{1,2048}\$"""
  val Motivation = """^.{1,2048}\$"""
  val Op = """^add|remove|replace|move|copy|test\$"""
}
```

<!--- transclude cjqnn2yyp00022iyaoxbsvs0q -->

And supporting algebraic data types:

<!--- transclude::api/$name;format="Camel"$Service.scala::[Supporting algebraic data types {] cjqnn2z9h00032iyap5q87pwr -->

```scala
// Supporting algebraic data types {
case class Identity(
  identifier: String,
  revision: Option[Int],    // a monotonically increasing counter of changes
  hash: Option[String])
object Identity {
  implicit val format: Format[Identity] = Jsonx.formatCaseClass
  val identityValidator: Validator[Identity] =
    validator[Identity] { identity =>
      identity.identifier is notEmpty
      identity.identifier should matchRegexFully(Matchers.Id)
      // need Option[Int]
      //identity.revision should be >= 0
    }
}
case class HypertextApplicationLanguage(
  halLinks: Seq[HalLink]
  )
object HypertextApplicationLanguage {
  implicit val format: Format[HypertextApplicationLanguage] = Jsonx.formatCaseClass
}
case class HalLink(
  rel: String,
  href: String,
  deprecation: Option[String] = None,
  name: Option[String] = None,
  profile: Option[String] = None,
  title: Option[String] = None,
  hreflang: Option[String] = None,
  `type`: Option[String] = None,
  templated: Boolean = false) {
  def withDeprecation(url: String) = this.copy(deprecation = Some(url))
  def withName(name: String) = this.copy(name = Some(name))
  def withProfile(profile: String) = this.copy(profile = Some(profile))
  def withTitle(title: String) = this.copy(title = Some(title))
  def withHreflang(lang: String) = this.copy(hreflang = Some(lang))
  def withType(mediaType: String) = this.copy(`type` = Some(mediaType))
}
object HalLink {
  implicit val format: Format[HalLink] = Jsonx.formatCaseClass
}
case class Mutation(
  op: String,
  path: String,
  value: Option[String]
  )
object Mutation {
  implicit val format: Format[Mutation] = Jsonx.formatCaseClass
  val mutationValidator: Validator[Mutation] =
    validator[Mutation] { mutation =>
      mutation.op is notEmpty
      mutation.path is notEmpty
      mutation.op should matchRegexFully(Matchers.Op)
    }
}
// }
```

<!--- transclude cjqnn2z9h00032iyap5q87pwr -->

The REST resource for $name$ is defined as:

<!--- transclude::api/$name;format="Camel"$Service.scala::[case class $name;format="Camel"$Resource(] cjqnn2zk700042iyavwhtlyap -->

```scala
case class $name;format="Camel"$Resource(
  $name;format="camel"$: $name;format="Camel"$
)
```

<!--- transclude cjqnn2zk700042iyavwhtlyap -->

The DDD aggregate for $name$ is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[case class $name;format="Camel"$Aggregate(] cjqnn2zut00052iyadv72dtj5 -->

```scala
case class $name;format="Camel"$Aggregate(
  $name;format="camel"$Identity: Identity,
  $name;format="camel"$Resource: $name;format="Camel"$Resource
)
```

<!--- transclude cjqnn2zut00052iyadv72dtj5 -->

The state for $name$ is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[override type State = .*] cjqnn305600062iyaa5fz0oh3 -->

```scala
  override type State = Option[$name;format="Camel"$State]
```

<!--- transclude cjqnn305600062iyaa5fz0oh3 -->

And uses the following $name;format="Camel"$State algebraic data type:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[case class $name;format="Camel"$State(] cjqnn30g600072iya1y9yjgiu -->

```scala
case class $name;format="Camel"$State(
  $name;format="camel"$Aggregate: $name;format="Camel"$Aggregate,
  status: $name;format="Camel"$Status.Status = $name;format="Camel"$Status.NONEXISTENT
)
```

<!--- transclude cjqnn30g600072iya1y9yjgiu -->

The initial state for all DDD aggregates is:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[override def initialState: .*] cjqnn30qt00082iya2vcfqu6s -->

```scala
  override def initialState: Option[$name;format="Camel"$State] = None
```

<!--- transclude cjqnn30qt00082iya2vcfqu6s -->

The possible statuses for the $name$ aggregate are defined to be:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[object $name;format="Camel"$Status extends Enumeration {] cjqnn310o00092iyay9y5wunu -->

```scala
object $name;format="Camel"$Status extends Enumeration {
  val NONEXISTENT, ACTIVE, ARCHIVED, UNKNOWN = Value
  type Status = Value
  implicit val format: Format[Value] = enumFormat(this)
//  implicit val pathParamSerializer: PathParamSerializer[Status] =
//    PathParamSerializer.required("$name;format="camel"$Status")(withName)(_.toString)
}
```

<!--- transclude cjqnn310o00092iyay9y5wunu -->

The finite state machine (FSM) for the DDD aggregate is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[override def behavior: Behavior = {] cjqnn31az000a2iyahafrcvnm -->

```scala
  override def behavior: Behavior = {
    case None => nonexistent$name;format="Camel"$
    case Some(state) if state.status == $name;format="Camel"$Status.ACTIVE => active$name;format="Camel"$
    case Some(state) if state.status == $name;format="Camel"$Status.ARCHIVED => archived$name;format="Camel"$
    case Some(state) => unknown$name;format="Camel"$
  }
```

<!--- transclude cjqnn31az000a2iyahafrcvnm -->

The persistent entity for $name$ is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[final class $name;format="Camel"$Entity extends PersistentEntity {] cjqnn31lm000b2iya3a9t563z -->

```scala
final class $name;format="Camel"$Entity extends PersistentEntity {
  //private val published$name;format="Camel"$CreatedEvent = pubSubRegistry.refFor(TopicId[$name;format="Camel"$CreatedEvent])
  override type Command = $name;format="Camel"$Command[_]
  override type Event = $name;format="Camel"$Event
  override type State = Option[$name;format="Camel"$State]
  type OnCommandHandler[M] = PartialFunction[(Command, CommandContext[M], State), Persist]
  type ReadOnlyHandler[M] = PartialFunction[(Command, ReadOnlyCommandContext[M], State), Unit]
  override def initialState: Option[$name;format="Camel"$State] = None
  // Finite State Machine (FSM)
  override def behavior: Behavior = {
    case None => nonexistent$name;format="Camel"$
    case Some(state) if state.status == $name;format="Camel"$Status.ACTIVE => active$name;format="Camel"$
    case Some(state) if state.status == $name;format="Camel"$Status.ARCHIVED => archived$name;format="Camel"$
    case Some(state) => unknown$name;format="Camel"$
  }
  private val nonexistent$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { create$name;format="Camel"$Command }
        .onCommand[Replace$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { reply$name;format="Camel"$DoesNotExist }
        .onEvent {
          case ($name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate), state) => $name;format="Camel"$State(Some($name;format="camel"$Aggregate), $name;format="Camel"$Status.ACTIVE, 1)
          case (_, state) => state
        }
    }
  }
  private val active$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
        .onCommand[Replace$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replace$name;format="Camel"$Command }
        .onEvent {
          case ($name;format="Camel"$ReplacedEvent($name;format="camel"$Id, replacement$name;format="Camel"$Resource, motivation), state) =>
            $name;format="Camel"$State(Some($name;format="Camel"$Aggregate($name;format="camel"$Id, replacement$name;format="Camel"$Resource)), $name;format="Camel"$Status.ACTIVE, 1)
          case (_, state) => state
        }
    }
  }
  private val archived$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
        .onEvent {
          case (_, state) => state
        }
    }
  }
  private val unknown$name;format="Camel"$ = {
    get$name;format="Camel"$Action orElse {
      Actions()
        .onCommand[Create$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
        .onCommand[Replace$name;format="Camel"$Command, Either[ServiceError, $name;format="Camel"$Aggregate]] { replyConflict }
    }
  }
  private def get$name;format="Camel"$Action = Actions()
    .onReadOnlyCommand[Get$name;format="Camel"$Query.type, $name;format="Camel"$State] {
      case (Get$name;format="Camel"$Query, ctx, state) => ctx.reply(state)
    }
  private def create$name;format="Camel"$Command: OnCommandHandler[Either[ServiceError, $name;format="Camel"$Aggregate]] = {
    case (Create$name;format="Camel"$Command($name;format="camel"$Aggregate), ctx, state) =>
      ctx.thenPersist($name;format="Camel"$CreatedEvent($name;format="camel"$Aggregate)) { evt =>
        ctx.reply(Right($name;format="camel"$Aggregate))
      }
  }
  private def replace$name;format="Camel"$Command: OnCommandHandler[Either[ServiceError, $name;format="Camel"$Aggregate]] = {
    case (Replace$name;format="Camel"$Command(replace$name;format="Camel"$Request), ctx, state) =>
      ctx.thenPersist($name;format="Camel"$ReplacedEvent(replace$name;format="Camel"$Request)) { evt =>
        ctx.reply(Right($name;format="Camel"$Aggregate("3",replace$name;format="Camel"$Request.replacement$name;format="Camel"$Resource)))
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
  private def reply$name;format="Camel"$DoesNotExist[R]: OnCommandHandler[Either[ServiceError, R]] = {
    case (_, ctx, _) =>
      ctx.reply(Left($name;format="Camel"$DoesNotExist))
      ctx.done
  }
}
```

<!--- transclude cjqnn31lm000b2iya3a9t563z -->

For CRUDy operations the following subordinate, nounified, resources are created:
*   Creation
*   Replacement
*   Mutation: [JSON Patch](http://jsonpatch.com/)
*   Deactivation
*   Reactivation

Creation
--------
A Creation request takes a desired $name;format="Camel"$ algebraic data type and responds with the created $name;format="Camel"$Resource plus the supporing algebraic data types of Identity and HypertextApplicationLanguage. If the $name$ resource is not created the service responses with an ErrorResponse. The following REST calls can be used. Identifiers are optional. If specified all identifiers must adhere to the Matcher for Id. Otherwise, the service will create and use a collision resistant unique identifier.

<!--- transclude::api/$name;format="Camel"$Service.scala::[$name$ Creation Calls {] cjqnn31w9000c2iyajicry7j6 -->

```scala
// $name$ Creation Calls {
  /**
    * Rest api allowing an authenticated user to create a "$name$" aggregate.
    *
    * @param  $name;format="camel"$Id  Optional unique identifier of the "$name$"
    *         creationId    Optional unique identifier of the creation subordinate resource
    *
    * @return HTTP 201 Created               if the "$name$" was created successfully
    *         HTTP 202 Accepted              if the request has been accepted, but the processing is not complete
    *         HTTP 400 Bad Request           if domain validation of the [[Create$name;format="Camel"$Request]] failed
    *         HTTP 401 Unauthorized          if JSON Web Token is missing
    *         HTTP 403 Forbidden             if authorization failure
    *         HTTP 404 Not Found             if requested resource doesn't exist, or so as to not reveal a 401 or 403
    *         HTTP 409 Conflict              if the "$name$" already exists with the same unique identity
    *         HTTP 413 Payload Too Large     if request size exceeds a defined limit
    *         HTTP 422 Unprocessable Entity  if the aggregate is not in the proper state to perform this action.
    *
    * REST POST endpoints:
    *   /api/$plural_name;format="lower,hyphen"$
    *   /api/$plural_name;format="lower,hyphen"$/:id
    *   /api/$plural_name;format="lower,hyphen"$/creation
    *   /api/$plural_name;format="lower,hyphen"$/:id/creation
    *   /api/$plural_name;format="lower,hyphen"$/creation/:creationId
    *   /api/$plural_name;format="lower,hyphen"$/:id/creation/:creationId
    *
    * Examples:
    * CT="Content-Type: application/json"
    * DATA='{"$name;format="camel"$": {"name": "test", "description": "test description"}}'
    * curl -H \$CT -X POST -d \$DATA http://localhost:9000/api/$plural_name;format="lower,hyphen"$
    */
  def post$name;format="Camel"$1:                                             ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  def post$name;format="Camel"$2($name;format="camel"$Id: String):                       ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  def create$name;format="Camel"$1:                                           ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  def create$name;format="Camel"$2($name;format="camel"$Id: String):                     ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  def create$name;format="Camel"$3(creationId: String):                       ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  def create$name;format="Camel"$4($name;format="camel"$Id: String, creationId: String): ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  // Retrieve status of creation request
  def getCreation$name;format="Camel"$($name;format="camel"$Id: String, creationId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Creation$name;format="Camel"$Response]]
  def streamCreation$name;format="Camel"$($name;format="camel"$Id: String, creationId: String): ServiceCall[NotUsed, Source[Creation$name;format="Camel"$Response, NotUsed]]
// }
```

<!--- transclude cjqnn31w9000c2iyajicry7j6 -->

The Matcher for identifiers is defined to be:

<!--- transclude::api/$name;format="Camel"$Service.scala::[val Id = .*] cjqnn327d000d2iya4r7l3hix -->

```scala
  val Id = """^[a-zA-Z0-9\-\.\_\~]{1,64}\$"""
```

<!--- transclude cjqnn327d000d2iya4r7l3hix -->

The create $name$ request is defined as:

<!--- transclude::api/$name;format="Camel"$Service.scala::[Create $name$ Request payload {] cjqnn32it000e2iya6ldq5fka -->

```scala
// Create $name$ Request payload {
//type Create$name;format="Camel"$Request = String
//implicit val create$name;format="Camel"$RequestValidator
//    : Validator[Create$name;format="Camel"$Request] { r =>
//    r has size > 0
//    r has size <= maxRequestSize
//    }
case class ValidCreate$name;format="Camel"$Request(
    $name;format="camel"$: $name;format="Camel"$
) {}
case object ValidCreate$name;format="Camel"$Request {
  implicit val format: Format[ValidCreate$name;format="Camel"$Request] = Jsonx.formatCaseClass
  implicit val validCreate$name;format="Camel"$RequestValidator
    : Validator[ValidCreate$name;format="Camel"$Request] =
    validator[ValidCreate$name;format="Camel"$Request] { create$name;format="Camel"$Request =>
      create$name;format="Camel"$Request.$name;format="camel"$ is valid($name;format="Camel"$.$name;format="camel"$Validator)
    }
}
// }
```

<!--- transclude cjqnn32it000e2iya6ldq5fka -->

And the create $name$ response is defined as:

<!--- transclude::api/$name;format="Camel"$Service.scala::[case class Create$name;format="Camel"$Response(] cjqnn32ts000f2iya8p8rgfn7 -->

```scala
case class Create$name;format="Camel"$Response(
    $name;format="camel"$Id: Identity,
    $name;format="camel"$: $name;format="Camel"$,
    $name;format="camel"$Hal: Option[HypertextApplicationLanguage]
)
```

<!--- transclude cjqnn32ts000f2iya8p8rgfn7 -->

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[$name$ Creation Calls {] cjqnn334n000g2iyaab6m4ou5 -->

```scala
// $name$ Creation Calls {
  override def post$name;format="Camel"$
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        val $name;format="camel"$Id = Cuid.createCuid()
        val creationId = Cuid.createCuid()
        logger.info(
          s"Posting '$name$' with identifier \$$name;format="camel"$Id...")
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }
  override def create$name;format="Camel"$1
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        val $name;format="camel"$Id = Cuid.createCuid()
        val creationId = Cuid.createCuid()
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }
  override def create$name;format="Camel"$2($name;format="camel"$Id: String)
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        val creationId = Cuid.createCuid()
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }
  override def create$name;format="Camel"$3(creationId: String)
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        val $name;format="camel"$Id = Cuid.createCuid()
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }
  override def create$name;format="Camel"$4($name;format="camel"$Id: String, creationId: String)
    : ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { (requestHeader, create$name;format="Camel"$Request) =>
        this
          .create$name;format="Camel"$Internal($name;format="camel"$Id, creationId)
          .handleRequestHeader(requestHeader => requestHeader)
          .invokeWithHeaders(requestHeader, create$name;format="Camel"$Request).map {
            case (responseHeader, response) => (Ok, Right(response))
          }
      }
    }
  def create$name;format="Camel"$Internal($name;format="camel"$Id: String, creationId: String)
    : ServerServiceCall[Create$name;format="Camel"$Request, Create$name;format="Camel"$Response] =
    authenticated { (tokenContent, _) =>
      ServerServiceCall { create$name;format="Camel"$Request =>
        val username = tokenContent.username
        logger.info(s"User \$username is creating a $name$ ")
        logger.info(
          s"Creating '$name$' with input \$create$name;format="Camel"$Request...")
        val validationResult = validate(create$name;format="Camel"$Request)
        validationResult match {
          case failure: Failure =>
            throw new TransportException(TransportErrorCode.BadRequest,
                                         "request validation failure")
          case _ =>
        }
        val $name;format="camel"$Aggregate =
          $name;format="Camel"$Aggregate($name;format="camel"$Id, $name;format="Camel"$Resource(create$name;format="Camel"$Request.$name;format="camel"$))
        val $name;format="camel"$Resource =
          $name;format="Camel"$Resource(create$name;format="Camel"$Request.$name;format="camel"$)
        val $name;format="camel"$EntityRef =
          registry.refFor[$name;format="Camel"$Entity]($name;format="camel"$Id.toString)
        logger.info(s"Publishing event \$$name;format="camel"$Aggregate")
        val topic = pubSubRegistry.refFor(TopicId[$name;format="Camel"$Resource])
        topic.publish($name;format="camel"$Resource)
        $name;format="camel"$EntityRef
          .ask(Create$name;format="Camel"$Command($name;format="camel"$Aggregate))
          .map { _ =>
            mapToCreate$name;format="Camel"$Response($name;format="camel"$Id, $name;format="camel"$Resource)
          }
      }
    }
  private def mapToCreate$name;format="Camel"$Response(
      $name;format="camel"$Id: String,
      $name;format="camel"$Resource: $name;format="Camel"$Resource): Create$name;format="Camel"$Response = {
    Create$name;format="Camel"$Response($name;format="camel"$Id,
                             $name;format="camel"$Resource.$name;format="camel"$)
  }
  private def mapToCreate$name;format="Camel"$Response(
      $name;format="camel"$State: $name;format="Camel"$State): Create$name;format="Camel"$Response = {
    Create$name;format="Camel"$Response($name;format="camel"$State.$name;format="camel"$Aggregate map { _.$name;format="camel"$Id } getOrElse "No identifier",
                             $name;format="camel"$State.$name;format="camel"$Aggregate map { _.$name;format="camel"$Resource.$name;format="camel"$} getOrElse $name;format="Camel"$("No name", Some("No description")))
  }
// }
```

<!--- transclude cjqnn334n000g2iyaab6m4ou5 -->

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[The create $name$ command {] cjqnn33f8000h2iya90ekcdlv -->

```scala
// The create $name$ command {
case class Create$name;format="Camel"$Command(
  $name;format="camel"$Aggregate: $name;format="Camel"$Aggregate)
    extends $name;format="Camel"$Command[Either[ServiceError, Create$name;format="Camel"$Reply]]
object Create$name;format="Camel"$Command {
  implicit val format: Format[Create$name;format="Camel"$Command] = Json.format
}
// }
```

<!--- transclude cjqnn33f8000h2iya90ekcdlv -->

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[The create $name$ reply {] cjqnn33pv000i2iya4hy7he4k -->

```scala
// The create $name$ reply {
case class Create$name;format="Camel"$Reply(
  $name;format="camel"$Aggregate: $name;format="Camel"$Aggregate)
object Create$name;format="Camel"$Reply {
  implicit val format: Format[Create$name;format="Camel"$Reply] = Json.format
}
// }
```

<!--- transclude cjqnn33pv000i2iya4hy7he4k -->

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[private def create$name;format="Camel"$Command: OnCommandHandler[Either[ServiceError, $name;format="Camel"$Aggregate]] = {] cjqnn3415000j2iyap03suly6 -->

```scala
```

<!--- transclude cjqnn3415000j2iyap03suly6 -->

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[private def create$name;format="Camel"$Command: OnCommandHandler[Either[ServiceError, $name;format="Camel"$Aggregate]] = {] cjqnn34du000k2iyaa8yj9079 -->

```scala
```

<!--- transclude cjqnn34du000k2iyaa8yj9079 -->

Replacement
-----------
A Replacement request takes the new desired $name;format="Camel"$ algebraic data type and responds with the replaced $name;format="Camel"$Resource plus the supporing algebraic data types of Identity and HypertextApplicationLanguage. If the $name$ resource is not replaced the service responses with an ErrorResponse. The following REST calls can be used. The $name$ identifier is required, but the replacementId is optional. If specified all identifiers must adhere to the Matcher for Id.

<!--- transclude::api/$name;format="Camel"$Service.scala::[$name$ Replacement Calls {] cjqnn34nu000l2iyasm6ucn7y -->

```scala
// $name$ Replacement Calls {
  /**
    * Rest api allowing an authenticated user to replace a "$name$".
    *
    * @param  $name;format="camel"$Id   The unique identifier of the "$name$"
    *         replacementId  Optional unique identifier of the replacement subordinate resource
    *
    * @return HTTP 200 OK                    if the "$name$" was replaced successfully
    *         HTTP 202 Accepted              if the request has been accepted, but the processing is not complete
    *         HTTP 400 Bad Request           if domain validation of the [[Replace$name;format="Camel"$Request]] failed
    *         HTTP 401 Unauthorized          if JSON Web Token is missing
    *         HTTP 403 Forbidden             if authorization failure (use 404 if authz failure shouldn't be revealed)
    *         HTTP 404 Not Found             if requested resource doesn't exist, or so as to not reveal a 401 or 403
    *         HTTP 413 Payload Too Large     if request size exceeds a defined limit
    *         HTTP 422 Unprocessable Entity  if the aggregate is not in the proper state to perform this action
    *
    * REST PUT endpoint:
    *   /api/$plural_name;format="lower,hyphen"$/:id
    * REST POST endpoints:
    *   /api/$plural_name;format="lower,hyphen"$/:id/mutation
    *   /api/$plural_name;format="lower,hyphen"$/:id/mutation/:mutationId
    *
    * Example:
    * CT="Content-Type: application/json"
    * DATA='{"$name;format="camel"$": {"name": "test", "description": "different description"}}'
    * curl -H \$CT -X PUT -d \$DATA http://localhost:9000/api/$plural_name;format="lower,hyphen"$/cjq5au9sr000caqyayo9uktss
    */
  def put$name;format="Camel"$($name;format="camel"$Id: String):                             ServiceCall[Replace$name;format="Camel"$Request, Either[ErrorResponse, Replace$name;format="Camel"$Response]]
  def replace$name;format="Camel"$1($name;format="camel"$Id: String):                        ServiceCall[Replace$name;format="Camel"$Request, Either[ErrorResponse, Replace$name;format="Camel"$Response]]
  def replace$name;format="Camel"$2($name;format="camel"$Id: String, replacementId: String): ServiceCall[Replace$name;format="Camel"$Request, Either[ErrorResponse, Replace$name;format="Camel"$Response]]
  // Retrieve status of replacement request
  def getReplacement$name;format="Camel"$($name;format="camel"$Id: String, replacementId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Replacement$name;format="Camel"$Response]]
  def streamReplacement$name;format="Camel"$($name;format="camel"$Id: String, replacementId: String): ServiceCall[NotUsed, Source[Replacement$name;format="Camel"$Response, NotUsed]]
// }
```

<!--- transclude cjqnn34nu000l2iyasm6ucn7y -->

The replace $name$ request is defined as:

<!--- transclude::api/$name;format="Camel"$Service.scala::[case class Replace$name;format="Camel"$Request(] cjqnn34y0000m2iya0nihiwwv -->

```scala
case class Replace$name;format="Camel"$Request(
    replacement$name;format="Camel"$: $name;format="Camel"$,
    motivation: Option[String]
)
```

<!--- transclude cjqnn34y0000m2iya0nihiwwv -->

And the replace $name$ response is defined as:

<!--- transclude::api/$name;format="Camel"$Service.scala::[case class Replace$name;format="Camel"$Response(] cjqnn3594000n2iya7gm27hyw -->

```scala
case class Replace$name;format="Camel"$Response(
    $name;format="camel"$Id: Identity,
    $name;format="camel"$: $name;format="Camel"$,
    $name;format="camel"$Hal: Option[HypertextApplicationLanguage]
)
```

<!--- transclude cjqnn3594000n2iya7gm27hyw -->

Mutation
--------

<!--- transclude::api/$name;format="Camel"$Service.scala::[$name$ Mutation Calls {] cjqnn35ke000o2iyai8nwyybp -->

```scala
// $name$ Mutation Calls {
  /**
    * Rest api allowing an authenticated user to mutate a "$name$".
    *
    * @param  $name;format="camel"$Id  The unique identifier of the "$name$"
    *         mutationId    Optional unique identifier of the mutation subordinate resource
    *
    * @return HTTP 200 OK                    if the "$name$" was mutated successfully
    *         HTTP 202 Accepted              if the request has been accepted, but the processing is not complete
    *         HTTP 400 Bad Request           if domain validation of the [[Mutate$name;format="Camel"$Request]] failed
    *         HTTP 401 Unauthorized          if JSON Web Token is missing
    *         HTTP 403 Forbidden             if authorization failure (use 404 if authz failure shouldn't be revealed)
    *         HTTP 404 Not Found             if requested resource doesn't exist, or so as to not reveal a 401 or 403
    *         HTTP 413 Payload Too Large     if request size exceeds a defined limit
    *         HTTP 422 Unprocessable Entity  if the aggregate is not in the proper state to perform this action
    *
    * REST PATCH endpoint:
    *   /api/$plural_name;format="lower,hyphen"$/:id
    * REST POST endpoints:
    *   /api/$plural_name;format="lower,hyphen"$/:id/replacement
    *   /api/$plural_name;format="lower,hyphen"$/:id/replacement/:replacementId
    *
    * Example:
    * CT="Content-Type: application/json"
    * DATA='[{"op": "replace", "path": "/name", "value": "new name"}]'
    * curl -H \$CT -X PATCH -d \$DATA http://localhost:9000/api/$plural_name;format="lower,hyphen"$/cjq5au9sr000caqyayo9uktss
    */
  def patch$name;format="Camel"$($name;format="camel"$Id: String):                       ServiceCall[Mutate$name;format="Camel"$Request, Either[ErrorResponse, Mutate$name;format="Camel"$Response]]
  def mutate$name;format="Camel"$1($name;format="camel"$Id: String):                     ServiceCall[Mutate$name;format="Camel"$Request, Either[ErrorResponse, Mutate$name;format="Camel"$Response]]
  def mutate$name;format="Camel"$2($name;format="camel"$Id: String, mutationId: String): ServiceCall[Mutate$name;format="Camel"$Request, Either[ErrorResponse, Mutate$name;format="Camel"$Response]]
  // Retrieve status of mutation request
  def getMutation$name;format="Camel"$($name;format="camel"$Id: String, mutationId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Mutation$name;format="Camel"$Response]]
  def streamMutation$name;format="Camel"$($name;format="camel"$Id: String, mutationId: String): ServiceCall[NotUsed, Source[Mutation$name;format="Camel"$Response, NotUsed]]
// }
```

<!--- transclude cjqnn35ke000o2iyai8nwyybp -->

Deactivation
------------

<!--- transclude::api/$name;format="Camel"$Service.scala::[$name$ Deactivation Calls {] cjqnn35vh000p2iyalkhte7xz -->

```scala
// $name$ Deactivation Calls {
  /**
    * Rest api allowing an authenticated user to deactivate a "$name$".
    *
    * @param  $name;format="camel"$Id    The unique identifier of the "$name$"
    *         deactivationId  Optional unique identifier of the deactivation subordinate resource
    *
    * @return HTTP 200 OK                    if the "$name$" was deactivated successfully
    *         HTTP 202 Accepted              if the request has been accepted, but the processing is not complete
    *         HTTP 400 Bad Request           if domain validation of the [[Deactivate$name;format="Camel"$Request]] failed
    *         HTTP 401 Unauthorized          if JSON Web Token is missing
    *         HTTP 403 Forbidden             if authorization failure (use 404 if authz failure shouldn't be revealed)
    *         HTTP 404 Not Found             if requested resource doesn't exist, or so as to not reveal a 401 or 403
    *         HTTP 413 Payload Too Large     if request size exceeds a defined limit
    *         HTTP 422 Unprocessable Entity  if the aggregate is not in the proper state to perform this action
    *
    * REST DELETE endpoint:
    *   /api/$plural_name;format="lower,hyphen"$/:id
    * REST POST endpoints:
    *   /api/$plural_name;format="lower,hyphen"$/:id/deactivation
    *   /api/$plural_name;format="lower,hyphen"$/:id/deactivation/:deactivationId
    *
    * Example:
    * CT="Content-Type: application/json"
    * curl -H \$CT -X DELETE http://localhost:9000/api/$plural_name;format="lower,hyphen"$/cjq5au9sr000caqyayo9uktss
    */
  def patch$name;format="Camel"$($name;format="camel"$Id: String):                               ServiceCall[Deactivate$name;format="Camel"$Request, Either[ErrorResponse, Deactivate$name;format="Camel"$Response]]
  def deactivate$name;format="Camel"$1($name;format="camel"$Id: String):                         ServiceCall[Deactivate$name;format="Camel"$Request, Either[ErrorResponse, Deactivate$name;format="Camel"$Response]]
  def deactivate$name;format="Camel"$2($name;format="camel"$Id: String, deactivationId: String): ServiceCall[Deactivate$name;format="Camel"$Request, Either[ErrorResponse, Deactivate$name;format="Camel"$Response]]
  // Retrieve status of deactivation request
  def getDeactivation$name;format="Camel"$($name;format="camel"$Id: String, deactivationId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Deactivation$name;format="Camel"$Response]]
  def streamDeactivation$name;format="Camel"$($name;format="camel"$Id: String, deactivationId: String): ServiceCall[NotUsed, Source[Deactivation$name;format="Camel"$Response, NotUsed]]
// }
```

<!--- transclude cjqnn35vh000p2iyalkhte7xz -->

Reactivation
------------

<!--- transclude::api/$name;format="Camel"$Service.scala::[$name$ Reactivation Calls {] cjqnn365w000q2iyaupxakres -->

```scala
// $name$ Reactivation Calls {
  /**
    * Rest api allowing an authenticated user to reactivate a "$name$".
    *
    * @param  $name;format="camel"$Id    The unique identifier of the "$name$"
    *         reactivationId  Optional unique identifier of the reactivation subordinate resource
    *
    * @return HTTP 200 OK                    if the "$name$" was reactivated successfully
    *         HTTP 202 Accepted              if the request has been accepted, but the processing is not complete
    *         HTTP 400 Bad Request           if domain validation of the [[Reactivate$name;format="Camel"$Request]] failed
    *         HTTP 401 Unauthorized          if JSON Web Token is missing
    *         HTTP 403 Forbidden             if authorization failure (use 404 if authz failure shouldn't be revealed)
    *         HTTP 404 Not Found             if requested resource doesn't exist, or so as to not reveal a 401 or 403
    *         HTTP 413 Payload Too Large     if request size exceeds a defined limit
    *         HTTP 422 Unprocessable Entity  if the aggregate is not in the proper state to perform this action
    *
    * REST POST endpoints:
    *   /api/$plural_name;format="lower,hyphen"$/:id/reactivation
    *   /api/$plural_name;format="lower,hyphen"$/:id/reactivation/:reactivationId
    *
    * Example:
    * CT="Content-Type: application/json"
    * curl -H \$CT -X POST http://localhost:9000/api/$plural_name;format="lower,hyphen"$/cjq5au9sr000caqyayo9uktss/reactivation
    */
  def patch$name;format="Camel"$($name;format="camel"$Id: String):                               ServiceCall[Reactivate$name;format="Camel"$Request, Either[ErrorResponse, Reactivate$name;format="Camel"$Response]]
  def reactivate$name;format="Camel"$1($name;format="camel"$Id: String):                         ServiceCall[Reactivate$name;format="Camel"$Request, Either[ErrorResponse, Reactivate$name;format="Camel"$Response]]
  def reactivate$name;format="Camel"$2($name;format="camel"$Id: String, reactivationId: String): ServiceCall[Reactivate$name;format="Camel"$Request, Either[ErrorResponse, Reactivate$name;format="Camel"$Response]]
  // Retrieve status of reactivation request
  def getReactivation$name;format="Camel"$($name;format="camel"$Id: String, reactivationId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Reactivation$name;format="Camel"$Response]]
  def streamReactivation$name;format="Camel"$($name;format="camel"$Id: String, reactivationId: String): ServiceCall[NotUsed, Source[Reactivation$name;format="Camel"$Response, NotUsed]]
// }
```

<!--- transclude cjqnn365w000q2iyaupxakres -->

Read
----

<!--- transclude::api/$name;format="Camel"$Service.scala::[$name$ Get Calls {] cjqnn36ft000r2iya148g8due -->

```scala
// $name$ Get Calls {
  /**
    * Rest api allowing an authenticated user to get a "$name$" with the given surrogate key.
    *
    * @param $name;format="camel"$Id    The unique identifier of the "$name$"
    *
    * @return HTTP 200 OK                    if the "$name$" was retrieved successfully
    *
    * Example:
    * CT="Content-Type: application/json"
    * curl -H \$CT http://localhost:9000/api/$plural_name;format="lower,hyphen"$/cjq5au9sr000caqyayo9uktss
    */
  def get$name;format="Camel"$($name;format="camel"$Id: String): ServiceCall[NotUsed, Either[ErrorResponse, Get$name;format="Camel"$Response]]
  /**
    * Get all "$plural_name$".
    *
    * @return A list of "$name$" resources.
    *
    * Example:
    * curl http://localhost:9000/api/$plural_name;format="lower,hyphen"$
    */
  def getAll$plural_name;format="Camel"$(page: Option[String]): ServiceCall[NotUsed, utils.PagingState[GetAll$plural_name;format="Camel"$Response]]
  def getAll$plural_name;format="Camel"$:                       ServiceCall[NotUsed, GetAll$plural_name;format="Camel"$Response]
// }
```

<!--- transclude cjqnn36ft000r2iya148g8due -->

```bash
-- With Bearer Auth Token
export AT=`./get-auth-token.sh`
sed 's/\r//'  $plural_name;format="lower,hyphen"$.csv | perl -MText::CSV -MJSON::MaybeXS=encode_json -lne '\$c=Text::CSV->new;\$c->parse(\$_);@C=\$c->fields if \$.==1;@F=\$c->fields;@L{@C}=@F;\$J{$name;format="camel"$}=\%L;\$l=encode_json \%J;`curl --show-error --header \"Authorization: Bearer \${'AT'}\" -H \"Content-Type: application/json\" -X POST -d \047\$l\047 http://localhost:9000/api/$plural_name;format="lower,hyphen"$/\$F[0]/create-$name;format="norm"$`unless \$.==1;'
```

For REST calls with DDD/CQRS/ES only use GET and POST
GET for queries
  pagination and expand for large resources
 POST for commands
   "Use POST APIs to create new subordinate resources" [HTTP Methods](https://restfulapi.net/http-methods/)
   A DDD command can be thought of as a subordinate resource to the DDD aggregate entity
   The command "could" have an identity and be queryable, for instance an async req/resp.
   A Saga needs to be implemented in this manner
   Command body should include a unique identifier, can be a span id

Distributed tracing
-------------------

[Zipkin](https://zipkin.io/pages/instrumenting.html)

Annotation

An Annotation is used to record an occurrence in time. There’s a set of core annotations used to define the beginning and end of an RPC request:

cs - Client Send. The client has made the request. This sets the beginning of the span.
sr - Server Receive: The server has received the request and will start processing it. The difference between this and cs will be combination of network latency and clock jitter.
ss - Server Send: The server has completed processing and has sent the request back to the client. The difference between this and sr will be the amount of time it took the server to process the request.
cr - Client Receive: The client has received the response from the server. This sets the end of the span. The RPC is considered complete when this annotation is recorded.
When using message brokers instead of RPCs, the following annotations help clarify the direction of the flow:

ms - Message Send: The producer sends a message to a broker.
mr - Message Receive: A consumer received a message from a broker.
Unlike RPC, messaging spans never share a span ID. For example, each consumer of a message is a different child span of the producing span.

Trace Id

The overall 64 or 128-bit ID of the trace. Every span in a trace shares this ID.

Span Id

The ID for a particular span. This may or may not be the same as the trace id.

Parent Id

This is an optional ID that will only be present on child spans. That is the span without a parent id is considered the root of the trace.

HTTP Tracing

HTTP headers are used to pass along trace information.

The B3 portion of the header is so named for the original name of Zipkin: BigBrotherBird.

Ids are encoded as hex strings:

X-B3-TraceId: 128 or 64 lower-hex encoded bits (required)
X-B3-SpanId: 64 lower-hex encoded bits (required)
X-B3-ParentSpanId: 64 lower-hex encoded bits (absent on root span)
X-B3-Sampled: Boolean (either “1” or “0”, can be absent)
X-B3-Flags: “1” means debug (can be absent)
For more information on B3, please see its specification.

Timestamps are microseconds

[b3-propagation](https://github.com/openzipkin/b3-propagation)

Single Header
A single header named b3 standardized in late 2018 for use in JMS and w3c tracestate. Design and rationale are captured here. Check or update our support page for adoption status.

In simplest terms b3 maps propagation fields into a hyphen delimited string.

b3={TraceId}-{SpanId}-{SamplingState}-{ParentSpanId}, where the last two fields are optional.

For example, the following state encoded in multiple headers:

X-B3-TraceId: 80f198ee56343ba864fe8b2a57d3eff7
X-B3-ParentSpanId: 05e3ac9a4f6e3b90
X-B3-SpanId: e457b5a2e4d86bd1
X-B3-Sampled: 1
Becomes one b3 header, for example:

b3: 80f198ee56343ba864fe8b2a57d3eff7-e457b5a2e4d86bd1-1-05e3ac9a4f6e3b90
[play-zipkin-tracing](https://github.com/bizreach/play-zipkin-tracing)

[OpenTracing for the Lagom Framework](https://github.com/deltaprojects/lagom-opentracing)

libraryDependencies += "com.deltaprojects" % "lagom-opentracing_2.12" % "0.2.3"

JWT
----

*   User gets authenticated and gets a ticket of some type (JWT, Kerberos, ...)
*   User wants to use your Self-Contained System (SCS) and exchanges any token that verifies their identity with a JWT for your SCS that adds the roles they have withing your SCS.
*   The JWT has a

Versioning
-----------

Semantic versioning: [Semantic Versioning 2.0.0](https://semver.org/)
1. MAJOR version when you make incompatible API changes,
2. MINOR version when you add functionality in a backwards-compatible manner, and
3. PATCH version when you make backwards-compatible bug fixes.

RESTful versioning:
1. MAJOR: The resource has changed so much it is considered a different resource, thus it has a different URI. Version 1 of `http://myservice/helloworld/123` becomes  `http://example.com/helloworld/v2/123` in version 2.
2. MINOR: Http Accept header encodes minor version number. Requesting major version 1 and minor version 0 of a resource should have an "Accept" header of "application/vnd.helloworld.hal+json;version=1.0" and use URI http://myservice/helloworld/123. Requesting major version 3 and minor version 2 of a resource should have an "Accept" header of "application/vnd.helloworld.hal+json;version=3.2" and use URI `http://myservice/helloworld/v3/123`. A mismatch between the major version in the header and in the URI will return an error.
3. PATCH: Requests will not specify patch version, but the full semantic version information is avaliable for every request.

Deprecation: A request to a prior version that is still supported will include information on what the latest version is and when the requested version will be decomissioned.

HATEOAS
-------

[How a RESTful API represents resources](https://www.oreilly.com/ideas/how-a-restful-api-represents-resources)
[HAL Primer](https://phlyrestfully.readthedocs.io/en/latest/halprimer.html)
[Getting started with JSON Hyper-Schema](https://blog.apisyouwonthate.com/getting-started-with-json-hyper-schema-184775b91f)
[Getting started with JSON Hyper-Schema: Part 2](https://blog.apisyouwonthate.com/getting-started-with-json-hyper-schema-part-2-ca9d7ffdf6f6)

Hypermedia Application Language (HAL)

```json
{"_links":{"self":{"href":"/"},"curies": [...]
```
```yaml
_embedded:
_links:
  self:
    href:
  first:
  prev:
  next:
  last:
  # DDD commands allowed
  archive:
    href: ""
    targetMediaType: "vnd.helloworld.hal+json;version=3.2"
    targetSchema: { "\$ref": "#"} # point to the root document or {"\$ref": "https://schemas.dashron.com/users"}
    methods: [POST]
```
targetSchema - request body is identical to the target representation
submissionSchema - request body does not match the target representation
headerSchema -
```yaml
    targetSchema:
      id:
        type: String
      name:
        type: String
  # Curies for templating hrefs
  curies: [{
    name:
    href:
    templated: true
  }],
```
```json
{
  "_links": {
    "self": { "href": "/orders" },
    "curies": [{
      "name": "acme",
      "href": "http://docs.acme.com/relations/{rel}",
      "templated": true
    }],
    "acme:widgets": { "href": "/widgets" }
  }
}
```
Need links to stable self that includes transactionClock in identity as well as current.
`http://myservice/helloworld/123/c12341895a3`
[JSON Hyper-Schema: A Vocabulary for Hypermedia Annotation of JSON](https://json-schema.org/latest/json-schema-hypermedia.html)
[JSON Schema](http://json-schema.org/)
JSON Hyper-Schema document
```json
{
    "type": "object",
    "properties": {
        "id": {
            "type": "number"
        },
        "title": {
            "type": "string"
        },
        "urlSlug": {
            "type": "string"
        },
        "post": {
            "type": "string"
        }
    },
    "required": ["id"],
    "base": "http://api.dashron.com/",
    "links": [{
        "rel": "self",
        "href": "posts/{id}",
        "templateRequired": ["id"]
    }]
}
```
Example application that uses schema on the client side: A React component for building Web forms from JSON Schema.
[react-jsonschema-form](https://github.com/mozilla-services/react-jsonschema-form)
[DDD & REST — Domain-Driven APIs for the web](https://speakerdeck.com/olivergierke/ddd-and-rest-domain-driven-apis-for-the-web?slide=42)
ETags
Aggregate Root -> Collection/Item Resource
Relations -> Links
IDs -> URIs
@Version -> ETags
Last Modified Property -> Last Modified Header
According to the HTTP/1.1 Spec:
The POST method is used to request that the origin server accept the entity enclosed in the request as a new subordinate of the resource identified by the Request-URI in the Request-Line
In other words, POST is used to create.
The PUT method requests that the enclosed entity be stored under the supplied Request-URI. If the Request-URI refers to an already existing resource, the enclosed entity SHOULD be considered as a modified version of the one residing on the origin server. If the Request-URI does not point to an existing resource, and that URI is capable of being defined as a new resource by the requesting user agent, the origin server can create the resource with that URI."
That is, PUT is used to create or update.
So, which one should be used to create a resource? Or one needs to support both?
[REST and DDD: incompatible?](http://dontpanic.42.nl/2012/04/rest-and-ddd-incompatible.html)
Representational State Transfer is a software architectural style that defines a set of constraints to be used for creating web services. Web services that conform to the REST architectural style, termed RESTful web services, provide interoperability between computer systems on the Internet. Wikipedia
### License
MIT
### Author Information
| Author                | E-mail                        |
|-----------------------|-------------------------------|
|  |   |