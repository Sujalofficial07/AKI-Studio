# 🎨 Aki Studio - Mobile Android IDE

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-brightgreen.svg)](https://developer.android.com/jetpack/compose)
[![API](https://img.shields.io/badge/API-24%2B-orange.svg)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A powerful, feature-rich Android IDE built for mobile devices with eye-protective UI themes and professional development tools.

![Aki Studio Banner](https://via.placeholder.com/1200x400/2196F3/FFFFFF?text=Aki+Studio+-+Code+Anywhere)

## ✨ Features

### 🎨 **Adaptive Themes**
- 🌙 **Dark Mode** - OLED-optimized with true black backgrounds
- 👁️ **Eye Protection Mode** - Warm color palette with reduced blue light
- ☀️ **Light Mode** - Clean and professional appearance
- 🎯 **Automatic Theme Switching** - Based on system preferences

### 💻 **Code Editor**
- Line numbers with syntax-aware coloring
- Multi-file tab management
- Horizontal and vertical scrolling
- Monospace font optimized for code
- Auto-save functionality
- File picker with project structure

### 🖥️ **Built-in Terminal**
- Execute build commands (`build`, `run`, `clean`)
- Command history
- Real-time output display
- Custom command support
- Clear and persistent sessions

### 📁 **Project Management**
- Create new Android projects
- Multiple project templates:
  - Empty Activity
  - Basic Activity
  - Bottom Navigation Activity
- Project metadata tracking
- Quick access to recent projects

### 🛠️ **Developer Tools Suite**
- **Layout Inspector** - Analyze UI hierarchies
- **LogCat Viewer** - Real-time application logs
- **Resource Manager** - Manage drawables, strings, colors
- **Build Analyzer** - Performance insights
- **Gradle Console** - Build system output
- **Device Manager** - Virtual device management
- **Profiler** - CPU, memory, network monitoring
- **Database Inspector** - SQLite database viewer
- **Network Inspector** - HTTP/HTTPS traffic monitoring
- **SDK Manager** - Android SDK updates
- **APK Analyzer** - APK size and composition analysis
- **Git Integration** - Version control support

## 📱 Screenshots

| Projects Screen | Code Editor | Terminal |
|----------------|-------------|----------|
| ![Projects](https://via.placeholder.com/300x600/E3F2FD/2196F3?text=Projects) | ![Editor](https://via.placeholder.com/300x600/E8F5E9/4CAF50?text=Editor) | ![Terminal](https://via.placeholder.com/300x600/FFF3E0/FF9800?text=Terminal) |

| Tools Grid | Eye Protection Mode | Dark Mode |
|------------|---------------------|-----------|
| ![Tools](https://via.placeholder.com/300x600/F3E5F5/9C27B0?text=Tools) | ![Eye Protection](https://via.placeholder.com/300x600/FFF8E1/FFB74D?text=Eye+Protection) | ![Dark](https://via.placeholder.com/300x600/212121/90CAF9?text=Dark+Mode) |

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or later
- JDK 17 or later
- Android SDK 34
- Minimum Android device: API 24 (Android 7.0)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/aki-studio.git
cd aki-studio
```

2. **Open in Android Studio**
- Launch Android Studio
- Select "Open an Existing Project"
- Navigate to the cloned directory
- Wait for Gradle sync to complete

3. **Build and Run**
```bash
./gradlew assembleDebug
./gradlew installDebug
```

Or simply click the **Run** button in Android Studio.

## 📂 Project Structure

```
aki-studio/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/akistudio/ide/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── ui/
│   │   │   │       ├── theme/
│   │   │   │       │   ├── Theme.kt
│   │   │   │       │   ├── Color.kt
│   │   │   │       │   └── Type.kt
│   │   │   │       ├── screens/
│   │   │   │       │   └── MainScreen.kt
│   │   │   │       └── components/
│   │   │   │           ├── ProjectsScreen.kt
│   │   │   │           ├── EditorScreen.kt
│   │   │   │           ├── TerminalScreen.kt
│   │   │   │           ├── ToolsScreen.kt
│   │   │   │           └── SettingsDialog.kt
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
├── LICENSE
└── .gitignore
```

## 🔧 Configuration

### Gradle Properties
Edit `gradle.properties` to customize build settings:
```properties
org.gradle.jvmargs=-Xmx4096m
android.useAndroidX=true
kotlin.code.style=official
```

### Theme Customization
Modify colors in `ui/theme/Color.kt`:
```kotlin
val CustomPrimary = Color(0xFF2196F3)
val CustomSecondary = Color(0xFF4CAF50)
```

## 📖 Usage

### Creating a Project
1. Navigate to **Projects** tab
2. Tap the **+** (Add) button
3. Enter project name
4. Select a template
5. Tap **Create**

### Editing Code
1. Go to **Editor** tab
2. Open a file from the file picker
3. Edit code with full keyboard support
4. Auto-save keeps your changes

### Running Commands
1. Switch to **Terminal** tab
2. Type commands:
   - `help` - Show available commands
   - `build` - Build the project
   - `run` - Run the application
   - `clean` - Clean build files
   - `version` - Show version info

### Enabling Eye Protection
1. Tap **Settings** icon (⚙️)
2. Toggle **Eye Protection Mode**
3. Enjoy reduced blue light!

## 🛠️ Built With

- **[Kotlin](https://kotlinlang.org/)** - Programming language
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern UI toolkit
- **[Material Design 3](https://m3.material.io/)** - Design system
- **[Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - Asynchronous programming
- **[Gradle](https://gradle.org/)** - Build system

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and development process.

## 📝 Roadmap

- [ ] Syntax highlighting for multiple languages
- [ ] Code completion and IntelliSense
- [ ] Git integration (commit, push, pull)
- [ ] Plugin system for extensions
- [ ] Cloud sync for projects
- [ ] Collaborative editing
- [ ] Debugger integration
- [ ] Layout drag-and-drop designer
- [ ] APK signing and publishing
- [ ] Multi-language support

## 🐛 Known Issues

- Large files may cause performance issues on low-end devices
- Syntax highlighting not yet implemented
- Terminal commands are simulated (not real shell)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Your Name** - *Initial work* - [YourGitHub](https://github.com/yourusername)

See also the list of [contributors](https://github.com/yourusername/aki-studio/contributors) who participated in this project.

## 🙏 Acknowledgments

- Inspired by Android Studio and VS Code
- Material Design 3 guidelines
- The Android developer community
- All contributors and testers

## 📞 Support

- 📧 Email: support@akistudio.dev
- 🐛 Issues: [GitHub Issues](https://github.com/yourusername/aki-studio/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/yourusername/aki-studio/discussions)

## ⭐ Star History

[![Star History Chart](https://api.star-history.com/svg?repos=yourusername/aki-studio&type=Date)](https://star-history.com/#yourusername/aki-studio&Date)

---

<p align="center">Made with ❤️ for the Android community</p>
<p align="center">⭐ Star this repo if you find it useful!</p>
