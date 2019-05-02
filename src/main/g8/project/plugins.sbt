// The Lagom plugin
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.5.0")
// Needed for importing the project into Eclipse
//addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")
// Platform Tooling plugin
addSbtPlugin("com.lightbend.rp" % "sbt-reactive-app" % "1.1.0")
// add sbt-ensime to your build or in global.sbt
addSbtPlugin("org.ensime" % "sbt-ensime" % "2.5.1")
// In project/plugins.sbt. Note, does not support sbt 0.13, only sbt 1.0.
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.5.1")
// A Scala library to fetch dependencies from Maven / Ivy repositories
//addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.1.0-SNAPSHOT")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")