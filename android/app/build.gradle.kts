import groovy.lang.GroovyObject
import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.reader(Charsets.UTF_8).use { reader ->
        localProperties.load(reader)
    }
}

val flutterRoot = localProperties.getProperty("flutter.sdk")
    ?: throw GradleException("Flutter SDK not found. Define location with flutter.sdk in the local.properties file.")

val flutterVersionCode = localProperties.getProperty("flutter.versionCode") ?: "1"
val flutterVersionName = localProperties.getProperty("flutter.versionName") ?: "1.0"
val kotlin_version: String by rootProject.extra

apply(plugin = "com.android.application")
apply(plugin = "kotlin-android")
apply(from = "$flutterRoot/packages/flutter_tools/gradle/flutter.gradle")

val flutter = extensions.getByName("flutter") as GroovyObject

android {
    compileSdkVersion(flutter.getProperty("compileSdkVersion") as Int)
    ndkVersion = flutter.getProperty("ndkVersion") as String

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    defaultConfig {
        applicationId = "com.example.hikepal"
        minSdkVersion(flutter.getProperty("minSdkVersion") as Int)
        targetSdkVersion(flutter.getProperty("targetSdkVersion") as Int)
        versionCode = flutterVersionCode.toInt()
        versionName = flutterVersionName
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

flutter.setProperty("source", "../..")

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
}
