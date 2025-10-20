package com.akistudio.ide.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPreviewScreen(
    projectName: String,
    onClose: () -> Unit
) {
    var previewMode by remember { mutableStateOf(PreviewMode.PHONE) }
    var showLayoutBounds by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preview: $projectName") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, "Close")
                    }
                },
                actions = {
                    IconButton(onClick = { showLayoutBounds = !showLayoutBounds }) {
                        Icon(
                            Icons.Default.GridOn,
                            "Layout Bounds",
                            tint = if (showLayoutBounds) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    PreviewModeButton(
                        icon = Icons.Default.PhoneAndroid,
                        label = "Phone",
                        isSelected = previewMode == PreviewMode.PHONE,
                        onClick = { previewMode = PreviewMode.PHONE }
                    )
                    PreviewModeButton(
                        icon = Icons.Default.Tablet,
                        label = "Tablet",
                        isSelected = previewMode == PreviewMode.TABLET,
                        onClick = { previewMode = PreviewMode.TABLET }
                    )
                    PreviewModeButton(
                        icon = Icons.Default.Watch,
                        label = "Watch",
                        isSelected = previewMode == PreviewMode.WATCH,
                        onClick = { previewMode = PreviewMode.WATCH }
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            DevicePreview(
                mode = previewMode,
                showLayoutBounds = showLayoutBounds,
                content = { SampleAppContent() }
            )
        }
    }
}

@Composable
fun PreviewModeButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DevicePreview(
    mode: PreviewMode,
    showLayoutBounds: Boolean,
    content: @Composable () -> Unit
) {
    val (width, height) = when (mode) {
        PreviewMode.PHONE -> 360.dp to 640.dp
        PreviewMode.TABLET -> 600.dp to 960.dp
        PreviewMode.WATCH -> 250.dp to 250.dp
    }

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .then(
                if (showLayoutBounds)
                    Modifier.border(2.dp, Color.Red, RoundedCornerShape(16.dp))
                else Modifier
            )
    ) {
        content()
    }
}

@Composable
fun SampleAppContent() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "App Preview",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { }) {
                Text("Sample Button")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text("Sample Input") }
            )
        }
    }
}

enum class PreviewMode {
    PHONE, TABLET, WATCH
}
