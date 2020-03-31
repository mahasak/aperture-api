import com.typesafe.config.ConfigFactory
import com.typesafe.sbt.packager.docker.Cmd
import com.typesafe.sbt.packager.docker.DockerChmodType

name := """aperture_api"""
organization := "io.bigbears"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

//Universal packager
maintainer := "Mahasak Pijittum <mahasak@gmail.com>"
packageName in Universal := "app"
executableScriptName := "api"

val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()

mappings in Universal := PackageFileMappings((mappings in Universal).value)
  .mappings

lazy val dockerSettings = Seq(
  packageName in Docker := conf.getString("docker.package.name"),

  dockerCommands := Seq(
    Cmd("FROM", "openjdk:8-jre-alpine"),
    Cmd("LABEL", s"""maintainer="${conf.getString("docker.maintainer")}""""),
    Cmd("RUN", "apk --no-cache add bash"),
    Cmd("WORKDIR", "/opt/docker"),
    Cmd("ADD", "--chown=daemon:daemon opt /opt"),
    Cmd("USER", "daemon"),
    Cmd("ENTRYPOINT", s"""["/opt/docker/bin/${conf.getString("app.name")}"]"""),
    Cmd("CMD", """[]""")
  )
)
dockerChmodType := DockerChmodType.UserGroupWriteExecute
dockerExposedPorts ++= Seq(9000)
