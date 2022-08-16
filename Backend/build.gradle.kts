plugins {
    id("org.jetbrains.kotlin.plugin.allopen")
    kotlin("jvm")
    kotlin("plugin.spring") version "1.3.70"
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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.UsesKotlinJavaToolchain>().configureEach {
    kotlinJavaToolchain.jdk.use(
        "F:\\Applications\\Java\\jdk11.0.8_10", // Put a path to your JDK
        JavaVersion.VERSION_11
    )
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

application {
    mainClass.set("MainKt")
}