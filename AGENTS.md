# AGENTS.md

This file provides guidance to Codex (Codex.ai/code) when working with code in this repository.

## Project Overview

Kotlin Multiplatform (KMP) app for Android + iOS around the Rick & Morty API. Early
scaffold stage: only `sharedLogic` and the two app shells contain real code. The
feature-module structure is declared but not yet implemented (see Architecture).

Stack: Kotlin 2.4.0, AGP 9.0.1, Compose Multiplatform 1.11.1, Material3. JVM target 11.
Versions live in `gradle/libs.versions.toml` (single source of truth — do not hardcode
versions in module build files). Typesafe accessors are enabled
(`settings.gradle.kts` → `enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")`), so use
`projects.sharedLogic`, `projects.features.characterFeedApi`, `libs.compose.ui`, etc.

## Build, Run, Test

Gradle wrapper is the entry point. Configuration cache and build cache are **on**
(`gradle.properties`) — prefer `./gradlew` over launching a daemon manually.

```bash
# Android debug APK (NOTE: currently fails to compile — see "Known gaps" below)
./gradlew :androidApp:assembleDebug

# Build the shared iOS framework the way Xcode does
./gradlew :sharedLogic:embedAndSignAppleFrameworkForXcode

# Run all shared-logic tests across targets
./gradlew :sharedLogic:allTests

# Single test class / method (host/JVM tests)
./gradlew :sharedLogic:allTests --tests "com.example.rickandmortykmm.GreetingTest"

# Clean
./gradlew clean
```

**iOS app:** open `iosApp/iosApp.xcodeproj` in Xcode and Run. The Xcode project has a
Run Script phase that shells out to `./gradlew :sharedLogic:embedAndSignAppleFrameworkForXcode`
from the repo root, so the `SharedLogic` framework is built automatically before the Swift
target compiles. Only `iosArm64` + `iosSimulatorArm64` are configured — there is no
`iosX64` (Intel simulator) target and no desktop/JVM target. `iosApp/Configuration/Config.xcconfig`
has `TEAM_ID=` empty; set a team before building for a physical device.

`local.properties` (git-ignored) must contain `sdk.dir=...` pointing at the Android SDK
for any Android/Gradle build to configure.

No lint/format tooling (ktlint, detekt, spotless) is wired up — do not assume
`./gradlew detekt`/`check` exists. Code style is `kotlin.code.style=official`.

## Architecture

### Modules (`settings.gradle.kts` is the source of truth)

- `:androidApp` — Android app shell. Applies `composeMultiplatform` (Compose MP, not
  plain AndroidX Compose). Depends only on `:sharedLogic`. Single source file:
  `MainActivity.kt`.
- `:sharedLogic` — the KMP library. Produces a **static** iOS framework named
  `SharedLogic` (consumed by Swift via `import SharedLogic`). Targets:
  `androidLibrary`, `iosArm64`, `iosSimulatorArm64`. Source sets: `commonMain`,
  `androidMain`, `iosMain`, `commonTest`. Package root: `com.example.rickandmortykmm`.
- `:features:*` — **declared but empty.** Six domains (Character, Location, Episode,
  and their `*Feed` variants) each split into `*Api` + `*Impl` (12 modules total). The
  directories under `features/` contain no `build.gradle.kts` and no sources — only the
  `include(...)` + `project(...).projectDir` mappings in `settings.gradle.kts`. Gradle
  treats them as empty projects. When implementing one, you must create its
  `build.gradle.kts` (apply `kotlinMultiplatform` + `androidMultiplatformLibrary`,
  matching `:sharedLogic`) and its source sets.

### iOS ↔ Kotlin bridge

Swift calls into Kotlin through the `SharedLogic` framework. `ContentView.swift` does
`import SharedLogic` and `Greeting().greet()`. To expose new Kotlin code to iOS, it must
live in `:sharedLogic` (commonMain + iosMain) and be public; rebuild via the Xcode run
script or `:sharedLogic:embedAndSignAppleFrameworkForXcode`.

### Planned feature pattern

The `Api`/`Impl` split per domain signals the intended architecture: each feature exposes
a public `*Api` module (interfaces/models) consumed by other features and the app shells,
with the `*Impl` module holding the implementation. Nothing enforces this yet — follow it
when adding the first feature so the module graph stays decoupled.

## Known gaps (read before assuming things compile)

- **`App()` is undefined.** `androidApp/.../MainActivity.kt` calls a top-level `App()`
  composable that exists in no tracked source file, so `:androidApp:assembleDebug` does
  not compile. The README references a `/sharedUI` Compose-Multiplatform module where
  shared UI (and presumably `App()`) is meant to live, but that module is not in
  `settings.gradle.kts` and not on disk. Provide `App()` (or create `sharedUI` and wire
  it into `:androidApp`'s dependencies) before expecting an Android build to pass.
- **README is partially stale.** It describes a `sharedUI` module and a `jvmMain`/desktop
  target that do not exist in this project. Trust `settings.gradle.kts` and
  `gradle/libs.versions.toml`, not the README's module/target list.
- **No infrastructure libraries yet.** The version catalog has only Compose + AndroidX +
  `kotlin-test`/`junit`. There is no networking (Ktor), DI (Koin/Hilt), persistence
  (SQLDelight/Room), navigation, image loading, or coroutine-test/Turbine. Adding any of
  these means adding the dependency to `libs.versions.toml` first.
- **Not under git.** No `.git` directory is initialized; `.gitignore` exists but is inert
  until `git init`.
