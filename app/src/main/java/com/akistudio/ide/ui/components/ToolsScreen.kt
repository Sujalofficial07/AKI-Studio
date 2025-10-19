package com.akistudio.ide.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class Tool(
    val name: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun ToolsScreen() {
    val tools = listOf(
        Tool("Layout Inspector", "Inspect UI layouts", Icons.Default.ViewInAr),
        Tool("LogCat", "View application logs", Icons.Default.Article),
        Tool("Resource Manager", "Manage app resources", Icons.Default.Image),
        Tool("Build Analyzer", "Analyze build performance", Icons.Default.Speed),
        Tool("Gradle Console", "View Gradle output", Icons.Default.Terminal),
        Tool("Device Manager", "Manage virtual devices", Icons.Default.PhoneAndroid),
        Tool("Profiler", "Profile app performance", Icons.Default.ShowChart),
        Tool("Database Inspector", "Inspect databases", Icons.Default.Storage),
        Tool("Network Inspector", "Monitor network calls", Icons.Default.Cloud),
        Tool("SDK Manager", "Manage Android SDKs", Icons.Default.Download),
        Tool("APK Analyzer", "Analyze APK files", Icons.Default.FindInPage),
        Tool("Git Integration", "Version control", Icons.Default.AccountTree)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Developer Tools",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tools) { tool ->
                ToolCard(tool = tool)
            }
        }
    }
}

@Composable
fun ToolCard(tool: Tool) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = tool.icon,
                contentDescription = tool.name,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = tool.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tool.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
