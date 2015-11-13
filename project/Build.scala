import sbt._
import Keys._

import com.typesafe.sbt.SbtScalariform._
import _root_.scalariform.formatter.preferences.IndentSpaces
import scalariform.formatter.preferences._

object Build extends Build {

  // Project information
  val ORGANIZATION = "io.shinto"
  val PROJECT_NAME = "amaterasu"
  val PROJECT_VERSION = "0.1.0"
  val SCALA_VERSION = "2.11.7"

  // Mesos native library path
  val pathToMesosLibs = "/usr/local/lib"

  lazy val root = Project(
    id = PROJECT_NAME,
    base = file("."),
    settings = commonSettings
  )

  lazy val commonSettings =
    basicSettings ++
      formatSettings ++
      net.virtualvoid.sbt.graph.Plugin.graphSettings

  lazy val basicSettings = Seq(
    version := PROJECT_VERSION,
    organization := ORGANIZATION,
    scalaVersion := SCALA_VERSION,

    libraryDependencies ++= Seq(
      "org.apache.mesos" % "mesos" % "0.24.0",
      "com.typesafe" % "config" % "1.2.1",
      "org.slf4j" % "slf4j-api" % "1.7.9",
      "ch.qos.logback" % "logback-classic" % "1.1.2" % "runtime",
      "org.scalatest" %% "scalatest" % "2.2.2" % "test",
      "com.github.nscala-time" %% "nscala-time" % "2.2.0",
      "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.6.1",
      "com.amazonaws" % "aws-java-sdk-s3" % "1.10.27",
      "commons-io" % "commons-io" % "2.4",
    
      "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"
    ),

    scalacOptions in Compile ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature"
    ),

    javaOptions += "-Djava.library.path=%s:%s".format(
      sys.props("java.library.path"),
      pathToMesosLibs
    ),

    fork in run := true,

    fork in Test := true,

    parallelExecution in Test := false
  )

  lazy val formatSettings = scalariformSettings ++ Seq(
    ScalariformKeys.preferences := FormattingPreferences()
      .setPreference(IndentWithTabs, false)
      .setPreference(IndentSpaces, 2)
      .setPreference(AlignParameters, false)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
      .setPreference(CompactControlReadability, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(PreserveSpaceBeforeArguments, true)
      .setPreference(SpaceBeforeColon, false)
      .setPreference(SpaceInsideBrackets, false)
      .setPreference(SpaceInsideParentheses, false)
      .setPreference(SpacesWithinPatternBinders, true)
      .setPreference(FormatXml, true)
  )

  object Plugins extends Build {
    lazy val root = Project("root", file(".")) dependsOn
      uri("git://github.com/sbt/sbt-assembly.git#0.14.0")
  }

}