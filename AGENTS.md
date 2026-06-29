# AGENTS.md

This file provides guidance to Codex (Codex.ai/code) when working with code in this repository.

## Project Overview

Kotlin Multiplatform (KMP) app for Android + iOS around the Rick & Morty API.
The app has Android and iOS shells, shared infrastructure, an iOS umbrella
framework module, and feature modules split by domain.

Stack: Kotlin 2.4.0, AGP 9.0.1, Compose Multiplatform 1.11.1, Material3. JVM target 11.
Versions live in `gradle/libs.versions.toml` (single source of truth — do not hardcode
versions in module build files). Typesafe accessors are enabled
(`settings.gradle.kts` → `enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")`), so use
`projects.shared`, `projects.core`, `projects.features.characterFeedApi`, `libs.compose.ui`, etc.

## Build, Run, Test

Gradle wrapper is the entry point. Configuration cache and build cache are **on**
(`gradle.properties`) — prefer `./gradlew` over launching a daemon manually.

```bash
# Android debug APK
./gradlew :androidApp:assembleDebug

# Build the shared iOS framework the way Xcode does
./gradlew :shared:embedAndSignAppleFrameworkForXcode

# Run shared module tests across targets
./gradlew :shared:allTests

# Run all checks
./gradlew check

# Clean
./gradlew clean
```

**iOS app:** open `iosApp/iosApp.xcodeproj` in Xcode and Run. The Xcode project has a
Run Script phase that shells out to `./gradlew :shared:embedAndSignAppleFrameworkForXcode`
from the repo root, so the `SharedKit` framework is built automatically before the Swift
target compiles. The script sets `JAVA_HOME` to the local Homebrew JDK 21 when Xcode's GUI
environment does not expose Java. Only `iosArm64` + `iosSimulatorArm64` are configured —
there is no `iosX64` (Intel simulator) target and no desktop/JVM target.
`iosApp/Configuration/Config.xcconfig` has `TEAM_ID=` empty; set a team before building
for a physical device.

`local.properties` (git-ignored) must contain `sdk.dir=...` pointing at the Android SDK
for any Android/Gradle build to configure.

No lint/format tooling (ktlint, detekt, spotless) is wired up — do not assume
`./gradlew detekt`/`check` exists. Code style is `kotlin.code.style=official`.

## Architecture

### Modules (`settings.gradle.kts` is the source of truth)

- `:androidApp` — Android app shell. Applies Compose Multiplatform and depends on
  `:shared` plus the Android UI feature modules.
- `:core` — shared infrastructure: networking, dispatchers, base state/view-model helpers,
  and DI wiring.
- `:shared` — KMP umbrella module. Produces a **static** iOS framework named `SharedKit`
  consumed by Swift via `import SharedKit`. It exports `:core` plus all feature `Api` and
  `Impl` modules and exposes Swift entry points such as Koin setup and ViewModel factories.
- `:features:*` — six domains (Character, Location, Episode, and their `*Feed` variants)
  split into `*Api`, `*Impl`, and `*AndroidUI` modules. KMP feature modules use
  `kotlinMultiplatform` + `androidMultiplatformLibrary`; Android UI modules use Compose.

### iOS ↔ Kotlin bridge

Swift calls into Kotlin through the `SharedKit` framework. The iOS app imports
`SharedKit`, calls `setupKoin()` at launch, and obtains feature ViewModels through
top-level factory functions exposed by `:shared`. To expose new Kotlin code to iOS, make
it public in a module exported by `:shared`, then rebuild via the Xcode run script or
`:shared:embedAndSignAppleFrameworkForXcode`.

### Planned feature pattern

The `Api`/`Impl` split per domain signals the intended architecture: each feature exposes
a public `*Api` module (interfaces/models) consumed by other features and the app shells,
with the `*Impl` module holding the implementation. Nothing enforces this yet — follow it
when adding the first feature so the module graph stays decoupled.

## Known gaps (read before assuming things compile)

- `TEAM_ID` is empty in `iosApp/Configuration/Config.xcconfig`; physical-device builds
  require a development team.
- No lint/format tooling (ktlint, detekt, spotless) is wired up.
