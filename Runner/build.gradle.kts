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

dependencies {
    implementation(project(":UserInterface"))
    api(libs.kodein.di)
    implementation(project(mapOf("path" to ":Backend")))
    testImplementation(testLibs.junit.jupiter.api)
    testRuntimeOnly(testLibs.junit.jupiter.engine)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("my.krzyjan.documentmgr.MainKt")
}
