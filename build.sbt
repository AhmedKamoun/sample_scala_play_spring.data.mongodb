import play.PlayScala

Common.appSettings

lazy val dom = (project in file("modules/dom")).enablePlugins(PlayScala)

lazy val dto = (project in file("modules/dto")).enablePlugins(PlayScala).dependsOn(dom)

lazy val dal = (project in file("modules/dal")).enablePlugins(PlayScala).dependsOn(dom, dto)

lazy val bl = (project in file("modules/bl")).enablePlugins(PlayScala).dependsOn(dom, dto, dal)

lazy val admin = (project in file("modules/admin")).enablePlugins(PlayScala).dependsOn(dom, dal, dto, bl)

lazy val root = (project in file(".")).enablePlugins(PlayScala).aggregate(dom, dal, bl, dto, admin).dependsOn(dom, dal, bl, dto, admin)

libraryDependencies ++= Common.commonDependencies

