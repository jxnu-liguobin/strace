import sbt.Keys._
import sbt._
import com.typesafe.sbt.SbtPgp.autoImportImpl._

/**
 * @author : tong.wang
 * @since : 2018-12-20 22:00
 * @version : 1.0.0
 */
object Publish extends AutoPlugin {
  override def trigger: PluginTrigger = allRequirements

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    useGpg := false,
    usePgpKeyHex("124EC2FFE16C56ACF83A311C7A0A07E2FDA7A346"),
    pgpPublicRing := baseDirectory.value / "project" / ".gnupg" / "pubring.gpg",
    pgpSecretRing := baseDirectory.value / "project" / ".gnupg" / "secring.gpg",

    credentials += Credentials(Path.userHome / ".sbt" / ".credentials-center"),
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2/")
    },

    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ ⇒ false },

    pomExtra in Global := {
      <url>https://github.com/wtog/strace</url>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:wtog/strace.git</url>
        <connection>scm:git:git@github.com:wtog/strace.git</connection>
      </scm>
      <developers>
        <developer>
          <id>wangtong</id>
          <name>wangtong</name>
          <url>https://github.com/wtog/</url>
        </developer>
      </developers>
    })
}
