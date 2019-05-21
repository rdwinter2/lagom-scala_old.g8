package $package$.api

//import $organization$.common.regex.Matchers
import $organization$.common.response.ErrorResponse
import $organization$.common.utils.JsonFormats._
import $organization$.common.validation.ValidationViolationKeys._

import ai.x.play.json.Jsonx
import akka.{Done, NotUsed}
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{
  KafkaProperties,
  PartitionKeyStrategy
}
import com.lightbend.lagom.scaladsl.api.deser.{
  DefaultExceptionSerializer,
  PathParamSerializer
}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wix.accord.Descriptions._
import java.time.{Duration, Instant}
import java.util.UUID
import julienrf.json.derived
import play.api.{Environment, Mode}
import play.api.libs.json._

//object $name;format="Camel"$Service  {
//  val TOPIC_NAME = "agg.event.$name;format="lower,snake,word"$"
//}

/**
  * The $name$ service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the $name;format="Camel"$Service.
  */
trait $name;format="Camel"$Service extends Service {

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("$name;format="norm"$").withCalls(
      // $name$ Queries
      restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$/:id", get$name;format="Camel"$ _),
      //restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$",     getAll$plural_name;format="Camel"$ _),
      // CRUDy Bulk Data Administration
      //restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-creation",        bulkCreate$name;format="Camel"$ _),
      //restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-replacement",     bulkReplace$name;format="Camel"$ _),
      //restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-mutation",        bulkMutate$name;format="Camel"$ _),
      //restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-deactivation",    bulkDeactivate$name;format="Camel"$ _),
      //restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-reactivation",    bulkReactivate$name;format="Camel"$ _),
      //restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-distruction",     bulkDistroy$name;format="Camel"$ _),
      // CRUDy Bulk Data Administration Queries
      //restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-creation/:id",        get$name;format="Camel"$BulkCreation _),
      //restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-replacement/:id",     get$name;format="Camel"$BulkReplacement _),
      //restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-mutation/:id",        get$name;format="Camel"$BulkMutation _),
      //restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-deactivation/:id",    get$name;format="Camel"$BulkDeactivation _),
      //restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-reactivation/:id",    get$name;format="Camel"$BulkReactivation _),
      //restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$/data-administration/bulk-distruction/:id",     get$name;format="Camel"$BulkDistruction _),
      // CRUDy plain REST
      //restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$",     post$name;format="Camel"$1 _),
      //restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/:id", post$name;format="Camel"$2 _),
      //restCall(Method.PUT,    "/api/$plural_name;format="lower,hyphen"$/:id", put$name;format="Camel"$ _),
      //restCall(Method.PATCH,  "/api/$plural_name;format="lower,hyphen"$/:id", patch$name;format="Camel"$ _),
      //restCall(Method.DELETE, "/api/$plural_name;format="lower,hyphen"$/:id", delete$name;format="Camel"$ _),
      //restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$/:id", get$name;format="Camel"$ _),
      //restCall(Method.GET,    "/api/$plural_name;format="lower,hyphen"$",     getAll$plural_name;format="Camel"$ _),
      // Data Administrator bulk data hammer interface
      // request body is an array of Create, Update, Delete (CUD) operations each containing an array
      // Example:
      // {"dataAdminActions": [{"create":[{"id":..,"name":..,"description":..},{..},..]},{"delete":[..]},..]}
      // NOTE: for update you just need to supply the id and the changed fields
      // Service will respond with a 202 Accepted and a link to check the status
      //}
      //restCall(Method.POST,   "/api/$plural_name;format="lower,hyphen"$/data-administration",                   administerCe _),
      // CRUDy DDDified REST without a proper ubiquitious language
      // Create
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/creation",                                create$name;format="Camel"$1 _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/creation",                            create$name;format="Camel"$2 _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/creation/:creationId",                    create$name;format="Camel"$3 _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/creation/:creationId",                create$name;format="Camel"$4 _),
      //restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/creation/:creationId",                getCreation$name;format="Camel"$ _),
      //pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/creation/:creationId/stream",         streamCreation$name;format="Camel"$ _),
      // Read
      // Update
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/replacement",                         replace$name;format="Camel"$1 _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/replacement/:replacementId",          replace$name;format="Camel"$2 _),
      //restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/replacement/:replacementId",          getReplacement$name;format="Camel"$ _),
      //pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/replacement/:replacementId/stream",   streamReplacement$name;format="Camel"$ _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/mutation",                            mutate$name;format="Camel"$1 _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/mutation/:mutationId",                mutate$name;format="Camel"$2 _),
      //restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/mutation/:mutationId",                getMutation$name;format="Camel"$ _),
      //pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/mutation/:mutationId/stream",         streamMutation$name;format="Camel"$ _),
      // Delete
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/deactivation",                        deactivate$name;format="Camel"$1 _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/deactivation/:deactivationId",        deactivate$name;format="Camel"$2 _),
      //restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/deactivation/:deactivationId",        getDeactivation$name;format="Camel"$ _),
      //pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/deactivation/:deactivationId/stream", streamDeactivation$name;format="Camel"$ _),
      // Undelete
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/reactivation",                        reactivate$name;format="Camel"$1 _),
      //restCall(Method.POST, "/api/$plural_name;format="lower,hyphen"$/:id/reactivation/:reactivationId",        reactivate$name;format="Camel"$2 _),
      //restCall(Method.GET,  "/api/$plural_name;format="lower,hyphen"$/:id/reactivation/:reactivationId",        getReactivation$name;format="Camel"$ _),
      //pathCall(             "/api/$plural_name;format="lower,hyphen"$/:id/reactivation/:reactivationId/stream", streamReactivation$name;format="Camel"$ _),
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

