name := "tinder-bandits"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
  "mysql" % "mysql-connector-java" % "5.1.26",
  "net.liftweb" %% "lift-json" % "2.5+",
  "joda-time" % "joda-time" % "2.4+"
)
