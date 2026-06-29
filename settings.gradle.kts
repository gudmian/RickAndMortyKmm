rootProject.name = "RickAndMortyKmm"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":androidApp")

// Umbrella + infrastructure
include(":core")
project(":core").projectDir = file("core")

include(":shared")
project(":shared").projectDir = file("shared")

// Character (feed + detail)
include(":features:CharacterApi")
project(":features:CharacterApi").projectDir = file("features/Character/CharacterApi")
include(":features:CharacterImpl")
project(":features:CharacterImpl").projectDir = file("features/Character/CharacterImpl")
include(":features:CharacterAndroidUI")
project(":features:CharacterAndroidUI").projectDir = file("features/Character/CharacterAndroidUI")

// CharacterFeed
include(":features:CharacterFeedApi")
project(":features:CharacterFeedApi").projectDir = file("features/CharacterFeed/CharacterFeedApi")
include(":features:CharacterFeedImpl")
project(":features:CharacterFeedImpl").projectDir = file("features/CharacterFeed/CharacterFeedImpl")
include(":features:CharacterFeedAndroidUI")
project(":features:CharacterFeedAndroidUI").projectDir = file("features/CharacterFeed/CharacterFeedAndroidUI")

// Location (feed + detail)
include(":features:LocationApi")
project(":features:LocationApi").projectDir = file("features/Location/LocationApi")
include(":features:LocationImpl")
project(":features:LocationImpl").projectDir = file("features/Location/LocationImpl")
include(":features:LocationAndroidUI")
project(":features:LocationAndroidUI").projectDir = file("features/Location/LocationAndroidUI")

// LocationFeed
include(":features:LocationFeedApi")
project(":features:LocationFeedApi").projectDir = file("features/LocationFeed/LocationFeedApi")
include(":features:LocationFeedImpl")
project(":features:LocationFeedImpl").projectDir = file("features/LocationFeed/LocationFeedImpl")
include(":features:LocationFeedAndroidUI")
project(":features:LocationFeedAndroidUI").projectDir = file("features/LocationFeed/LocationFeedAndroidUI")

// Episode (feed + detail)
include(":features:EpisodeApi")
project(":features:EpisodeApi").projectDir = file("features/Episode/EpisodeApi")
include(":features:EpisodeImpl")
project(":features:EpisodeImpl").projectDir = file("features/Episode/EpisodeImpl")
include(":features:EpisodeAndroidUI")
project(":features:EpisodeAndroidUI").projectDir = file("features/Episode/EpisodeAndroidUI")

// EpisodeFeed
include(":features:EpisodeFeedApi")
project(":features:EpisodeFeedApi").projectDir = file("features/EpisodeFeed/EpisodeFeedApi")
include(":features:EpisodeFeedImpl")
project(":features:EpisodeFeedImpl").projectDir = file("features/EpisodeFeed/EpisodeFeedImpl")
include(":features:EpisodeFeedAndroidUI")
project(":features:EpisodeFeedAndroidUI").projectDir = file("features/EpisodeFeed/EpisodeFeedAndroidUI")
