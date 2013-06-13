import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "bitcoin-peak"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.quartz-scheduler" % "quartz" % "2.1.7"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
