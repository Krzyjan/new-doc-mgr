rootProject.name = "kotlin-conventions"

dependencyResolutionManagement {
    versionCatalogs {
        create("testLibs") {
            from(files("../gradle/test-libs.versions.toml"))
        }
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

