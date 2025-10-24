pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
   versionCatalogs {
        create("libs") {
            from(files("gradle/libs.versions.toml"))
        }
    }
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AkiStudio"

include(":app")
include(":feature_editor")
include(":feature_filemanager")
include(":feature_terminal")
include(":feature_builder")
include(":core")
include(":ui")
