plugins {
    id("org.jetbrains.kotlin.plugin.allopen")
    kotlin("jvm")
    application
}

group = "me.krzyjan.documentmgr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.paging.common)
    implementation("javax.inject:javax.inject:1")
    testImplementation(testLibs.kotlin.test)
    testImplementation(testLibs.h2)
    testImplementation(testLibs.junit.jupiter.api)
    testImplementation(testLibs.junit.jupiter.engine)
    testImplementation(testLibs.kotlinx.coroutines.test.jvm)
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}

defaultTasks("build")

tasks.test {
    useJUnitPlatform()
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

application {
    mainClass.set("my.krzyjan.documentmgr.model.MainKt")
}
