val localProperties = java.util.Properties()
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

plugins {
    id("com.android.application")
    id("kotlin-android")
}

apply(from = "$flutterRoot/packages/flutter_tools/gradle/flutter.gradle")

// Access flutter properties through project.extensions
val flutterCompileSdkVersion: Int by lazy {
    project.extensions.getByName<groovy.lang.GroovyObject>("flutter")
        .getProperty("compileSdkVersion") as Int
}

val flutterNdkVersion: String? by lazy {
    project.extensions.getByName<groovy.lang.GroovyObject>("flutter")
        .getProperty("ndkVersion") as String?
}

val flutterMinSdkVersion: Int by lazy {
    project.extensions.getByName<groovy.lang.GroovyObject>("flutter")
        .getProperty("minSdkVersion") as Int
}

val flutterTargetSdkVersion: Int by lazy {
    project.extensions.getByName<groovy.lang.GroovyObject>("flutter")
        .getProperty("targetSdkVersion") as Int
}

android {
    compileSdk = flutterCompileSdkVersion
    ndkVersion = flutterNdkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
        }
    }

    defaultConfig {
        // TODO: Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html).
        applicationId = "com.example.hikepal"
        // You can update the following values to match your application needs.
        // For more information, see: https://docs.flutter.dev/deployment/android#reviewing-the-build-configuration.
        minSdk = flutterMinSdkVersion
        targetSdk = flutterTargetSdkVersion
        versionCode = flutterVersionCode.toInt()
        versionName = flutterVersionName
    }

    buildTypes {
        getByName("release") {
            // TODO: Add your own signing config for the release build.
            // Signing with the debug keys for now, so `flutter run --release` works.
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10")
}
