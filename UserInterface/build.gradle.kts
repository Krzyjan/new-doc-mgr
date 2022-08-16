plugins {
    id("conventions")
    kotlin("jvm")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}


val doodleVersion: String by project

dependencies {
    implementation("io.nacular.doodle:core:$doodleVersion")
    implementation("io.nacular.doodle:desktop-jvm-${targetSuffix()}:$doodleVersion")
    implementation("io.nacular.doodle:themes:$doodleVersion")
    implementation("io.nacular.doodle:controls:$doodleVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.UsesKotlinJavaToolchain>().configureEach {
    kotlinJavaToolchain.jdk.use(
        "F:\\Applications\\Java\\jdk11.0.8_10", // Put a path to your JDK
        JavaVersion.VERSION_11
    )
}

