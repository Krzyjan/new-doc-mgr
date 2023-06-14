
rootProject.name = "NewDocumentManager"

include("Backend", "UserInterface","Runner")

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
            version("kodein", "7.18.0")
            version("springframework", "3.1.0")
            version("hibernate", "6.2.4.Final")
            library("kodein-di", "org.kodein.di", "kodein-di").versionRef("kodein")
            library("spring-boot-starter-data-jpa", "org.springframework.boot", "spring-boot-starter-data-jpa").versionRef("springframework")
            library("spring-boot-gradle-plugin", "org.springframework.boot", "spring-boot-gradle-plugin").versionRef("springframework")
            library("hibernate-core", "org.hibernate.orm", "hibernate-core").versionRef("hibernate")
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")
        }
        create("testLibs") {
            version("kotlin", "1.8.0")
            version("jupiter", "5.9.2")
            version("springframework", "3.1.0")
            version("hibernate", "6.2.4.Final")
            version("h2", "2.1.214")
            library("junit-jupiter-api", "org.junit.jupiter", "junit-jupiter-api").versionRef("jupiter")
            library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("jupiter")
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").versionRef("springframework")
            library("hibernate-testing", "org.hibernate", "hibernate-testing").versionRef("hibernate")
            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").versionRef("kotlin")
            library("h2", "com.h2database", "h2").versionRef("h2")
        }
    }
}
