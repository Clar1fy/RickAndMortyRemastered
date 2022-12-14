plugins {

    // Application
    id(libs.plugins.agp.library.get().pluginId)

    // Kotlin
    id("kotlin-android")

    // Kapt
    id("kotlin-kapt")

    // Apollo
    id(libs.plugins.apollo.apollo.get().pluginId)
}


android {
    compileSdk = config.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = config.versions.minSdk.get().toInt()
        targetSdk = config.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(config.versions.releaseBuildType.get()) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/\"")
        }

        getByName(config.versions.debugBuildType.get()) {
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = options.versions.kotlinJvmTargetOptions.get()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    api(project(":domain"))

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Apollo
    api(libs.bundles.apollo)

    // OkHttp
    implementation(libs.bundles.okHttp)

    // Room
    api(libs.bundles.room)
    kapt(libs.room.compiler)

    // Paging 3
    api(libs.paging.paging)
}

apollo {
    packageName.set("com.timplifier.rickandmortyremastered")
    generateKotlinModels.set(true)
}