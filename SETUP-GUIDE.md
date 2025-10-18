# 🚀 Aki Studio - Complete Setup Guide

## 📋 Table of Contents
1. [Prerequisites](#prerequisites)
2. [Local Development Setup](#local-development-setup)
3. [GitHub Repository Setup](#github-repository-setup)
4. [Building the Project](#building-the-project)
5. [Running on Device](#running-on-device)
6. [Project Structure](#project-structure)
7. [Configuration](#configuration)
8. [Troubleshooting](#troubleshooting)

---

## Prerequisites

### Required Software
- **Android Studio**: Hedgehog | 2023.1.1 or later
- **JDK**: Version 17 or later
- **Git**: Latest version
- **Android SDK**: API Level 35 (Android 15)
- **Minimum Device**: Android 7.0 (API 24)

### Recommended
- **RAM**: 8GB minimum, 16GB recommended
- **Storage**: 10GB free space
- **Internet**: For downloading dependencies

---

## Local Development Setup

### Step 1: Install Android Studio
1. Download from [developer.android.com](https://developer.android.com/studio)
2. Run installer and follow setup wizard
3. Install Android SDK 35 and Build Tools

### Step 2: Install JDK 17
```bash
# On macOS with Homebrew
brew install openjdk@17

# On Windows
# Download from https://adoptium.net/

# On Linux (Ubuntu/Debian)
sudo apt install openjdk-17-jdk
```

### Step 3: Clone Repository
```bash
git clone https://github.com/yourusername/aki-studio.git
cd aki-studio
```

### Step 4: Open in Android Studio
1. Launch Android Studio
2. Click **File → Open**
3. Navigate to `aki-studio` folder
4. Click **OK**
5. Wait for Gradle sync (5-10 minutes first time)

### Step 5: Configure SDK
1. Go to **Tools → SDK Manager**
2. Ensure these are installed:
   - Android SDK Platform 35
   - Android SDK Build-Tools 35.0.0
   - Android Emulator
   - Android SDK Platform-Tools

---

## GitHub Repository Setup

### Step 1: Create GitHub Repository
```bash
# Initialize git (if not cloned)
git init

# Add remote
git remote add origin https://github.com/yourusername/aki-studio.git

# Add all files
git add .

# Commit
git commit -m "Initial commit: Aki Studio v1.0.0"

# Push to GitHub
git push -u origin main
```

### Step 2: Set Up GitHub Actions
The `.github/workflows/android.yml` file is already configured. It will:
- Build the project on every push
- Run all tests
- Generate APK artifacts
- Upload APK for download

### Step 3: Add Repository Secrets (Optional)
For release builds with signing:
1. Go to **Settings → Secrets and variables → Actions**
2. Add these secrets:
   - `KEYSTORE_FILE` (base64 encoded keystore)
   - `KEYSTORE_PASSWORD`
   - `KEY_ALIAS`
   - `KEY_PASSWORD`

---

## Building the Project

### Debug Build (Development)
```bash
# Via Gradle
./gradlew assembleDebug

# Output location
# app/build/outputs/apk/debug/app-debug.apk
```

### Release Build (Production)
```bash
# Via Gradle
./gradlew assembleRelease

# Output location
# app/build/outputs/apk/release/app-release.apk
```

### Build in Android Studio
1. Click **Build → Build Bundle(s) / APK(s)**
2. Select **Build APK(s)**
3. Wait for build to complete
4. Click **locate** to find the APK

---

## Running on Device

### Option 1: USB Debugging
1. Enable Developer Options on your device:
   - Go to **Settings → About Phone**
   - Tap **Build Number** 7 times
2. Enable **USB Debugging** in Developer Options
3. Connect device via USB
4. Click **Run** (▶️) in Android Studio
5. Select your device from the list

### Option 2: Wireless Debugging (Android 11+)
1. Enable **Wireless Debugging** in Developer Options
2. In Android Studio, click **Pair Devices Using Wi-Fi**
3. Enter pairing code from device
4. Click **Run** and select device

### Option 3: Emulator
1. Click **Device Manager** in Android Studio
2. Click **Create Device**
3. Select device (e.g., Pixel 6)
4. Select system image (API 35)
5. Click **Finish**
6. Click **Run** and select emulator

---

## Project Structure

```
aki-studio/
├── .github/                      # GitHub configurations
│   ├── workflows/
│   │   └── android.yml          # CI/CD pipeline
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md
│   │   └── feature_request.md
│   └── pull_request_template.md
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/akistudio/ide/
│   │   │   │   ├── MainActivity.kt              # App entry point
│   │   │   │   └── ui/
│   │   │   │       ├── theme/
│   │   │   │       │   ├── Color.kt            # Color definitions
│   │   │   │       │   ├── Theme.kt            # Theme configuration
│   │   │   │       │   └── Type.kt             # Typography
│   │   │   │       ├── screens/
│   │   │   │       │   └── MainScreen.kt       # Main navigation
│   │   │   │       └── components/
│   │   │   │           ├── ProjectsScreen.kt   # Project manager
│   │   │   │           ├── EditorScreen.kt     # Code editor
│   │   │   │           ├── TerminalScreen.kt   # Terminal
│   │   │   │           ├── ToolsScreen.kt      # Developer tools
│   │   │   │           └── SettingsDialog.kt   # Settings
│   │   │   ├── res/
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   └── mipmap-*/                   # App icons
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                                # Unit tests
│   │   └── androidTest/                         # Instrumented tests
│   ├── build.gradle.kts                         # App build config
│   └── proguard-rules.pro                       # ProGuard rules
├── gradle/
│   ├── wrapper/
│   │   ├── gradle-wrapper.jar
│   │   └── gradle-wrapper.properties
│   └── libs.versions.toml                       # Version catalog
├── build.gradle.kts                             # Root build config
├── settings.gradle.kts                          # Project settings
├── gradle.properties                            # Gradle properties
├── .gitignore
├── README.md
├── LICENSE
├── CONTRIBUTING.md
└── SETUP_GUIDE.md (this file)
```

---

## Configuration

### Update Package Name
To change the package name from `com.akistudio.ide`:

1. **In `build.gradle.kts` (app level)**:
```kotlin
android {
    namespace = "com.yourname.yourapp"
    defaultConfig {
        applicationId = "com.yourname.yourapp"
    }
}
```

2. **Refactor in Android Studio**:
   - Right-click on package
   - Select **Refactor → Rename**
   - Enter new name
   - Click **Refactor**

### Update App Name
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your App Name</string>
```

### Customize Colors
Edit `ui/theme/Color.kt`:
```kotlin
val CustomPrimary = Color(0xFFYourColor)
val CustomSecondary = Color(0xFFYourColor)
```

### Change Version
Edit `app/build.gradle.kts`:
```kotlin
defaultConfig {
    versionCode = 2
    versionName = "1.0.1"
}
```

---

## Troubleshooting

### Gradle Sync Failed
**Issue**: Gradle sync fails with connection error

**Solution**:
```bash
# Clear Gradle cache
rm -rf ~/.gradle/caches/

# In Android Studio: File → Invalidate Caches → Invalidate and Restart
```

### Build Failed - SDK Not Found
**Issue**: SDK location not found

**Solution**:
Create `local.properties`:
```properties
sdk.dir=/path/to/Android/Sdk
```

### App Crashes on Launch
**Issue**: App crashes immediately after launch

**Solutions**:
1. Check LogCat for errors
2. Verify `AndroidManifest.xml` is correct
3. Ensure all permissions are declared
4. Clean and rebuild: `./gradlew clean build`

### Out of Memory Error
**Issue**: Gradle runs out of memory

**Solution**:
Edit `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=512m
```

### Device Not Detected
**Issue**: USB device not