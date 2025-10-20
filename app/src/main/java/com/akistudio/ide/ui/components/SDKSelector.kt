package com.akistudio.ide.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class SDKVersion(
    val apiLevel: Int,
    val version: String,
    val codeName: String,
    val isInstalled: Boolean = false
)

@Composable
fun SDKSelectorDialog(
    onDismiss: () -> Unit,
    onSelect: (SDKVersion) -> Unit
) {
    val sdkVersions = remember {
        listOf(
            SDKVersion(35, "15.0", "Vanilla Ice Cream", true),
            SDKVersion(34, "14.0", "Upside Down Cake", true),
            SDKVersion(33, "13.0", "Tiramisu", true),
            SDKVersion(32, "12L", "Snow Cone", true),
            SDKVersion(31, "12.0", "Snow Cone", true),
            SDKVersion(30, "11.0", "Red Velvet Cake", true),
            SDKVersion(29, "10.0", "Quince Tart", true),
            SDKVersion(28, "9.0", "Pie", false),
            SDKVersion(27, "8.1", "Oreo", false),
            SDKVersion(26, "8.0", "Oreo", false),
            SDKVersion(25, "7.1", "Nougat", false),
            SDKVersion(24, "7.0", "Nougat", false)
        )
    }
    
    var selectedSDK by remember { mutableStateOf<SDKVersion?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Target SDK") },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                items(sdkVersions) { sdk ->
                    SDKItem(
                        sdk = sdk,
                        isSelected = selectedSDK == sdk,
                        onClick = { selectedSDK = sdk }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedSDK?.let { onSelect(it) }
                    onDismiss()
                },
                enabled = selectedSDK != null
            ) {
                Text("Select")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun SDKItem(
    sdk: SDKVersion,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (sdk.isInstalled) Icons.Default.CheckCircle else Icons.Default.Download,
                contentDescription = null,
                tint = if (sdk.isInstalled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "API ${sdk.apiLevel} - Android ${sdk.version}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = sdk.codeName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (!sdk.isInstalled) {
                    Text(
                        text = "Not installed - Click to download",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            if (isSelected) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
