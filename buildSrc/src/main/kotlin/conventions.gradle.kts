plugins {
    `java-library`
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

group = "me.krzyjan.documentmgr"
