This is a Kotlin Multiplatform project targeting Android, iOS.

* [/androidApp](./androidApp/src/main/kotlin) contains the Android Compose application shell.

* [/iosApp](./iosApp/iosApp) contains the native iOS application shell. It imports the Kotlin
  framework as `SharedKit`.

* [/shared](./shared/src/commonMain/kotlin) is the KMP umbrella module exported to iOS as
  `SharedKit`.

* [/core](./core/src/commonMain/kotlin) contains shared infrastructure.

* [/features](./features) contains feature modules split into `Api`, `Impl`, and Android UI modules.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.
- iOS Kotlin framework: `./gradlew :shared:embedAndSignAppleFrameworkForXcode` from Xcode,
  or use Xcode's built-in Run Script phase.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