// $name$ Service Calls

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
  //def post$name;format="Camel"$1:                                             ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  //def post$name;format="Camel"$2($name;format="camel"$Id: String):                       ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  //def create$name;format="Camel"$1:                                           ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  //def create$name;format="Camel"$2($name;format="camel"$Id: String):                     ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  //def create$name;format="Camel"$3(creationId: String):                       ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  //def create$name;format="Camel"$4($name;format="camel"$Id: String, creationId: String): ServiceCall[Create$name;format="Camel"$Request, Either[ErrorResponse, Create$name;format="Camel"$Response]]
  // Retrieve status of creation request
  //def getCreation$name;format="Camel"$($name;format="camel"$Id: String, creationId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Creation$name;format="Camel"$Response]]
  //def streamCreation$name;format="Camel"$($name;format="camel"$Id: String, creationId: String): ServiceCall[NotUsed, Source[Creation$name;format="Camel"$Response, NotUsed]]
// }

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
  //def put$name;format="Camel"$($name;format="camel"$Id: String):                             ServiceCall[Replace$name;format="Camel"$Request, Either[ErrorResponse, Replace$name;format="Camel"$Response]]
  //def replace$name;format="Camel"$1($name;format="camel"$Id: String):                        ServiceCall[Replace$name;format="Camel"$Request, Either[ErrorResponse, Replace$name;format="Camel"$Response]]
  //def replace$name;format="Camel"$2($name;format="camel"$Id: String, replacementId: String): ServiceCall[Replace$name;format="Camel"$Request, Either[ErrorResponse, Replace$name;format="Camel"$Response]]
  // Retrieve status of replacement request
  //def getReplacement$name;format="Camel"$($name;format="camel"$Id: String, replacementId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Replacement$name;format="Camel"$Response]]
  //def streamReplacement$name;format="Camel"$($name;format="camel"$Id: String, replacementId: String): ServiceCall[NotUsed, Source[Replacement$name;format="Camel"$Response, NotUsed]]
// }

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
  //def patch$name;format="Camel"$($name;format="camel"$Id: String):                       ServiceCall[Mutate$name;format="Camel"$Request, Either[ErrorResponse, Mutate$name;format="Camel"$Response]]
  //def mutate$name;format="Camel"$1($name;format="camel"$Id: String):                     ServiceCall[Mutate$name;format="Camel"$Request, Either[ErrorResponse, Mutate$name;format="Camel"$Response]]
  //def mutate$name;format="Camel"$2($name;format="camel"$Id: String, mutationId: String): ServiceCall[Mutate$name;format="Camel"$Request, Either[ErrorResponse, Mutate$name;format="Camel"$Response]]
  // Retrieve status of mutation request
  //def getMutation$name;format="Camel"$($name;format="camel"$Id: String, mutationId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Mutation$name;format="Camel"$Response]]
  //def streamMutation$name;format="Camel"$($name;format="camel"$Id: String, mutationId: String): ServiceCall[NotUsed, Source[Mutation$name;format="Camel"$Response, NotUsed]]
