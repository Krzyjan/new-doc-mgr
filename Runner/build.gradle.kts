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

val kodeinVersion: String by project

dependencies {
    implementation(project(":UserInterface"))
    api("org.kodein.di:kodein-di:$kodeinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("my.krzyjan.documentmgr.MainKt")
}
