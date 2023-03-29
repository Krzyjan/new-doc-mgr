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

val kotlinVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.6")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.6.6")
    implementation("org.hibernate.orm:hibernate-core:6.0.0.Final")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("com.h2database:h2:2.1.212")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.6")
    testImplementation("org.hibernate:hibernate-testing:6.0.0.Final")
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