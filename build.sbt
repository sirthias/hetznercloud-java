// basic
name := "hetznercloud-java"
version := "1.1.2a"
organization := "com.decodified"
scalaVersion := "2.12.4"
homepage := Some(url("https://github.com/TomSDEVSN/hetznercloud-java"))
description := "Java Integration to manage the Hetzner-Cloud "
startYear := Some(2018)
licenses := Seq("MIT" -> new URL("https://www.mozilla.org/en-US/MPL/2.0/"))
scmInfo := Some(ScmInfo(url("https://github.com/TomSDEVSN/hetznercloud-java"), "scm:git:git@github.com:TomSDEVSN/hetznercloud-java.git"))
developers := List(Developer(id = "TomSDEVSN", name = "Tom Siewert", email = "", url = url("http://github.com/TomSDEVSN")))
javacOptions ++= Seq("-encoding", "UTF-8")
libraryDependencies ++= Seq(
  "org.springframework"        % "spring-web"       % "5.0.3.RELEASE",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.0",
  "org.projectlombok"          % "lombok"           % "1.16.20"
)

// publishing
useGpg := true
publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false }
publishTo := sonatypePublishTo.value
crossPaths := false