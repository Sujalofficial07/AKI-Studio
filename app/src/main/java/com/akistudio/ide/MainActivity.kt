package com.akistudio.ide

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.akistudio.ide.ui.theme.AkiStudioTheme
import com.akistudio.ide.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle permission results
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Request storage permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
                )
            )
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
        
        enableEdgeToEdge()
        setContent {
            AkiStudioApp()
        }
    }
}

@Composable
fun AkiStudioApp() {
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
