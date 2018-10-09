import org.ensime.EnsimeKeys._
import org.ensime.EnsimePlugin

lazy val root = (project in file("."))
  .settings(name := "$name;format="norm"$")
  .aggregate(
    `common`,
    $name;format="camel"$Api,
    $name;format="camel"$Impl,
    $name;format="camel"$StreamApi,
    $name;format="camel"$StreamImpl)
  .settings(commonSettings: _*)

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

libraryDependencies += lagomScaladslPersistenceCassandra

lazy val $name;format="camel"$Api = (project in file("$name;format="norm"$-api"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val $name;format="camel"$Impl` = (project in file("$name;format="norm"$-impl"))
  .settings(commonSettings: _*)
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
  .dependsOn($name;format="camel"$Api)

lazy val $name;format="camel"$StreamApi = (project in file("$name;format="norm"$-stream-api"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val $name;format="camel"$StreamImpl = (project in file("$name;format="norm"$-stream-impl"))
  .settings(commonSettings: _*)
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn($name;format="camel"$StreamApi, $name;format="camel"$Api)

def commonSettings: Seq[Setting[_]] = Seq(
)

lagomCassandraCleanOnStart in ThisBuild := true
////lagomCassandraEnabled in ThisBuild := false
////lagomUnmanagedServices in ThisBuild := Map("cas_native" -> "http://localhost:9042")