lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.mzielinski",
      scalaVersion := "2.13.6"
    )),
    name := "advent of code 2021"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
