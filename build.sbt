name := "reactive-tweet"

scalaVersion := "2.11.8"

val `reactive-tweet` =
  (project in file("."))
    .enablePlugins(PlayScala)

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

libraryDependencies += "com.ning" % "async-http-client" % "1.9.29"