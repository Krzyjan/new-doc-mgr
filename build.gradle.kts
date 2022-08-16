plugins {
    kotlin("jvm") version "1.7.10" apply false
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.UsesKotlinJavaToolchain>().configureEach {
    kotlinJavaToolchain.jdk.use(
        "F:\\Applications\\Java\\jdk11.0.8_10", // Put a path to your JDK
        JavaVersion.VERSION_11
    )
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}


