import sbt._

/**
  * @author : tong.wang
  * @since : 2018-12-08 00:25
  * @version : 1.0.0
  */
object Dependencies {

  object Versions {
    lazy val log4j2 = "2.11.0"
  }

  import Versions._

  lazy val log = Seq(
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4j2 % Provided,
    "org.apache.logging.log4j" % "log4j-api" % log4j2 % Provided,
    "org.apache.logging.log4j" % "log4j-core" % log4j2 % Provided)

  lazy val typesafe = Seq(
    "com.typesafe" % "config" % "1.3.3" % Provided
  )
 
  lazy val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.1" % Provided  
  )
  
  lazy val test = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0" % Test,
    "org.scalatest" %% "scalatest" % "3.0.8" % Test)

  val crossVersion = Seq("2.12.8", "2.11.12")

  lazy val dependencies = (log ++ test ++ typesafe ++ akka)
}
