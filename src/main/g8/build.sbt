import org.ensime.EnsimeKeys._
import org.ensime.EnsimePlugin

organization in ThisBuild := "$organization$"
version in ThisBuild := "$version$"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.7"

ensimeScalaVersion in ThisBuild := "2.12.7"
ensimeIgnoreScalaMismatch in ThisBuild := true
ensimeIgnoreMissingDirectories := true

lazy val serviceLocatorProject = ProjectRef(uri("."), "lagom-internal-meta-project-service-locator")
ensimeUnmanagedSourceArchives in serviceLocatorProject := Nil
ensimeUnmanagedJavadocArchives in serviceLocatorProject := Nil
ensimeScalacTransformer in serviceLocatorProject := identity
ensimeScalacOptions in serviceLocatorProject := EnsimePlugin.ensimeSuggestedScalacOptions(scalaVersion.value)
ensimeJavacOptions in serviceLocatorProject := Nil

lazy val cassandraProject = ProjectRef(uri("."), "lagom-internal-meta-project-cassandra")
ensimeUnmanagedSourceArchives in cassandraProject := Nil
ensimeUnmanagedJavadocArchives in cassandraProject := Nil
ensimeScalacTransformer in cassandraProject := identity
ensimeScalacOptions in cassandraProject := EnsimePlugin.ensimeSuggestedScalacOptions(scalaVersion.value)
ensimeJavacOptions in cassandraProject := Nil

lazy val kafkaProject = ProjectRef(uri("."), "lagom-internal-meta-project-kafka")
ensimeUnmanagedSourceArchives in kafkaProject := Nil
ensimeUnmanagedJavadocArchives in kafkaProject := Nil
ensimeScalacTransformer in kafkaProject := identity
ensimeScalacOptions in kafkaProject := EnsimePlugin.ensimeSuggestedScalacOptions(scalaVersion.value)
ensimeJavacOptions in kafkaProject := Nil

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "4.0.0"
val cuid = "cool.graph" % "cuid-java" % "0.1.1"
val jwt = "com.pauldijou" %% "jwt-play-json" % "0.12.1"
val accord = "com.wix" %% "accord-core" % "0.6.1"

lazy val `$name;format="norm"$` = (project in file("."))
  .aggregate(`$name;format="norm"$-api`, `$name;format="norm"$-impl`, `$name;format="normalize"$-stream-api`, `$name;format="normalize"$-stream-impl`)

lazy val `$name;format="norm"$-api` = (project in file("$name;format="norm"$-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `$name;format="norm"$-impl` = (project in file("$name;format="norm"$-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`$name;format="norm"$-api`)

lazy val `$name;format="norm"$-stream-api` = (project in file("$name;format="norm"$-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `$name;format="norm"$-stream-impl` = (project in file("$name;format="norm"$-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`$name;format="norm"$-stream-api`, `$name;format="norm"$-api`)
