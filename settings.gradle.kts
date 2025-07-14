pluginManagement {
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

rootProject.name = "RickAndMortyApp"
include(":app")
include(":feature")
include(":data")
include(":feature:main")
include(":feature:character")
include(":feature:settings")
include(":feature:language")
include(":data:characters")
include(":data:characters:remote")
include(":data:characters:local")
include(":data:settings")
include(":feature:splash")
