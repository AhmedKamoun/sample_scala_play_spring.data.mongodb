import sbt.Keys._
import sbt._

object Common {
  // Settings for the app, i.e. the root project
  val appSettings = settings(appName)
  val spring_version = "4.2.4.RELEASE"
  val commonDependencies = Seq(
    // Add your project dependencies here,
    //  <!-- MongoDB database driver -->
    "org.mongodb" % "mongo-java-driver" % "3.2.0",

    "org.springframework" % "spring-context" % spring_version,
    "org.springframework" % "spring-tx" % spring_version,
    "org.springframework" % "spring-test" % spring_version % "test",
    "org.springframework" % "spring-context-support" % spring_version,
    "org.springframework.data" % "spring-data-mongodb" % "1.8.2.RELEASE",
    "org.springframework.data" % "spring-data-commons" % "1.11.2.RELEASE",

    "net.sf.ehcache" % "ehcache" % "2.10.0",
    "joda-time" % "joda-time" % "2.9.1",
    "cglib" % "cglib" % "3.1",
    "c3p0" % "c3p0" % "0.9.1.2",
    "com.jason-goodwin" %% "authentikat-jwt" % "0.4.1"
  )

  def appName = "sample_scala_play_springIoC_spring data mongodb"

  // Settings for every service, i.e. for admin and web subprojects
  def serviceSettings(module: String) = moduleSettings(module) ++: Seq(
    /*
     includeFilter in (Assets, LessKeys.less) := "*.less",
     excludeFilter in (Assets, LessKeys.less) := "_*.less",
     pipelineStages := Seq(rjs, digest, gzip),
     RjsKeys.mainModule := s"main-$module"
     */
  )

  // Settings for every module, i.e. for every subproject
  def moduleSettings(module: String) = settings(module) ++: Seq(
    javaOptions in Test += s"-Dconfig.resource=application.conf"
  )

  // Common settings for every project
  def settings(theName: String) = Seq(
    name := theName,
    organization := "kiminix.org",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.4",
    doc in Compile <<= target.map(_ / "none"),
    scalacOptions in ThisBuild ++= Seq(
      "-target:jvm-1.7",
      "-encoding", "UTF-8",
      "-deprecation", // warning and location for usages of deprecated APIs
      "-feature", // warning and location for usages of features that should be imported explicitly
      "-unchecked", // additional warnings where generated code depends on assumptions
      "-language:reflectiveCalls",
      "-Xlint", // recommended additional warnings
      "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
      "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
      "-Ywarn-inaccessible",
      "-Ywarn-dead-code"
    )
  )
}