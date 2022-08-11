
rootProject.name = "NewDocumentManager"

include("Backend", "UserInterface","Runner")

pluginManagement {
    val kotlinVersion: String by settings
    val springVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
        id("org.springframework.boot") version springVersion
    }
}

