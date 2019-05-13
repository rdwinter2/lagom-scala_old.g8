API Schema First ansible role
=============================

This ansible role takes an API schema definition file and generates algebraic datatypes and Serialization/Deserialization ([SerDes](https://en.wikipedia.org/wiki/SerDes)) code for various protocols.

Specify an API schema definition language (SDL) file written as a JSON schema or as a YAML/GraphQL inspired formation as described below.

Custom YAML/GraphQL inspired SDL.

In Domain Driven Design (DDD) Aggregate Root (AR) entities have a unique identity and form the only ACID transactional boundary for interacting with the domain. All commands, which express an intent to change system state, must be directed to a specific AR. An AR may be composed of other entities, however these entities have no global identity or lifecycle. If the AR is deleted, the subordinate entities are also deleted. If the AR has a deep hierarchy, you can reference other ARs if 1) the original AR doesn't enforce invariants between those ARs, and 2) you need to link directly to those ARs not subordinate entities.

DDD Value Objects - identified only by their values. Two value objects with identical attributes are identical.

DDD Entity Objects - have a lifecycle, and are identified by a synthetic identifier, also called a [surrogate key](https://en.wikipedia.org/wiki/Surrogate_key). Two entities with identical attributes but different identifiers are different entities. This identifier is assigned when the entity is create and remains constain throughout its lifecycle. My current preference for synthetic identifiers are collision resistant identifiers ([CUID](https://github.com/prismagraphql/cuid-java)). They are relatively short, URL friendly, and horizontally scalable. Additional data is available to refer to a particular version of an entity. This data may include the [Fault Containment Region](#fault-containment-region), transaction ID, transaction time, transaction hash, [global consensus](#global-consensus) ordering, etc. To uniquely identify a version of an entity one or more pieces of data are required from the above depending on the circumstance.

Built-in Scalar data types
* Date
* DateTime
* Int
* Float
* String
* Boolean
* Id 

\$\$ Boolean = \{ true, false \} \$\$
\$\$ Int = \{ n | n \in \mathbb{Z} \} \$\$
\$\$ String = \{ c_0c_1 ... | c_0c_1 \in unicode \} \$\$

```yaml
# type names are defined in lower camelCase
# type names are used in upper CamelCase
name: String
# list types are specified by placing the type definition in square brackets
names: [String]
# types can be declared as NOT NULL by appending an exclamation mark
name: String!
# or
names: [String!]  # A possibly empty list of non-null Strings
names: [String]!  # A non empty list of nullable Strings
names: [String!]! # A  non empty list of non-null Strings
```
A description of the type can be added prior to the definition with a here doc indented to the same level.

```yaml
"""
The name of the entity.
"""
name: String
```

Additional annotations can be added 

* @default to specify a default value
* @check for defining check constraints
  * some mathematical functions can be used
  * strings are taken to be regular expressions that must full match
  * date formats such as @check(iso-8601)
  * datetime formats such as @check(iso-8601=seconds utc)
  * the length function can be specified in bytes and optionally a [binary prefix](https://en.wikipedia.org/wiki/Binary_prefix) without the "i" KMGTPE (K vice Ki).
* UoM to denote the unit of measure
  * DD - decimal degree
  * ft - feet
  * ft MSL - feet mean sea level
* @hidden for an attribute that is not normally returned during a query
* @isUnique for a globally unique identifier
* @isPartUnique for an identifier that is only unique within its hierarchy
* @aliases for a list of deprecated aliases

```yaml
name: String! @check("[A-Za-z0-9_, \-]{1,255}")
latitude: String! @check("[-+]?\d+\.\d*", abs<=90) @UoM("DD")
```

Algebraic data type - An algebraic data type is a kind of composite type. They are built up from Product types and Sum types. They are one of the building blocks for [making illegal states unrepresentable](https://fsharpforfunandprofit.com/posts/designing-with-types-making-illegal-states-unrepresentable/).

Product types (*) - a tuple or record (this and that)
```yaml
# Specified as a YAML collection type using indentation.
geospatialCoordinate: GeospatialCoordinate
    latitude: Float! @check(abs<=90) @UoM("DD")
    longitude: Float! @check(abs<=180) @UoM("DD")
    elevation: Int! @UoM("ft MSL")
    horizontalAccuracy: Int! @UoM("ft")
    verticalAccuracy: Int! @UoM("ft")
# May also be specified with the product operator '*' between types
emailAndPost: EmailContactInfo * PostalContactInfo
```

Sum types (|) - a discriminated union, enumeration, or variant type (this or that)

Here the syntax diverges slightly from YAML and borrows from the F# and Elm languages. 

```yaml
direction: North | South | East | West

name: String @check(".{,2048}")

emailContactInfo: String! @check("[a-zA-Z0-9\.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*")

postalContactInfo: String! @check(".{1,4096}")

contactInfo:
    | emailOnly: EmailContactInfo
    | postOnly: PostalContactInfo
    | emailAndPost: EmailContactInfo * PostalContactInfo

# Since we don't need to rename fields we can use the product operator
contact: 
    name: Name!
    contactInfo: ContactInfo!
```

The last part `contract` could also be specified as:

```yaml
contact: 
    Name!
    ContactInfo!
```

Or even:

```yaml
contact: Name! * ContactInfo!
```

Pointers (a.k.a Links) within and among Entities. Dot (.) notation is used to refer to an element of an entity such as its Id. NOTE: You cannot externally link to an entity that is not the aggregate root without going through the root. Short links, to another entity in the same aggregate entity, use a single dash and a greater than sign, ->. Long links, to another aggregate entity, use two dashes and a greater than sign, -->.

```yaml
"""
A tag is a label attached to someone or something for the purpose of identification or to give other information. A tag is a value object and its name is its identifier.
"""
tag:
    name: String! @check(".{,64}")

"""
An article is a written work published in a print or electronic medium. It may be for the purpose of propagating news, research results, academic analysis, or debate.
"""
article:
    id: Id!
    title: String @check(len<=2K)
    description: String @check(len<=4K)
    body: String @check(len<=2M)
    tags: [--> Tag.name!]
```

## YAML anchors


Content can be duplicated and included across the document.

From [Learn X in Y minutes](https://learnxinyminutes.com/docs/yaml/)
```
# Anchors can be used to duplicate/inherit properties
base: &base
  name: Everyone has same name

# The regexp << is called Merge Key Language-Independent Type. It is used to
# indicate that all the keys of one or more specified maps should be inserted
# into the current map.

foo: &foo
  <<: *base
  age: 10

bar: &bar
  <<: *base
  age: 20

# foo and bar would also have name: Everyone has same name
```

Invariants, a.k.a Business Rules, ?

Example from [Real World App](https://github.com/gothinkster/realworld/tree/master/api).

```yaml
user:
    email:
    token:
    username:
    bio:
    image:
profile:
    username:
    bio:
    image:
    following:
article:
    slug:
    title:
    description:
    body:
    tags: @aliases("tagList")
    createdAt:
    updatedAt:
    favorited:
    favoritesCount:
    author: Profile
comment:
    id:
    createAt:
    updatedAt:
    body:
    author: Profile
tag:
    name: String
error:
    body: String
```

Commands

```yaml
"""
Allow a user to authenticate themselves and recieve a JWT token for further interaction.
"""
login:
    resource: User
    method: POST
    endpoint: /api/users/login
    body: Email! * Password!
    response: User | Error
    events: UserLoggedIn
    error: 403 | 404 | 422

Example request body:

{
  "user":{
    "email": "jake@jake.jake",
    "password": "jakejake"
  }
}
No authentication required, returns a User

Required fields: email, password

# DDD version
authentication:
    resource: User
    endpoint: /api/users/:email/authentication
    body: Password!
    response: User | Error
    events: UserLoggedIn
    error: 403 | 404 | 422
```

```yaml

```

### <a name="global-consensus"></a> Global Consensus

Use an asynchronous Byzantine-fault tolerant mechansim for determining global consensus ordering of transactions.
[Tendermint Core](https://www.tendermint.com/) or [Hydra Hashgraph](https://www.hedera.com/).

### <a name="fault-containment-region"></a> Fault Containment Region (FCR)

> A fault-containment region is defined as the set of subsystems that share one or more common resources and may be affected by a single fault.
[Fault containment and error detection in the time-triggered architecture](https://ieeexplore.ieee.org/document/1193942)

A FCR may be as small as a single laptop or mobile device. Or it may be many compute and storage nodes that all share a common dependency. One FCR is distinct from another FCR if and only if there exists no single fault, including network, power, cooling, fire, etc., that would impact both simultaniously. A loss of connectivity to a WAN need not be considered a fault for this definition if the system can still perform its mission under those conditions. 

[availability zone](https://searchaws.techtarget.com/definition/availability-zones)

[NASA Fault Management Handbook](https://www.nasa.gov/pdf/636372main_NASA-HDBK-1002_Draft.pdf)

[Reliability and Fault Tolerance](https://www.uio.no/studier/emner/matnat/fys/FYS4220/h11/undervisningsmateriale/forelesninger-rt/2011-9_Reliability_and_Fault_Tolerance.pdf)

Note: Within a single FCR each entity has a [single writer](https://mechanical-sympathy.blogspot.com/2011/09/single-writer-principle.html). Between FCRs, an entity may accept conflicting commands that must be resolved via [global consensus](#global-consensus).