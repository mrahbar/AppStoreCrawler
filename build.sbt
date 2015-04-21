name := """AppStoreCrawler"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.apache.commons" % "commons-lang3" % "3.4",
  "org.jsoup" % "jsoup" % "1.8.1"
)
