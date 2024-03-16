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
