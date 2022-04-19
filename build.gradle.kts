import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.20"
    kotlin("jvm") version "1.6.20"
    application
    kotlin("plugin.spring") version "1.3.70"
}

group = "me.krzyjan.documentmgr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.6")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.6.6")
    implementation("org.hibernate.orm:hibernate-core:6.0.0.Final")
    testImplementation(kotlin("test"))
    testImplementation("com.h2database:h2:2.1.212")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.6")
    testImplementation("org.hibernate:hibernate-testing:6.0.0.Final")
}


tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

application {
    mainClass.set("MainKt")
}