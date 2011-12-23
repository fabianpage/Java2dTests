organization := "drasher"

name := "slick2dTest"

version := "0.1"

seq(Revolver.settings: _*)

seq(slickSettings: _*)

slick.version := "274"

scalaVersion := "2.9.1"

resolvers += "Diablo3D" at "http://adterrasperaspera.com/lwjgl"

libraryDependencies += "org.lwjgl" % "lwjgl" % "2.7.1"