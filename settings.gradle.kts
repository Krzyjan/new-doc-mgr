rootProject.name = "NewDocumentManager"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":Backend", ":composeApp")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        id("org.springframework.boot") version "3.1.0"
        id("org.jetbrains.compose") version "1.7.3"
        kotlin("plugin.spring") version "3.1.0"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
