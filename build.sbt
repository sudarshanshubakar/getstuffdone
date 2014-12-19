name := """getstuffdone"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
    .enablePlugins(PlayJava)


scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  filters
)

libraryDependencies += "com.google.api-client" % "google-api-client" % "1.19.0"

libraryDependencies += "com.google.apis" % "google-api-services-plus" % "v1-rev202-1.19.0"

libraryDependencies += "org.mongodb.morphia" % "morphia" % "0.108"

libraryDependencies += "com.google.inject" % "guice" % "4.0-beta5"

