plugins {
    id("conventions")
    id ("org.jetbrains.kotlin.jvm")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

kotlin {
    val doodleVersion:String by project

    dependencies {
        implementation("io.nacular.doodle:core:$doodleVersion")
        implementation("io.nacular.doodle:desktop-jvm-${targetSuffix()}:$doodleVersion")
        implementation("io.nacular.doodle:themes:$doodleVersion")
        implementation("io.nacular.doodle:controls:$doodleVersion")
    }
}
