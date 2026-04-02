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

// Flutter Gradle extensions are exposed from Groovy scripts, so Groovy interop is required here.
val flutter = extensions.getByName("flutter") as GroovyObject
val flutterCompileSdkVersion = flutter.getProperty("compileSdkVersion") as? Int
    ?: throw GradleException("flutter.compileSdkVersion is missing or not an Int")
val flutterNdkVersion = flutter.getProperty("ndkVersion") as? String
    ?: throw GradleException("flutter.ndkVersion is missing or not a String")
val flutterMinSdkVersion = flutter.getProperty("minSdkVersion") as? Int
    ?: throw GradleException("flutter.minSdkVersion is missing or not an Int")
val flutterTargetSdkVersion = flutter.getProperty("targetSdkVersion") as? Int
    ?: throw GradleException("flutter.targetSdkVersion is missing or not an Int")

android {
    compileSdkVersion(flutterCompileSdkVersion)
    ndkVersion = flutterNdkVersion

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
        minSdkVersion(flutterMinSdkVersion)
        targetSdkVersion(flutterTargetSdkVersion)
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
