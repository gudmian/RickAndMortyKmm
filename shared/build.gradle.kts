import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
}

kotlin {
    androidLibrary {
        namespace = "com.example.rickandmortykmm.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SharedKit"
            isStatic = true
            export(projects.core)
            export(projects.features.characterApi)
            export(projects.features.characterImpl)
            export(projects.features.characterFeedApi)
            export(projects.features.characterFeedImpl)
            export(projects.features.locationApi)
            export(projects.features.locationImpl)
            export(projects.features.locationFeedApi)
            export(projects.features.locationFeedImpl)
            export(projects.features.episodeApi)
            export(projects.features.episodeImpl)
            export(projects.features.episodeFeedApi)
            export(projects.features.episodeFeedImpl)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            api(projects.features.characterApi)
            api(projects.features.characterImpl)
            api(projects.features.characterFeedApi)
            api(projects.features.characterFeedImpl)
            api(projects.features.locationApi)
            api(projects.features.locationImpl)
            api(projects.features.locationFeedApi)
            api(projects.features.locationFeedImpl)
            api(projects.features.episodeApi)
            api(projects.features.episodeImpl)
            api(projects.features.episodeFeedApi)
            api(projects.features.episodeFeedImpl)
        }
    }
}