// }

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
  //def patch$name;format="Camel"$($name;format="camel"$Id: String):                               ServiceCall[Deactivate$name;format="Camel"$Request, Either[ErrorResponse, Deactivate$name;format="Camel"$Response]]
  //def deactivate$name;format="Camel"$1($name;format="camel"$Id: String):                         ServiceCall[Deactivate$name;format="Camel"$Request, Either[ErrorResponse, Deactivate$name;format="Camel"$Response]]
  //def deactivate$name;format="Camel"$2($name;format="camel"$Id: String, deactivationId: String): ServiceCall[Deactivate$name;format="Camel"$Request, Either[ErrorResponse, Deactivate$name;format="Camel"$Response]]
  // Retrieve status of deactivation request
  //def getDeactivation$name;format="Camel"$($name;format="camel"$Id: String, deactivationId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Deactivation$name;format="Camel"$Response]]
  //def streamDeactivation$name;format="Camel"$($name;format="camel"$Id: String, deactivationId: String): ServiceCall[NotUsed, Source[Deactivation$name;format="Camel"$Response, NotUsed]]
// }

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
  //def patch$name;format="Camel"$($name;format="camel"$Id: String):                               ServiceCall[Reactivate$name;format="Camel"$Request, Either[ErrorResponse, Reactivate$name;format="Camel"$Response]]
  //def reactivate$name;format="Camel"$1($name;format="camel"$Id: String):                         ServiceCall[Reactivate$name;format="Camel"$Request, Either[ErrorResponse, Reactivate$name;format="Camel"$Response]]
  //def reactivate$name;format="Camel"$2($name;format="camel"$Id: String, reactivationId: String): ServiceCall[Reactivate$name;format="Camel"$Request, Either[ErrorResponse, Reactivate$name;format="Camel"$Response]]
  // Retrieve status of reactivation request
  //def getReactivation$name;format="Camel"$($name;format="camel"$Id: String, reactivationId: String):    ServiceCall[NotUsed, Either[ErrorResponse, Reactivation$name;format="Camel"$Response]]
  //def streamReactivation$name;format="Camel"$($name;format="camel"$Id: String, reactivationId: String): ServiceCall[NotUsed, Source[Reactivation$name;format="Camel"$Response, NotUsed]]
// }

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
  //def getAll$plural_name;format="Camel"$(page: Option[String]): ServiceCall[NotUsed, utils.PagingState[GetAll$plural_name;format="Camel"$Response]]
  //def getAll$plural_name;format="Camel"$:                       ServiceCall[NotUsed, GetAll$plural_name;format="Camel"$Response]
// }

//  def stream$plural_name;format="Camel"$
//    : ServiceCall[NotUsed, Source[$name;format="Camel"$Resource, NotUsed]]

// $name$ Topic

  def $name;format="camel"$MessageBrokerEvents: Topic[$name;format="Camel"$MessageBrokerEvent]

}

// $name$ regex matchers

object Matchers {
  val Email =
    """^[a-zA-Z0-9\.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"""
  val Identifier = """^[a-zA-Z0-9\-\.\_\~]{1,64}\$"""
  val Name = """^[a-zA-Z0-9\-\.\_\~]{1,128}\$"""
  val Description = """^.{1,2048}\$"""
  val Motivation = """^.{1,2048}\$"""
  val Op = """^add|remove|replace|move|copy|test\$"""
}

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
//   final case class Cat(name: String) extends Pet
//   final case class Fish(name: String, color: String) extends Pet
//   final case class Squid(name: String, age: Int) extends Pet

// $name$ object
final case class $name;format="Camel"$Type(
  name: String,
  description: Option[String])

