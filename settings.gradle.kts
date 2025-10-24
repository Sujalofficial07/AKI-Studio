pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("aki") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "AkiStudio"

include(":app")
include(":core")
include(":ui")
include(":feature_editor")
include(":feature_filemanager")
include(":feature_terminal")
include(":feature_builder")
