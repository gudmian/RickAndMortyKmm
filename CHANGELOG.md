# Changelog

Журнал решений и прогресса по фазам разработки Rick & Morty KMM.
Формат: по фазам — что сделано, какие архитектурные решения приняты, версии, риски.

## [P1] Скелет модулей и сборка — 2026-06-29

### Сделано
- Удалён старый общий модуль. Добавлены `:core` и `:shared` (KMP, фреймворк `SharedKit`).
- Зарегистрированы 18 feature-модулей: 6 доменов × `{Api, Impl, AndroidUI}`.
- `settings.gradle.kts`, корневой `build.gradle.kts`, `gradle/libs.versions.toml` переписаны.
- `:androidApp` переведён на зависимости от `:shared` + 6 `*AndroidUI`.
- Созданы `build.gradle.kts` для всех 22 проектов. `./gradlew projects` — BUILD SUCCESSFUL.

### Решения
- **Модульный граф:** `:core` (инфра) ← `*Api` ← `*Impl`; `:shared` агрегирует `:core` + все `*Api`/`*Impl` и экспортирует их в iOS-фреймворк `SharedKit` через `export(...)`. `*AndroidUI` — отдельные Android-library модули (подход A). Правило `Api ← Impl` запрещено — соблюдено.
- **AndroidUI-модули** собираются как `com.android.library` + Compose Multiplatform (без `org.jetbrains.kotlin.android` — AGP 9 содержит встроенную поддержку Kotlin, плагин запрещён). Плагин `kotlinAndroid` оставлен в каталоге, но не применяется.
- **KMP-библиотеки** (`core`, `*Api`, `*Impl`, `shared`) используют `kotlinMultiplatform` + `androidMultiplatformLibrary`, таргеты `androidLibrary` + `iosArm64` + `iosSimulatorArm64`.

### Версии (каталог)
Ktor 3.5.1 · Koin 4.2.2 · Coil3 3.5.0 · kotlinx-coroutines 1.11.0 · kotlinx-serialization-json 1.11.0 · Turbine 1.2.1 · androidx.navigation-compose 2.9.8 · CM lifecycle 2.11.0-beta02. Якори: Kotlin 2.4.0, AGP 9.0.1, Compose Multiplatform 1.11.1.

### Риски
- `TEAM_ID` в `iosApp/Configuration/Config.xcconfig` пустой; сборка на физическое устройство требует development team.
