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
        create("libs") {
            version("kotlin", "1.8.0")
            version("kotlinx-coroutines", "1.7.3")
            version("kodein", "7.18.0")
            version("springframework", "3.1.0")
            version("exposed", "0.41.1")
            version("paging", "3.2.1")

            library("kodein-di", "org.kodein.di", "kodein-di").versionRef("kodein")
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")
            library("kotlinx-coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("kotlinx-coroutines")
            library("exposed-core", "org.jetbrains.exposed", "exposed-core").versionRef("exposed")
            library("exposed-dao", "org.jetbrains.exposed", "exposed-dao").versionRef("exposed")
            library("exposed-jdbc", "org.jetbrains.exposed", "exposed-jdbc").versionRef("exposed")
            library("paging-runtime", "androidx.paging", "paging-runtime").versionRef("paging")
            library("paging-common", "androidx.paging", "paging-common").versionRef("paging")
        }
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
