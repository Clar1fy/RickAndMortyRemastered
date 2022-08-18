pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()

        maven { url = uri("https://jitpack.io") }
    }
}


enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("config") {
            from(files("gradle/config.versions.toml"))
        }
        create("options") {
            from(files("gradle/options.versions.toml"))
        }

    }
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven("timplifier")
        maven { url = uri("https://jitpack.io") }
    }
}
rootProject.name = "Boilerplate"
include(":app", ":domain", ":data")