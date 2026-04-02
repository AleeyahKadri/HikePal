import java.util.Properties

include(":app")

val localPropertiesFile = File(rootProject.projectDir, "local.properties")
val properties = Properties()

check(localPropertiesFile.exists())
localPropertiesFile.reader(Charsets.UTF_8).use { reader -> properties.load(reader) }

val flutterSdkPath = properties.getProperty("flutter.sdk")
check(flutterSdkPath != null) { "flutter.sdk not set in local.properties" }

apply(from = "$flutterSdkPath/packages/flutter_tools/gradle/app_plugin_loader.gradle")
