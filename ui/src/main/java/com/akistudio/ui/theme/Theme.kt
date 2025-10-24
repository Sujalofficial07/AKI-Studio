package com.akistudio.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NeonCyan = Color(0xFF00E5FF)
private val NeonPurple = Color(0xFF9C27B0)
private val SurfaceDark = Color(0xFF1A1A1A)
private val SurfaceGlass = Color(0x33222222)

private val DarkColors = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonPurple,
    background = SurfaceDark,
    surface = SurfaceDark,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun AkiTheme(useDynamic: Boolean = true, content: @Composable () -> Unit) {
    // Dynamic color integration if desired
    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
