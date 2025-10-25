# ⚡ AkiStudio - Native Android IDE

<div align="center">

![Build Status](https://img.shields.io/github/actions/workflow/status/sujalofficial07/AKI-Studio/build.yml?style=for-the-badge&logo=github)
![Latest Release](https://img.shields.io/github/v/release/sujalofficial07/AKI-Studio?style=for-the-badge&logo=android&color=00D9FF)
![Downloads](https://img.shields.io/github/downloads/sujalofficial07/AKI-Studio/total?style=for-the-badge&color=BB86FC)
![License](https://img.shields.io/github/license/sujalofficial07/AKI-Studio?style=for-the-badge)

**A fully native Android IDE that runs entirely on your mobile device**

[🌐 Website](https://sujalofficial07.github.io/AKI-Studio) • [📥 Download APK](https://github.com/sujalofficial07/AKI-Studio/releases/latest) • [📖 Documentation](https://github.com/sujalofficial07/AKI-Studio/wiki) • [💬 Community](https://github.com/sujalofficial07/AKI-Studio/discussions)

</div>

---

## ✨ Features

### 💻 Advanced Code Editor
- **Syntax Highlighting** for Kotlin, XML, JSON, and more
- **Auto-completion** with intelligent keyword prediction
- **Auto-indent**, line numbers, and minimap navigation
- **Multi-tab** support for seamless file switching
- **Themes**: OneDark, Dracula, NeonBlue, Solarized

### 📁 Smart File Manager
- **Scoped Storage** support with Storage Access Framework
- **GitHub Integration** - Clone, push, and manage repositories
- **Project Templates** - Quick start with pre-configured projects
- **Zip/Unzip** support for archiving projects
- **File operations**: Create, rename, copy, move, delete

### ⚙️ Terminal Emulator
- **Linux-style terminal** with full command support
- **Gradle commands** execution (`./gradlew build`, etc.)
- **Git CLI** integration for version control
- **Real-time logs** with color-coded output
- **Custom themes** with configurable prompt styles

### 🔨 APK Builder
- **Auto-detect** Gradle projects and configurations
- **Build, sign, and export** APK files on-device
- **Real-time build logs** with error parsing
- **Output summary** with clickable fix suggestions
- **Multi-flavor** and build variant support

### 🎨 Modern UI/UX
- **Material 3** design with neon glow effects
- **Glassmorphism** and eye-safe dark theme
- **Smooth animations** powered by MotionLayout
- **Adaptive layouts** for phones and tablets
- **Dynamic color** system integration

### 🔌 Extensibility
- **Plugin system** for language servers (LSP)
- **Theme marketplace** for custom color schemes
- **Crash recovery** with auto session restore
- **SDK auto-setup** on first launch

---

## 📸 Screenshots

<div align="center">

| Editor | File Manager | Terminal | Builder |
|--------|-------------|----------|---------|
| ![Editor](screenshots/editor.png) | ![Files](screenshots/files.png) | ![Terminal](screenshots/terminal.png) | ![Builder](screenshots/builder.png) |

</div>

---

## 🚀 Getting Started

### Prerequisites
- Android device with **Android 7.0 (API 24)** or higher
- At least **2GB RAM** recommended
- **500MB free storage** for app and projects

### Installation

1. **Download the latest APK** from [Releases](https://github.com/yourusername/AkiStudio/releases/latest)
2. **Enable installation from unknown sources** in your device settings
3. **Install the APK** and launch AkiStudio
4. **Grant storage permissions** when prompted
5. **Start coding!**

### First Project

1. Tap **"New Project"** on the home screen
2. Choose a **template** (Empty, Basic Activity, etc.)
3. Enter **project details** (name, package, SDK)
4. Tap **"Create"** and start editing!

---

## 🛠️ Development Setup

### Building from Source

```bash
# Clone the repository
git clone https://github.com/yourusername/AkiStudio.git
cd AkiStudio

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

### Requirements
- **JDK 21+**
- **Android SDK** with API 35
- **Gradle 8.8+**

### Project Structure

```
AkiStudio/
├── app/                    # Main application module
├── core/                   # Shared utilities and models
├── ui/                     # UI theme and components
├── feature_editor/         # Code editor feature
├── feature_filemanager/    # File manager feature
├── feature_terminal/       # Terminal emulator feature
├── feature_builder/        # APK builder feature
├── .github/workflows/      # CI/CD automation
└── docs_site/             # GitHub Pages website
```

---

## 🤝 Contributing

We welcome contributions! Please read our [Contributing Guidelines](CONTRIBUTING.md) before submitting a PR.

### Ways to Contribute
- 🐛 Report bugs and issues
- 💡 Suggest new features
- 📝 Improve documentation
- 🔧 Submit pull requests
- 🌍 Translate the app

---

## 📋 Roadmap

- [x] Core IDE functionality
- [x] Syntax highlighting
- [x] APK builder
- [x] GitHub integration
- [ ] Language server protocol (LSP) support
- [ ] Flutter/Dart support
- [ ] Cloud backup and sync
- [ ] Collaborative editing
- [ ] Plugin marketplace

---

## 🔒 Security & Privacy

- **No telemetry** or data collection
- **Encrypted** Git credentials storage
- **Sandboxed** project access
- **Open source** - audit the code yourself

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- **Sora Editor** - Code editor component
- **Material 3** - Design system
- **Kotlin Community** - Amazing language and ecosystem

---

## 📞 Contact & Support

- **Website**: [akistudio.dev](https://Sujalofficial07.github.io/AKI-Studio)
- **Issues**: [GitHub Issues](https://github.com/sujalofficial07/AKI-Studio/issues)
- **Discussions**: [GitHub Discussions](https://github.com/sujalofficial07/AKI-Studio/discussions)
- **Email**: soon

---

<div align="center">

**Built with ⚡ and 💙 by the AkiStudio team**

⭐ Star us on GitHub if you find this project useful!

</div>
