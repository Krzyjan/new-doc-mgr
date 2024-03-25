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
        kotlin("jvm") version "1.9.22"
        id("org.jetbrains.kotlin.plugin.allopen") version "1.9.22"
        id("org.springframework.boot") version "3.1.0"
        id("org.jetbrains.compose") version "1.6.2"
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
