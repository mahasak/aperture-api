import java.io.File

case class PackageFileMappings(mappings: Seq[(File, String)]) {
  private val ConfigDir = "conf/"

  private def ShareConfigFiles = Seq(
    "routes"
    , "application.conf"
  )

  def excludeConfigFiles: PackageFileMappings = {
    copy(mappings = mappings.filterNot { case (_, file) => file.contains(ConfigDir) })
  }

  def withShareConfig: PackageFileMappings = {
    copy(mappings = mappings ++ ShareConfigFiles.map(x => ConfigDir + x).map(name => (new File(name), name)))
  }

  def withFile(name: String): PackageFileMappings = {
    val path = ConfigDir + name
    copy(mappings = mappings :+ (new File(path), path))
  }

  def withConfigFor(name: String): PackageFileMappings = {
    val files = Seq(".conf", ".xml")
      .map(ConfigDir + name + _)
      .map(name => (new File(name), name))
    copy(mappings = mappings ++ files)
  }
}
