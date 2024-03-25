plugins {
    id("conventions")
    kotlin("jvm") version libs.versions.kotlin
    application
}

group = "me.krzyjan.documentmgr"
version = "unspecified"

kotlin {
    dependencies {
        implementation(project(":composeApp"))
        api(libs.kodein.di)
        implementation(project(mapOf("path" to ":Backend")))
        testImplementation(libs.junit.jupiter.api)
        testRuntimeOnly(libs.junit.jupiter.engine)
    }
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("my.krzyjan.documentmgr.MainKt")
}
