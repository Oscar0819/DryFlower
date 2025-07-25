pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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
}

rootProject.name = "DryFlower"
include(":app")
include(":core")
include(":core:network")
include(":core:data")
include(":core:model")
include(":feature")
include(":feature:album")
include(":feature:search")
include(":designsystem")
include(":core:android")
include(":feature:artist")
