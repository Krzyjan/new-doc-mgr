import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

group = "my.krzyjan.documentmgr"
version = "1.0-SNAPSHOT"

val buildType = project.findProperty("buildType") as? String ?: "debug"

kotlin {
    androidTarget()

    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.exposed.core)
                implementation(libs.kodein.di.framework.compose)
                implementation(libs.hoplite.core)
                implementation(libs.hoplite.hocon)
                implementation(libs.androidx.material3.android)
                implementation(libs.material.icons.extended)
                implementation(project(":model")) {
                    val consumerBuildType = project.findProperty("consumerBuildType")?.toString()
                        ?: "release" // Default to release
                    attributes {
                        attribute(
                            Attribute.of("my.krzyjan.buildVariant", String::class.java),
                            consumerBuildType
                        )
                    }
                }
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "my.krzyjan.documentmgr"
    compileSdk = 35

    sourceSets {
        getByName("main").manifest.srcFile("src/androidMain/AndroidManifest.xml")
        getByName("main").res.srcDirs("src/androidMain/res")
    }

    defaultConfig {
        applicationId = "my.krzyjan.documentmgr"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = buildType == "release"
            buildConfigField("Boolean", "IS_DEBUG", "false")
        }
        getByName("debug") {
            buildConfigField("Boolean", "IS_DEBUG", "true")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        buildConfig = true
    }
}

compose.desktop {
    application {
        mainClass = "my.krzyjan.documentmgr.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "my.krzyjan.documentmgr"
            packageVersion = "1.0.0"
        }
    }
}
