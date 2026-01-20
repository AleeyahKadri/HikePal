include(":app")

val localPropertiesFile = File(rootProject.projectDir, "local.properties")
val properties = java.util.Properties()

if (localPropertiesFile.exists()) {
    localPropertiesFile.reader(Charsets.UTF_8).use { properties.load(it) }
    
    val flutterSdkPath = properties.getProperty("flutter.sdk")
    if (flutterSdkPath != null) {
        apply(from = "$flutterSdkPath/packages/flutter_tools/gradle/app_plugin_loader.gradle")
    }
}
