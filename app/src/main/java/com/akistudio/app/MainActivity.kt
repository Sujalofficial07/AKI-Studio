package com.akistudio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akistudio.ui.theme.AkiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AkiTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(onOpenProject = { navController.navigate("editor") }) }
                    composable("editor") { EditorScreen() }
                    composable("filemanager") { FileManagerScreen() }
                    composable("terminal") { TerminalScreen() }
                    composable("builder") { BuilderScreen() }
                    composable("settings") { SettingsScreen() }
                }
            }
        }
    }
}
