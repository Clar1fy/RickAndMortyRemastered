plugins {

    // Application
    id(libs.plugins.agp.application.get().pluginId)

    // Kotlin
    id("kotlin-android")

    // Kapt
    id("kotlin-kapt")

    // Navigation SafeArgs
    id(libs.plugins.navigation.safeArgs.get().pluginId)

    // Apollo
    id(libs.plugins.apollo.apollo.get().pluginId)
}


android {
    compileSdk = config.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.timplifier.rickandmortyremastered"
        minSdk = config.versions.minSdk.get().toInt()
        targetSdk = config.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(config.versions.releaseBuildType.get()) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName(config.versions.debugBuildType.get()) {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = options.versions.kotlinJvmTargetOptions.get()
    }

    //ViewBinding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":data"))

    // UI Components
    implementation(libs.bundles.uiComponents)

    // Core
    implementation(libs.android.core)

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.bundles.navigation)

    // Dagger
    implementation(libs.dagger.dagger)
    kapt(libs.dagger.compiler)

    // Room
    kapt(libs.room.compiler)
}

apollo {
    packageName.set("com.timplifier.rickandmortyremastered")
    generateKotlinModels.set(true)
}