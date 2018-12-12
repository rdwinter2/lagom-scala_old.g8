<!--- to transclude content use the following syntax at the beginning of a line "transclude::file_name.ext::[an optional regular expression]" -->
<!--- Never directly edit transcluded content, always edit the source file -->
$name$
=============

This project has been generated using the rdwinter2/lagom-scala.g8 template.

For instructions on running and testing the project, see https://www.lagomframework.com/get-started-scala.html.

To generate a new project execute the following and supply values for (name, plural_name, organization, version, and package):

```bash
sbt new rdwinter2/lagom-scala.g8
```

After running `git init` or cloning from a repository `cd` into the directory and run `./custom-hooks/run-after-clone.sh`.


REST naming folling https://www.thoughtworks.com/insights/blog/rest-api-design-resource-modeling.

The REST call identifiers for the $name$ project are defined as:
<!--- transclude::api/$name;format="Camel"$Service.scala::[override final def descriptor = {] cjpiqadh10000zwn3jpph16w1 -->

```scala
  override final def descriptor = {
    import Service._
    // @formatter:off
    named("$name;format="norm"$").withCalls(
      restCall(Method.POST,    "/api/$plural_name;format="lower,hyphen"$",                     create$name;format="Camel"$WithSystemGeneratedId _),
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

<!--- transclude cjpiqadh10000zwn3jpph16w1 -->

The algebraic data type for $name$ is defined as:
<!--- transclude::api/$name;format="Camel"$Service.scala::[$name$ algebraic data type {] cjpiqadie0001zwn34dpn1rb4 -->

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
  transactionClock: Option[Int])

object Identity {
  implicit val format: Format[Identity] = Jsonx.formatCaseClass

  val $name;format="camel"$Validator: Validator[Identity] =
    validator[Identity] { identity =>
      identity.identifier is notEmpty
      identity.identifier should matchRegexFully(Matchers.Id)
      // need Option[Int]
      //identity.transactionClock should be >= 0
    }
}
// }
```

<!--- transclude cjpiqadie0001zwn34dpn1rb4 -->

With regular expression validation matchers:
<!--- transclude::api/$name;format="Camel"$Service.scala::[object Matchers {] cjpiqadjl0002zwn3yvup7f2e -->

```scala
object Matchers {
  val Email =
    """^[a-zA-Z0-9\.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"""
  val Id = """^[a-zA-Z0-9\-\.\_\~]{1,64}\$"""
  val Name = """^[a-zA-Z0-9\-\.\_\~]{1,128}\$"""
  val Description = """^.{1,2048}\$"""
}
```

<!--- transclude cjpiqadjl0002zwn3yvup7f2e -->

The REST resource for $name$ is defined as:

<!--- transclude::api/$name;format="Camel"$Service.scala::[case class $name;format="Camel"$Resource(] cjpiqadks0003zwn3zvgrnpky -->

```scala
case class $name;format="Camel"$Resource(
  $name;format="camel"$Id: String,
  $name;format="camel"$: $name;format="Camel"$
)
```

<!--- transclude cjpiqadks0003zwn3zvgrnpky -->

The DDD aggregate for $name$ is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[case class $name;format="Camel"$Aggregate(] cjpiqadma0004zwn3mhbfxcdd -->

```scala
case class $name;format="Camel"$Aggregate(
    $name;format="camel"$Id: String,
    $name;format="camel"$: $name;format="Camel"$
)
```

<!--- transclude cjpiqadma0004zwn3mhbfxcdd -->

The state for $name$ is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[case class $name;format="Camel"$State(] cjpiqado90005zwn3blaquhca -->

```scala
case class $name;format="Camel"$State(
  $name;format="camel"$Aggregate: Option[$name;format="Camel"$Aggregate],
  status: $name;format="Camel"$Status.Status = $name;format="Camel"$Status.NONEXISTENT,
  transactionClock: Int
)
```

<!--- transclude cjpiqado90005zwn3blaquhca -->

The possible statuses for the $name$ aggregate are defined to be:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[object $name;format="Camel"$Status extends Enumeration {] cjpiqadq30006zwn3i4yp6qfy -->

```scala
object $name;format="Camel"$Status extends Enumeration {
  val NONEXISTENT, ACTIVE, ARCHIVED = Value
  type Status = Value

  implicit val format: Format[Value] = enumFormat(this)
//  implicit val pathParamSerializer: PathParamSerializer[Status] =
//    PathParamSerializer.required("$name;format="camel"$Status")(withName)(_.toString)
}
```

<!--- transclude cjpiqadq30006zwn3i4yp6qfy -->

The entity for $name$ is defined as:

<!--- transclude::impl/$name;format="Camel"$ServiceImpl.scala::[final class $name;format="Camel"$Entity extends PersistentEntity {] cjpiqads30007zwn3j3dyxty5 -->

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

<!--- transclude cjpiqads30007zwn3j3dyxty5 -->



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

# Distributed tracing

https://zipkin.io/pages/instrumenting.html

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

https://github.com/openzipkin/b3-propagation

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
https://github.com/bizreach/play-zipkin-tracing

https://github.com/deltaprojects/lagom-opentracing

libraryDependencies += "com.deltaprojects" % "lagom-opentracing_2.12" % "0.2.3"


# JWT

* User gets authenticated and gets a ticket of some type (JWT, Kerberos, ...)
* User wants to use your Self-Contained System (SCS) and exchanges any token that verifies their identity with a JWT for your SCS that adds the roles they have withing your SCS.
* The JWT has a

# Versioning

Semantic versioning: https://semver.org/
1. MAJOR version when you make incompatible API changes,
2. MINOR version when you add functionality in a backwards-compatible manner, and
3. PATCH version when you make backwards-compatible bug fixes.

RESTful versioning:
1. MAJOR: The resource has changed so much it is considered a different resource, thus it has a different URI. Version 1 of http://myservice/helloworld/123 becomes  http://example.com/helloworld/v2/123 in version 2.
2. MINOR: Http Accept header encodes minor version number. Requesting major version 1 and minor version 0 of a resource should have an "Accept" header of "application/vnd.helloworld.hal+json;version=1.0" and use URI http://myservice/helloworld/123. Requesting major version 3 and minor version 2 of a resource should have an "Accept" header of "application/vnd.helloworld.hal+json;version=3.2" and use URI http://myservice/helloworld/v3/123. A mismatch between the major version in the header and in the URI will return an error.
3. PATCH: Requests will not specify patch version, but the full semantic version information is avaliable for every request.

Deprecation: A request to a prior version that is still supported will include information on what the latest version is and when the requested version will be decomissioned.

# HATEOAS

https://www.oreilly.com/ideas/how-a-restful-api-represents-resources
https://phlyrestfully.readthedocs.io/en/latest/halprimer.html
https://blog.apisyouwonthate.com/getting-started-with-json-hyper-schema-184775b91f
https://blog.apisyouwonthate.com/getting-started-with-json-hyper-schema-part-2-ca9d7ffdf6f6

Hypermedia Application Language (HAL)

{"_links":{"self":{"href":"/"},"curies": [...]

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

targetSchema - request body is identical to the target representation
submissionSchema - request body does not match the target representation
headerSchema -

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
https://stackoverflow.com/questions/28154998/can-anyone-provide-a-good-explanation-of-curies-and-how-to-use-them

Need links to stable self that includes transactionClock in identity as well as current.
http://myservice/helloworld/123/c12341895a3
https://json-schema.org/latest/json-schema-hypermedia.html
http://json-schema.org/

JSON Hyper-Schema document
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

Example application that uses schema on the client side: A React component for building Web forms from JSON Schema.
https://github.com/mozilla-services/react-jsonschema-form

https://speakerdeck.com/olivergierke/ddd-and-rest-domain-driven-apis-for-the-web?slide=42

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

http://dontpanic.42.nl/2012/04/rest-and-ddd-incompatible.html

Representational State Transfer is a software architectural style that defines a set of constraints to be used for creating web services. Web services that conform to the REST architectural style, termed RESTful web services, provide interoperability between computer systems on the Internet. Wikipedia

How to name a REST resource:
https://www.thoughtworks.com/insights/blog/rest-api-design-resource-modeling

“The key abstraction of information in REST is a resource. Any information that can be named can be a resource: a document or image, a temporal service (e.g. "today's weather in Los Angeles"), a collection of other resources, a non-virtual object (e.g. a person), and so on. In other words, any concept that might be the target of an author's hypertext reference must fit within the definition of a resource. A resource is a conceptual mapping to a set of entities, not the entity that corresponds to the mapping at any particular point in time.” - Roy Fielding’s dissertation.

"The POST method is used to request that the origin server accept the entity enclosed in the request as a new subordinate of the resource identified by the Request-URI in the Request-Line." HTTP/1.1 Spec

