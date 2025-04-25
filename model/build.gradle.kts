plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinAllOpen)
    application
    `java-test-fixtures`
}

group = "me.krzyjan.documentmgr"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)

    sourceSets {
        val main by getting {
            dependencies {
                implementation(libs.kotlin.reflect)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.exposed.core)
                implementation(libs.exposed.dao)
                implementation(libs.exposed.jdbc)
                implementation(libs.paging.common)
                implementation(libs.java.inject)
                implementation(libs.h2)
            }
        }

        val test by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.junit.jupiter.api)
                implementation(libs.junit.jupiter.engine)
                implementation(libs.kotlinx.coroutines.test.jvm)
                implementation(libs.hoplite.core)
                implementation(libs.hoplite.hocon)
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

configure<org.jetbrains.kotlin.allopen.gradle.AllOpenExtension> {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

application {
    mainClass.set("my.krzyjan.documentmgr.model.MainKt")
}
