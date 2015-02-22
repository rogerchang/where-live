name := """codefest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.scalatestplus" %% "play" % "1.2.0" % "test",
    jdbc,
    anorm,
    cache,
    ws
)

sources in (Compile,doc) := Seq.empty

publishArtifact in (Compile, packageDoc) := false