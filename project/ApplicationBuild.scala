import sbt._
import Keys._

object ApplicationBuild extends Build {

  val sharedSettings = Defaults.defaultSettings ++ Seq(
    resolvers ++= Seq(
      "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
      Resolver.url(
        "Typesafe Ivy Snapshots",
        url("http://repo.typesafe.com/typesafe/ivy-snapshots/"))(Resolver.ivyStylePatterns),
      Resolver.url(
        "Typesafe Snapshots with ivy style",
        url("http://repo.typesafe.com/typesafe/snapshots/"))(Resolver.ivyStylePatterns)
    ),
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "2.0.0",
      "com.typesafe" % "config" % "1.2.0",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "joda-time" % "joda-time" % "2.4+",
      "org.joda" % "joda-convert" % "1.3.1",
      "com.h2database" % "h2" % "1.3.172",
      "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
      "mysql" % "mysql-connector-java" % "5.1.26",
      "net.liftweb" %% "lift-json" % "2.5+",
      "mysql" % "mysql-connector-java" % "5.1.24",
      "c3p0" % "c3p0" % "0.9.1.2",
      "org.scalatest" %% "scalatest" % "2.2.0" % "test"
    )
  )

  lazy val root = Project(
    id = "tinder-bandits",
    base = file("."),
    settings = sharedSettings
  )

}