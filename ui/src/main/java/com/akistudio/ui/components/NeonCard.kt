package com.akistudio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NeonCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val glow = Brush.radialGradient(
        colors = listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.45f), Color.Transparent)
    )
    Box(
        modifier = modifier
            .background(Color(0x33222222))
            .clip(MaterialTheme.shapes.medium)
            .padding(12.dp)
    ) {
        Box(Modifier.blur(12.dp).background(glow))
        content()
    }
}
