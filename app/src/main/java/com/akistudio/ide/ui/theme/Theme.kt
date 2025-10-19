package com.akistudio.ide.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val EyeProtectionColorScheme = lightColorScheme(
    primary = EyeProtectionPrimary,
    onPrimary = EyeProtectionOnPrimary,
    secondary = EyeProtectionSecondary,
    onSecondary = EyeProtectionOnSecondary,
    tertiary = EyeProtectionTertiary,
    onTertiary = EyeProtectionOnTertiary,
    background = EyeProtectionBackground,
    onBackground = EyeProtectionOnBackground,
    surface = EyeProtectionSurface,
    onSurface = EyeProtectionOnSurface,
    surfaceVariant = EyeProtectionSurfaceVariant,
    onSurfaceVariant = EyeProtectionOnSurfaceVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant
)

@Composable
fun AkiStudioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    eyeProtectionMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        eyeProtectionMode -> EyeProtectionColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
