# AkiStudio — Native Android IDE

AkiStudio is a fully native Android IDE app built in Kotlin and Jetpack Compose. It features a neon-glow Material 3 UI, a syntax-highlighting editor, a SAF-based file manager, a terminal for Gradle/git, and a builder to assemble/sign APKs directly on-device.

![Status](https://img.shields.io/badge/status-pre--release-cyan)
![Kotlin](https://img.shields.io/badge/kotlin-100%25-purple)
![Gradle](https://img.shields.io/badge/gradle-8.8+-black)
![JDK](https://img.shields.io/badge/JDK-21+-black)

## Tech stack
- Kotlin (100%), Compose, Material 3
- Gradle 8.8+, JDK 21+
- Min SDK 24, Target SDK 35
- MVVM + Clean modular architecture
- GitHub Actions CI/CD: build, sign, release, docs deploy

## Modules
- `app/`: Shell & navigation
- `feature_editor/`: Compose editor & syntax highlighter
- `feature_filemanager/`: SAF operations & templates
- `feature_terminal/`: Terminal & process runner
- `feature_builder/`: Project detector & APK builder
- `core/`: DI, security, prefs, utils
- `ui/`: Neon glow theme, components

## Build
```bash
./gradlew :app:assembleDebug
