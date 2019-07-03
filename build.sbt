import Dependencies.dependencies
import Dependencies.crossVersion

lazy val commonSettings = Seq(
  name := "strace",
  organization := "io.github.wtog.strace",
  version := "1.0.0-SNAPSHOT",
  scalaVersion := crossVersion.head
)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

crossScalaVersions := crossVersion

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(libraryDependencies ++= dependencies)
  .settings(
    addCompilerPlugin(scalafixSemanticdb),
    scalacOptions ++= List("-Yrangepos", "-Ywarn-unused-import")
  )

