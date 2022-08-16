plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.UsesKotlinJavaToolchain>().configureEach {
    kotlinJavaToolchain.jdk.use(
        "F:\\Applications\\Java\\jdk11.0.8_10", // Put a path to your JDK
        JavaVersion.VERSION_11
    )
}
