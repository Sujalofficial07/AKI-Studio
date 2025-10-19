package com.akistudio.ide.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.akistudio.ide.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onThemeChange: (Boolean, Boolean) -> Unit
) {
    var currentScreen by remember { mutableStateOf(Screen.Projects) }
    var showSettings by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }
    var isEyeProtectionMode by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Aki Studio") },
                actions = {
                    IconButton(onClick = { showSettings = true }) {
                        Icon(Icons.Default.Settings, "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentScreen == Screen.Projects,
                    onClick = { currentScreen = Screen.Projects },
                    icon = { Icon(Icons.Default.Folder, "Projects") },
                    label = { Text("Projects") }
                )
                NavigationBarItem(
                    selected = currentScreen == Screen.Editor,
                    onClick = { currentScreen = Screen.Editor },
                    icon = { Icon(Icons.Default.Code, "Editor") },
                    label = { Text("Editor") }
                )
                NavigationBarItem(
                    selected = currentScreen == Screen.Terminal,
                    onClick = { currentScreen = Screen.Terminal },
                    icon = { Icon(Icons.Default.Terminal, "Terminal") },
                    label = { Text("Terminal") }
                )
                NavigationBarItem(
                    selected = currentScreen == Screen.Tools,
                    onClick = { currentScreen = Screen.Tools },
                    icon = { Icon(Icons.Default.Build, "Tools") },
                    label = { Text("Tools") }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (currentScreen) {
                Screen.Projects -> ProjectsScreen()
                Screen.Editor -> EditorScreen()
                Screen.Terminal -> TerminalScreen()
                Screen.Tools -> ToolsScreen()
            }
        }

        if (showSettings) {
            SettingsDialog(
                isDarkMode = isDarkMode,
                isEyeProtectionMode = isEyeProtectionMode,
                onDismiss = { showSettings = false },
                onThemeChange = { dark, eyeProtection ->
                    isDarkMode = dark
                    isEyeProtectionMode = eyeProtection
                    onThemeChange(dark, eyeProtection)
                }
            )
        }
    }
}

enum class Screen {
    Projects, Editor, Terminal, Tools
}
