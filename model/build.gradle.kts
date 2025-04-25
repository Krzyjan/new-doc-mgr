
plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinAllOpen)
    application
}

group = "me.krzyjan.documentmgr"
version = "1.0-SNAPSHOT"

kotlin {
    dependencies {
        implementation(libs.kotlin.reflect)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.exposed.core)
        implementation(libs.exposed.dao)
        implementation(libs.exposed.jdbc)
        implementation(libs.paging.common)
        implementation(libs.java.inject)
        testImplementation(libs.kotlin.test)
        implementation(libs.h2)
        testImplementation(libs.junit.jupiter.api)
        testImplementation(libs.junit.jupiter.engine)
        testImplementation(libs.kotlinx.coroutines.test.jvm)
        runtimeOnly(libs.kotlinx.coroutines.android)
        testImplementation(libs.hoplite.core)
        testImplementation(libs.hoplite.hocon)
    }
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
