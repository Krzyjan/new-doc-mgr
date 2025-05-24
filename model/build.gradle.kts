plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinAllOpen)
    `java-test-fixtures`
}

group = "me.krzyjan.documentmgr"
version = "1.0-SNAPSHOT"

val buildVariantAttributeName = "my.krzyjan.buildVariant"

dependencies {
    attributesSchema {
        attribute(Attribute.of(buildVariantAttributeName, String::class.java))
    }
}

// Create configurations for debug and release variants

val debugConfiguration: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
    extendsFrom(configurations.implementation.get())
    description = "Debug implementation of this project"
}

val releaseConfiguration: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
    extendsFrom(configurations.implementation.get())
    description = "Release implementation of this project"
}

val debugSourceSet = "debugSourceSet"
val releaseSourceSet = "releaseSourceSet"
sourceSets {
    create(debugSourceSet) {
        kotlin.srcDirs("src/debug/kotlin")
        resources.srcDirs("src/debug/resources")

        // connect configuration to this source set
        compileClasspath += debugConfiguration
        runtimeClasspath += debugConfiguration

        // Also use the code from "main"
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
    create(releaseSourceSet) {
        kotlin.srcDir("src/release/kotlin")
        resources.srcDirs("src/release/resources")

        // connect configuration to this source set
        compileClasspath += releaseConfiguration
        runtimeClasspath += releaseConfiguration

        // Also use the code from "main"
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

// Fetch already generated Kotlin compile tasks for the variants
val compileDebugSourceSetKotlin by tasks
val compileReleaseSourceSetKotlin by tasks

// Task for debug variant
val debugJar by tasks.registering(Jar::class) {
    dependsOn(compileDebugSourceSetKotlin)
    archiveClassifier.set("debug")
    from(sourceSets[debugSourceSet].output)
    from(sourceSets.main.get().output)
}

// Task for release variant
val releaseJar by tasks.registering(Jar::class) {
    dependsOn(compileReleaseSourceSetKotlin)
    archiveClassifier.set("release")
    from(sourceSets[releaseSourceSet].output)
    from(sourceSets.main.get().output)
}

// Configure the standard 'jar' task to be an alias for the release
tasks.named<Jar>("jar") {
    dependsOn(releaseJar)

    enabled = false // make sure it does not actually run
}

tasks.named("build").configure {
    dependsOn.clear() // Remove any default dependencies
    dependsOn(debugJar, releaseJar)
}

kotlin {
    jvmToolchain(21)

    sourceSets.getByName("main") {
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

    //Rename test to jvmTest
    sourceSets.getByName("test") {
        dependencies {
            implementation(libs.kotlinx.coroutines.test.jvm)
            implementation(libs.hoplite.core)
            implementation(libs.hoplite.hocon)
            implementation(libs.kotlin.test)
            implementation(libs.junit.jupiter.api)
            implementation(libs.junit.jupiter.engine)
        }
    }

    // Configure the test task to run with the debug and release variants
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

configurations {
    val runtimeElements by getting

    create("debugElements") {
        isCanBeConsumed = true
        isCanBeResolved = false
        attributes {
            // Standard usage attribute
            attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
            attribute(Attribute.of("org.jetbrains.kotlin.platform.type", String::class.java), "jvm")

            // Custom build variant attribute
            attribute(Attribute.of(buildVariantAttributeName, String::class.java), "debug")
        }
        extendsFrom(configurations.implementation.get())
        outgoing.artifact(tasks.named("debugJar"))
        description = "Consumable debug implementation of this project"
    }

    create("releaseElements") {
        isCanBeConsumed = true
        isCanBeResolved = false
        attributes {
            attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
            attribute(Attribute.of("org.jetbrains.kotlin.platform.type", String::class.java), "jvm")
            attribute(Attribute.of(buildVariantAttributeName, String::class.java), "release")
        }
        extendsFrom(configurations.implementation.get())
        outgoing.artifact(tasks.named("releaseJar"))
        description = "Consumable release implementation of this project"
    }
}

configure<org.jetbrains.kotlin.allopen.gradle.AllOpenExtension> {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

