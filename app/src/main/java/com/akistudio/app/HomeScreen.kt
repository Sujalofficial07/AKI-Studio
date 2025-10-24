package com.akistudio.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.akistudio.ui.components.NeonCard

@Composable
fun HomeScreen(onOpenProject: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NeonCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("AkiStudio", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
                Text("Native Android IDE • Compose • Neon Glow")
            }
        }
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onOpenProject) { Text("Open Editor") }
            Button(onClick = {}) { Text("File Manager") }
            Button(onClick = {}) { Text("Terminal") }
            Button(onClick = {}) { Text("Builder") }
        }
    }
}
