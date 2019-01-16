<!--- to transclude content use the following syntax at the beginning of a line "transclude::file_name.ext::[an optional regular expression]" -->
<!--- Never directly edit transcluded content, always edit the source file -->
$name$
=============

## <a name="overview"></a>Overview

This is a simple $name$ self-contained system (SCS) to demonstrate the use of the Lagom framework. It has a simple CRUD interface as well as methods for doing a proper Domain Driven Design (DDD) ubiquitious language (UL).

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
   * [$name$ Tag](#helloworldtagmicroservice)
   * [Tag $name$ Saga](#taghelloworldsagamicroservice)
   * [Authorization](#authorizationmicroservice)
   * [Authentication](#authenticationmicroservice)
2. [Glossary - Domain](#glossarydomain)
2. [Glossary - Technical](#glossarytechnical)
3. [References](#references)
4. [Notes](#notes)

## <a name="description"></a>Description

This project has been generated using the rdwinter2/lagom-scala.g8 template.

For instructions on running and testing the project, see [Using Lagom with Scala](https://www.lagomframework.com/get-started-scala.html).

To generate a new project execute the following and supply values for (name, plural_name, organization, version, and package):

```bash
sbt new rdwinter2/lagom-scala.g8
```

After running `git init` or cloning from a repository `cd` into the directory and run `./custom-hooks/run-after-clone.sh`.

## <a name="systemdesign"></a>System Design

The design of $name$ is modeled using an Events-First Domain Driven Design (E1DDD). By focusing on events first, we can better understand the essence of the domain. "When you start modeling events, it forces you to think about the behavior of the system, as opposed to thinking about structure inside the system." [Greg Young](https://www.youtube.com/watch?v=LDW0QWie21s)

$name$ is designed as a [Self Contained System](#selfcontainedsystem) (SCS) and uses the [autonomous bubble pattern](#autonomousbubblepattern) to isolate itself from other systems within the System of Systems.

As a SCS $name$ adheres to certain characteristics to promote autonomy. The [first three SCS characteristics](https://scs-architecture.org/) are:
> 1. Each SCS is an [autonomous web application](https://scs-architecture.org/#autonomous). For the SCS's domain, all data, the logic to process that data and all code to render the web interface is contained within the SCS. An SCS can fulfill its primary use cases on its own, without having to rely on other systems being available.
> 2. Each SCS is owned by [one team](https://scs-architecture.org/#one-team). This does not necessarily mean that only one team can change the code, but the owning team has the final say on what goes into the code base, for example by merging pull-requests.
> 3. Communication with other SCSs or 3rd party systems is [asynchronous wherever possible](https://scs-architecture.org/#async). Specifically, other SCSs or external systems should not be accessed synchronously within the SCS's own request/response cycle. This decouples the systems, reduces the effects of failure, and thus supports autonomy. The goal is decoupling concerning time: An SCS should work even if other SCSs are temporarily offline. This can be achieved even if the communication on the technical level is synchronous, e.g. by replicating data or buffering requests.

Another important aspect of SCS is that it follows [Promise Theory](http://markburgess.org/PromiseMethod.pdf). Autonomous agents that make voluntarily promises tend to converge to a desired state. When obligations are imposed the system transitions from a state of certainty to one that is less certain. What this means for our SCS is that its primary capabilities should take in to account methods for making progress even when external dependencies are unavailable.

By using the [autonomous bubble pattern](#autonomousbubblepattern) $name$ is [shielded from the entanglements of the legacy world](https://www.thoughtworks.com/radar/techniques/autonomous-bubble-pattern). This increases autonomy, reduces development friction, and improves architecture modernization efforts.

From [Eric Evans](http://domainlanguage.com/wp-content/uploads/2016/04/GettingStartedWithDDDWhenSurroundedByLegacySystemsV1.pdf)
> The bubble isolates that work so the team can evolve a model that addresses the chosen area, relatively unconstrained by the concepts of the legacy systems.
> The context boundary of the bubble is established with the popular anticorruption layer (ACL). This boundary isolates your new work from the larger system, allowing you to have a very different model in your context than exists just on the other side of the border.

## <a name="domainmodel"></a>Domain Model

### <a name="externaleventflow"></a>External Event Flow



### <a name="internaleventflow"></a>Internal Event Flow

### <a name="conceptualdatamodel"></a>Conceptual Data Model


## <a name="microservices"></a>Microservices

### <a name="helloworldmicroservice"></a>$name$

### <a name="helloworldtagmicroservice"></a>Tag

### <a name="taghelloworldsagamicroservice"></a>Tag $name$ Saga

### <a name="authorizationmicroservice"></a>Authorization

The authorization microservice exchanges an authentication token attesting to the identity of the user, for an authorization token which includes claims for what the user is allowed to do in this SCS.

The SCS can be programmed to accept a variety of authentication tokens or tickets. For now, it just accepts a JSON Web Token (JWT) with a user name claim.

The token given during the exchange is also a JWT and includes role names this user is authorized to have.

The authorization microservice also has commands for an administrative user to add roles and to assign roles to users.

### <a name="authenticationmicroservice"></a>Authentication

The $name$ SCS includes a simple username/password login authentication microservice. It is included only for completeness and actually belongs in an enterprise or federated identity service.


## <a name="glossarydomain"></a>Glossary - Domain

## <a name="glossarytechnical"></a>Glossary - Technical

<a name="autonomousbubblepattern"></a>Autonomous Bubble Pattern
  : "This approach involves creating a fresh context for new application development that is shielded from the entanglements of the legacy world. This is a step beyond just using an anticorruption layer. It gives the new bubble context full control over its backing data, which is then asynchronously kept up-to-date with the legacy systems. It requires some work to protect the boundaries of the bubble and keep both worlds consistent, but the resulting autonomy and reduction in development friction is a first bold step toward a modernized future architecture." [ThoughtWorks](https://www.thoughtworks.com/radar/techniques/autonomous-bubble-pattern)

<a name="boundedcontext"></a>Bounded Context
  : "A Bounded Context is an explicit boundary within which a domain model exists. Inside the boundary all terms and phrases of the Ubiquitous Language have specific meaning, and the model reflects the Language with exactness." Implementing DDD by Vaughn Vernon

<a name="domaindrivendesign"></a>Domain Driven Design (DDD)
  : "an approach to software development for complex needs by connecting the implementation to an evolving model. The premise of domain-driven design is the following: (1) placing the project's primary focus on the core domain and domain logic; (2) basing complex designs on a model of the domain; (3) initiating a creative collaboration between technical and domain experts to iteratively refine a conceptual model that addresses particular domain problems." [Wikipedia](https://en.wikipedia.org/wiki/Domain-driven_design)

<a name="eventsfirstdomaindrivendesign"></a>Events-First Domain Driven Design (E1DDD)
  : "The term Events-First Domain-Driven Design was coined by Russ Miles, and is the name for set of design principles that has emerged in our industry over the last few years and has proven to be very useful in building distributed systems at scale. These principles help us to shift the focus from the nouns (the domain objects) to the verbs (the events) in the domain. A shift of focus gives us a greater starting point for understanding the essence of the domain from a data flow and communications perspective, and puts us on the path toward a scalable event-driven design." [Reactive Microsystems](https://www.oreilly.com/library/view/reactive-microsystems/9781491994368/ch04.html)

<a name="promisetheory"></a>Promise Theory
  : "Promise theory is about modelling causation, change and balance between communicating agents (human or machine), It is about finding the necessary and sufficient conditions for cooperation between distributed agents.[Some Notes about Promise Theory](http://markburgess.org/PromiseMethod.pdf)

<a name="selfcontainedsystem"></a>Self-contained System (SCS)
  : "The Self-contained System (SCS) approach is an architecture that focuses on a separation of the functionality into many independent systems, making the complete logical system a collaboration of many smaller software systems." [scs-architecture.org](https://scs-architecture.org/index.html)

<a name="domainmodel"></a>Domain Model
  : "A conceptual model of the domain that incorporates both behavior and data." [Wikipedia](https://en.wikipedia.org/wiki/Domain_model)

<a name="domainprimitive"></a>Domain Primitive
  : "A value object so precise in its definition that it, by its mere existance, manifests its validity is called a Domain Primitive." [Secure by Design](https://www.manning.com/books/secure-by-design)

Ubiquitous Language
  :

## <a name="references"></a>References

[Handling aggregate root with deep object hierarchy](https://softwareengineering.stackexchange.com/questions/355954/handling-aggregate-root-with-deep-object-hierarchy)

## <a name="notes"></a>Notes


The REST call identifiers for the $name$ project are defined as:

## Data Administration

Some people still want to hammer the data. They need a bulk interface to be a bigger hammer.

Service called data-administration. Only those with the proper role.

POST to api/data-administration

Body: paths should not be to something outside this Self-Contained System?

```json
{
  "operations" [
  {
    "method": "POST",
    "path": "api/$name;format="norm"$/creation",
    "headers": [
    {
      "name": "Accept",
      "value": "application/json"
      }
    ],
    body": {
      "name": "1",
      "description": "one"
    }
  },
  {
    "method": "POST",
    "path": "api/$name;format="norm"$/creation",
    "headers": [
    {
      "name": "Accept",
      "value": "application/json"
      }
    ],
    body": {
      "name": "2",
      "description": "two"
    }
  },
  ...
  ]
}
```

Body can be compressed for transfer. Streaming update? resumeable? https://tus.io/
