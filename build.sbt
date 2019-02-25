name := "figaro"

version := "1.0"

scalaVersion := "2.12.6"

// https://mvnrepository.com/artifact/com.cra.figaro/figaro
libraryDependencies += "com.cra.figaro" %% "figaro" % "5.0.0.0"

//Andrzej's sbt
//  name := "Anonymity Examples"
//
//version := "0.0"
//
//scalaVersion := "2.12.6"
//
//scalacOptions += "-deprecation"
//
//scalacOptions += "-feature"
//
//scalacOptions += "-language:implicitConversions"
//
////val scalazVersion = "7.2.20"
//val scalazVersion = "7.2.27"
//val scalacheckVersion = "1.14"
//
//libraryDependencies += "org.scalacheck" %% "scalacheck" % s"$scalacheckVersion.0" % "test"
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
//
//
//libraryDependencies += "org.scalaz" %% "scalaz-core" % scalazVersion
//
//libraryDependencies += "org.scalaz" %% "scalaz-scalacheck-binding" % s"${scalazVersion}-scalacheck-${scalacheckVersion}" % "test"
//
//libraryDependencies += "com.cra.figaro" %% "figaro" % "5.0.0.0"
//
//// initialCommands in console := "import AverageAge._"
//
//initialCommands in console in Test := "import scalaz._, Scalaz._, scalacheck.ScalazProperties._, scalacheck.ScalazArbitrary._,scalacheck.ScalaCheckBinding._"

