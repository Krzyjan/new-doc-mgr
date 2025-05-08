import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinAllOpen)
    `java-test-fixtures`
}

group = "me.krzyjan.documentmgr"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)

    // Create configurations for debug and release variants
    val debugImplementation = configurations.create("debugImplementation")
    val releaseImplementation = configurations.create("releaseImplementation")

    // Create build types
    val debug = "debug"
    val release = "release"

    // Define source sets for debug and release
    val debugSourceSet = sourceSets.create(debug) {
        kotlin.srcDir("src/debug/kotlin")
    }
    val releaseSourceSet = sourceSets.create(release) {
        kotlin.srcDir("src/release/kotlin")
    }
    //This code will add the compile tasks
    this.target.compilations.create(debug)
    this.target.compilations.create(release)

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
    sourceSets.create("commonMain") {
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

    // Add dependencies to the debug and release configurations
    dependencies {
        debugImplementation(project(":model"))
        releaseImplementation(project(":model"))
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

    // Correctly get the debug classes directory
    fun getDebugClassesDir(): File {
        val compileTask =
            tasks.named("compile${debugSourceSet.name.replaceFirstChar { it.uppercase() }}Kotlin")
                .get() as KotlinCompile
        return compileTask.destinationDirectory.get().asFile
    }

    // Correctly get the release classes directory
    fun getReleaseClassesDir(): File {
        val compileTask =
            tasks.named("compile${releaseSourceSet.name.replaceFirstChar { it.uppercase() }}Kotlin")
                .get() as KotlinCompile
        return compileTask.destinationDirectory.get().asFile
    }

    // Create debug and release tasks
    tasks.register<Jar>("jarDebug") {
        archiveClassifier.set(debug)
        // Use the correct classes directory
        from(getDebugClassesDir())
        // Make the Jar task depend on the compilation task
        dependsOn(tasks.named("compile${debugSourceSet.name.replaceFirstChar { it.uppercase() }}Kotlin"))
    }

    tasks.register<Jar>("jarRelease") {
        archiveClassifier.set(release)
        // Use the correct classes directory
        from(getReleaseClassesDir())
        // Make the Jar task depend on the compilation task
        dependsOn(tasks.named("compile${releaseSourceSet.name.replaceFirstChar { it.uppercase() }}Kotlin"))
    }

    // Add the debug and release variants to the corresponding configurations
    artifacts {
        add(debugImplementation.name, tasks.named("jarDebug"))
        add(releaseImplementation.name, tasks.named("jarRelease"))
    }

    // Configure the test task to run with the debug and release variants
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

configure<org.jetbrains.kotlin.allopen.gradle.AllOpenExtension> {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
