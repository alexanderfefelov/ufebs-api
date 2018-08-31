name := "ufebs-api"
organization := "com.github.alexanderfefelov"

crossScalaVersions := Seq("2.11.12", "2.12.6")

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.1.0",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1",
  "org.dispatchhttp" %% "dispatch-core" % "0.14.0"
)

scalaxbPackageName in(Compile, scalaxb) := "com.github.alexanderfefelov.ufebs.api"

lazy val root = (project in file("."))
  .enablePlugins(ScalaxbPlugin)

doc in Compile := target.map(_ / "none").value
