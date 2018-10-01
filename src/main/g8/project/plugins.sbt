// The Lagom plugin
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.4.8")
// Needed for importing the project into Eclipse
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")
// Platform Tooling plugin
addSbtPlugin("com.lightbend.rp" % "sbt-reactive-app" % "1.1.0")
// add sbt-ensime to your build or in global.sbt
addSbtPlugin("org.ensime" % "sbt-ensime" % "2.5.1")