object $name;format="Camel"$Type {
  implicit val format: Format[$name;format="Camel"$Type] = Jsonx.formatCaseClass

  val $name;format="camel"$Validator: Validator[$name;format="Camel"$Type] =
    validator[$name;format="Camel"$Type] { o =>
      o.name is notEmpty
      o.name should matchRegexFully(Matchers.Name)
      o.description.each should matchRegexFully(Matchers.Description)
    }
}
// }

// Supporting algebraic data types {

// Identity.identifier and Identity.revision uniquely identify a particular version of an entity
final case class $name;format="Camel"$Identity(
  identifier: String       // a collision resistant unique identifier for the entity which remains constant throughout its lifecycle
)

object $name;format="Camel"$Identity {
  implicit val format: Format[$name;format="Camel"$Identity] = Jsonx.formatCaseClass

  val identityValidator: Validator[$name;format="Camel"$Identity] =
    validator[$name;format="Camel"$Identity] { i =>
      i.identifier is notEmpty
      i.identifier should matchRegexFully(Matchers.Identifier)
      // need Option[Int]
      //identity.revision should be >= 0
    }
}

// Not so sure if object level metadata is needed or just event level metadata
final case class $name;format="Camel"$Metadata(
  revision: Int             // a monotonically increasing count of changes perisited by this entity
//  created: Instant,      // When the 
//  lastModified: Option[Instant], // Last Change Transaction Time: the time assigned by the persistent entity
//                            // it will be different from the time it was persisted in Cassandra or Kafka or other datastore
//                            // the persistent entity is where the real transaction occurs, however it still need to be written to the log to become durable 
//  hash: Option[String],     // SHA256 hash of json representation of  the persistent entity's data
//                            // import java.security.MessageDigest import java.math.BigInteger MessageDigest.getInstance("SHA-256").digest("some string".getBytes("UTF-8")).map("%02x".format(_)).mkString
//  previousHash: Option[String], // SHA256 hash of the prior state of the  persistent entity's data
//  validTimeBegin: Option[Instant],    // "valid time (VT) is the time period during which a database fact is valid in the modeled reality." https://en.wikipedia.org/wiki/Valid_time
//  validTimeEnd: Option[Instant],
//  decisionTimeBegin: Option[Instant],  // "Decision time is the time period during which a fact stored in the database was decided to be valid.'' https://en.wikipedia.org/wiki/Temporal_database
//  decisionTimeEnd: Option[Instant]
  
)

object $name;format="Camel"$Metadata {
  implicit val format: Format[$name;format="Camel"$Metadata] = Jsonx.formatCaseClass
}

final case class HypertextApplicationLanguage(
  halLinks: Seq[HalLink]
  )

object HypertextApplicationLanguage {
  implicit val format: Format[HypertextApplicationLanguage] = Jsonx.formatCaseClass
}

