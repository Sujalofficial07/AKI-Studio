# Aki Studio - Setup Guide

## Prerequisites
- Android Studio Hedgehog or later
- JDK 17 or later
- Android SDK 35
- 8GB RAM recommended

## Step-by-Step Setup

### 1. Clone Repository
```bash
git clone https://github.com/yourusername/aki-studio.git
cd aki-studio
```

### 2. Open in Android Studio
- Launch Android Studio
- File → Open → Select aki-studio folder
- Wait for Gradle sync (5-10 minutes)

### 3. Build Project
```bash
./gradlew assembleDebug
```

### 4. Run on Device
- Enable USB Debugging on device
- Connect via USB
- Click Run ▶️ button

## Troubleshooting

### Gradle Sync Failed
- Check internet connection
- Clear Gradle cache: `./gradlew clean`
- Invalidate caches in Android Studio

### Build Errors
- Ensure JDK 17 is installed
- Check SDK installation
- Update Gradle if needed

## Configuration

### Change Package Name
Edit `app/build.gradle.kts`:
```kotlin
namespace = "your.package.name"
applicationId = "your.package.name"
```

### Customize Theme
Edit `ui/theme/Color.kt` to change colors.

## Support
Open an issue on GitHub for help.
