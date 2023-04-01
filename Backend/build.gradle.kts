plugins {
    id("org.jetbrains.kotlin.plugin.allopen")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    application
}

group = "me.krzyjan.documentmgr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.gradle.plugin)
    implementation(libs.hibernate.core)
    testImplementation(testLibs.kotlin.test)
    testImplementation(testLibs.h2)
    testImplementation(testLibs.spring.boot.starter.test)
    testImplementation(testLibs.hibernate.testing)
}

defaultTasks("build")

tasks.test {
    useJUnit()
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

application {
    mainClass.set("my.krzyjan.documentmgr.model.MainKt")
}