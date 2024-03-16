rootProject.name = "NewDocumentManager"

include("Backend", "UserInterface", "Runner")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    val kotlinVersion: String by settings
    val springVersion: String by settings
    val composeVersion: String by settings
    val springPluginVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
        id("org.springframework.boot") version springVersion
        id("org.jetbrains.compose") version composeVersion
        kotlin("plugin.spring") version springPluginVersion
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("testLibs") {
            version("kotlin", "1.8.0")
            version("kotlinx-coroutines", "1.7.3")
            version("jupiter", "5.9.2")
            version("springframework", "3.1.0")
            version("h2", "2.1.214")

            library("junit-jupiter-api", "org.junit.jupiter", "junit-jupiter-api").versionRef("jupiter")
            library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("jupiter")
            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").versionRef("kotlin")
            library("kotlinx-coroutines-test-jvm", "org.jetbrains.kotlinx", "kotlinx-coroutines-test-jvm").versionRef("kotlinx-coroutines")
            library("h2", "com.h2database", "h2").versionRef("h2")
        }
    }
}
