package com.akistudio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Neon Color Palette
val NeonCyan = Color(0xFF00D9FF)
val NeonPurple = Color(0xFFBB86FC)
val NeonPink = Color(0xFFFF006E)
val NeonGreen = Color(0xFF39FF14)

val DarkBackground = Color(0xFF1A1A1A)
val DarkSurface = Color(0xFF0D1B2A)
val GlassBackground = Color(0xFF1E293B)

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonPurple,
    tertiary = NeonPink,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0)
)

@Composable
fun AkiStudioTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
