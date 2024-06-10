name := """expense_tracker"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.4.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
libraryDependencies ++= Seq(
  "org.xerial" % "sqlite-jdbc" % "3.39.2.0",
  "org.playframework" %% "play-slick" % "6.1.0",
  "org.playframework" %% "play-slick-evolutions" % "6.1.0",
  "org.playframework.anorm" %% "anorm" % "2.7.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.5.1",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.19",
  "com.typesafe.akka" %% "akka-slf4j" % "2.6.19",
  "com.typesafe.play" %% "play-akka-http-server" % "2.9.3",
  "com.typesafe.play" %% "play-json" % "2.10.5"
)

lazy val slick = taskKey[Seq[File]]("Generate Tables.scala")
slick := {
  val dir = (Compile / sourceManaged).value
  val outputDir = dir / "app"
  val url = "jdbc:sqlite:./database/ExpenseTrackerDB.db" // connection info
  val jdbcDriver = "org.sqlite.JDBC"
  val slickDriver = "slick.jdbc.SQLiteProfile"
  val pkg = "models"

  val cp = (Compile / dependencyClasspath).value
  val s = streams.value

  runner.value.run("slick.codegen.SourceCodeGenerator",
    cp.files,
    Array(slickDriver, jdbcDriver, url, outputDir.getPath, pkg),
    s.log).failed foreach (sys error _.getMessage)

  val file = outputDir / pkg / "Tables.scala"

  Seq(file)
}



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
