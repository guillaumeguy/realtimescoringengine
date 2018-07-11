name := """realtimescoringengine"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += jdbc
libraryDependencies += cache
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
libraryDependencies += "org.jpmml" % "pmml-evaluator" % "1.3.5"

/***
  */
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
 "com.typesafe.play" %% "anorm" % "2.5.0"

)

val appDependencies = Seq(
  // Add your project dependencies here,
  "mysql" % "mysql-connector-java" % "5.1.36"

)
