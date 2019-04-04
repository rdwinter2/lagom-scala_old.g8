API Schema First ansible role
=============================

This ansible role takes an API schema definition file and generates algebraic datatypes and  Serialization/Deserialization (SerDes) code for various protocols.

Specify an API schema definition language (SDL) file written as a JSON schema or as a YAML/GraphQL inspired formation as described below.

Custom YAML/GraphQL inspired SDL.

DDD Value Objects - identified only by their values. Two value objects with identical attributes are identical.

DDD Entity Objects - have a lifecycle, and are identified by a synthetic identifier. Two entities with identical attributes but different identifiers are different entities. My current preference for synthetic identifiers are collision resistant identifiers ([CUID](https://github.com/prismagraphql/cuid-java)). They are relatively short, URL friendly, and horizontally scalable.

Built-in Scalar data types
* Date
* DateTime
* Int
* Float
* String
* Boolean
* Id 

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
* UoM to denote the unit of measure
  * DD - decimal degree
  * ft - feet
  * ft MSL - feet mean sea level
* @hidden for an attribute that is not normally returned during a query

```yaml
name: String! @check("[A-Za-z0-9_, \-]{1,255}")
latitude: String! @check("[-+]?\d+\.\d*", abs<=90) @UoM("DD")
```

Algebraic data type - An algebraic data type is a kind of composite type. They are built up from Product types and Sum types. They are one of the building blocks for [making illegal states unrepresentable](https://fsharpforfunandprofit.com/posts/designing-with-types-making-illegal-states-unrepresentable/).

Product types - a tuple or record (this and that)
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

Sum types - a discriminated union, enumeration, or variant type (this or that)

Here the syntax diverges slightly from YAML and borrows from the F# and Elm languages. 

```yaml
direction: 
    | North
    | South
    | East
    | West

emailContactInfo: String! @check("[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*")

postalContactInfo: String! @check(".{1,4096}")

contactInfo:
    | emailOnly: EmailContactInfo
    | postOnly: PostalContactInfo
    | emailAndPost: EmailContactInfo * PostalContactInfo

contact:
    name: Name
    contactInfo: ContactInfo
```