final case class HalLink(
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

final case class Mutation(
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

// $name$ Resource
final case class $name;format="Camel"$Resource(
  $name;format="camel"$Type: $name;format="Camel"$Type
)

object $name;format="Camel"$Resource {
  implicit val format: Format[$name;format="Camel"$Resource] = Jsonx.formatCaseClass

  val $name;format="camel"$ResourceValidator: Validator[$name;format="Camel"$Resource] =
    validator[$name;format="Camel"$Resource] { r =>
      r.$name;format="camel"$Type is valid($name;format="Camel"$Type.$name;format="camel"$Validator)
    }
}

// $name$ Request

// TODO: include span ID as the unique identity of a Create$name;format="Camel"$Request

// Create $name$ Request payload {
final case class Create$name;format="Camel"$Request(
    $name;format="camel"$Type: $name;format="Camel"$Type
) {}

case object Create$name;format="Camel"$Request {
  implicit val format: Format[Create$name;format="Camel"$Request] = Jsonx.formatCaseClass

  implicit val create$name;format="Camel"$RequestValidator
    : Validator[Create$name;format="Camel"$Request] =
    validator[Create$name;format="Camel"$Request] { r =>
      r.$name;format="camel"$Type is valid($name;format="Camel"$Type.$name;format="camel"$Validator)
    }
}
// }

final case class Replace$name;format="Camel"$Request(
    replacement$name;format="Camel"$: $name;format="Camel"$Type,
    motivation: Option[String]
) {}

case object Replace$name;format="Camel"$Request {
  implicit val format: Format[Replace$name;format="Camel"$Request] = Jsonx.formatCaseClass

  implicit val replace$name;format="Camel"$RequestValidator
    : Validator[Replace$name;format="Camel"$Request] =
    validator[Replace$name;format="Camel"$Request] { r =>
      r.replacement$name;format="Camel"$ is valid($name;format="Camel"$Type.$name;format="camel"$Validator)
      r.motivation.each should matchRegexFully(Matchers.Motivation)
    }
}

final case class Mutate$name;format="Camel"$Request(
    mutations: Seq[Mutation],
    motivation: Option[String]
) {}

case object Mutate$name;format="Camel"$Request {
  implicit val format: Format[Mutate$name;format="Camel"$Request] = Jsonx.formatCaseClass

  implicit val mutate$name;format="Camel"$RequestValidator
    : Validator[Mutate$name;format="Camel"$Request] =
    validator[Mutate$name;format="Camel"$Request] { mutate$name;format="Camel"$Request =>
      mutate$name;format="Camel"$Request.mutations.each is valid(Mutation.mutationValidator)
      mutate$name;format="Camel"$Request.motivation.each should matchRegexFully(Matchers.Motivation)
    }
}

// Response

final case class Create$name;format="Camel"$Response(
    $name;format="camel"$Identity: $name;format="Camel"$Identity,
    $name;format="camel"$Type: $name;format="Camel"$Type,
    $name;format="camel"$Hal: Option[HypertextApplicationLanguage]
)

object Create$name;format="Camel"$Response {
  implicit val format: Format[Create$name;format="Camel"$Response] = Jsonx.formatCaseClass
}

final case class Replace$name;format="Camel"$Response(
    $name;format="camel"$Identity: $name;format="Camel"$Identity,
    $name;format="camel"$Type: $name;format="Camel"$Type,
    $name;format="camel"$Hal: Option[HypertextApplicationLanguage]
)

object Replace$name;format="Camel"$Response {
  implicit val format: Format[Replace$name;format="Camel"$Response] = Json.format
}

final case class Get$name;format="Camel"$Response(
  $name;format="camel"$Identity: $name;format="Camel"$Identity,
    $name;format="camel"$Type: $name;format="Camel"$Type
)

object Get$name;format="Camel"$Response {
  implicit val format: Format[Get$name;format="Camel"$Response] = Json.format
}

final case class GetAll$plural_name;format="Camel"$Response($name;format="camel"$s: Seq[$name;format="Camel"$Resource])

object GetAll$plural_name;format="Camel"$Response {
  implicit val format: Format[GetAll$plural_name;format="Camel"$Response] = Json.format
}

// Message Broker Event
// One service to many other services

sealed trait $name;format="Camel"$MessageBrokerEvent {
  val $name;format="camel"$Identity: $name;format="Camel"$Identity
}

final case class $name;format="Camel"$Created(
  $name;format="camel"$Identity: $name;format="Camel"$Identity,
  $name;format="camel"$Type: $name;format="Camel"$Type
) extends $name;format="Camel"$MessageBrokerEvent

object $name;format="Camel"$Created {
  implicit val format: Format[$name;format="Camel"$Created] = Json.format
}

//final case class $name;format="Camel"$BrokerEvent(event: $name;format="Camel"$EventType,
//                          id: String,
//                          data: Map[String, String] = Map.empty[String, String])

object $name;format="Camel"$MessageBrokerEvent {
  implicit val format: Format[$name;format="Camel"$MessageBrokerEvent] =
    derived.flat.oformat((__ \ "type").format[String])
}

//object $name;format="Camel"$EventTypes extends Enumeration {
//  type $name;format="Camel"$EventType = Value
//  val REGISTERED, DELETED, VERIFIED, UNVERIFIED = Value
//
//  implicit val format: Format[$name;format="Camel"$EventType] = enumFormat($name;format="Camel"$EventTypes)
//}
