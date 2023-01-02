plugins {
    id("conventions")
    kotlin("jvm")
    application
}

group = "me.krzyjan.documentmgr"
version = "unspecified"

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.UsesKotlinJavaToolchain>().configureEach {
    kotlinJavaToolchain.jdk.use(
        "F:\\Applications\\Java\\jdk11.0.8_10", // Put a path to your JDK
        JavaVersion.VERSION_11
    )
}

val doodleVersion: String by project
val kodeinVersion: String by project

dependencies {
    implementation(project(":UserInterface"))
    implementation("io.nacular.doodle:desktop-jvm-${targetSuffix()}:$doodleVersion")
    api("org.kodein.di:kodein-di:$kodeinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("my.krzyjan.documentmgr.MainKt")
}
