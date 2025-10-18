package com.akistudio.ide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.akistudio.ide.ui.theme.AkiStudioTheme
import com.akistudio.ide.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            var isEyeProtectionMode by remember { mutableStateOf(false) }
            
            AkiStudioTheme(
                darkTheme = isDarkMode,
                eyeProtectionMode = isEyeProtectionMode
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        onThemeChange = { dark, eyeProtection ->
                            isDarkMode = dark
                            isEyeProtectionMode = eyeProtection
                        }
                    )
                }
            }
        }
    }
}
