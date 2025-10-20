package com.akistudio.ide.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedCreateProjectDialog(
    onDismiss: () -> Unit,
    onCreate: (ProjectConfig) -> Unit
) {
    var projectName by remember { mutableStateOf("") }
    var packageName by remember { mutableStateOf("com.example.app") }
    var selectedTemplate by remember { mutableStateOf("Empty Activity") }
    var selectedSDK by remember { mutableStateOf<SDKVersion?>(null) }
    var minSDK by remember { mutableStateOf(24) }
    var showSDKSelector by remember { mutableStateOf(false) }
    var currentStep by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp),
        title = {
            Column {
                Text("Create New Project")
                LinearProgressIndicator(
                    progress = (currentStep + 1) / 4f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        },
        text = {
            when (currentStep) {
                0 -> ProjectInfoStep(
                    projectName = projectName,
                    onProjectNameChange = { projectName = it },
                    packageName = packageName,
                    onPackageNameChange = { packageName = it }
                )
                1 -> TemplateSelectionStep(
                    selectedTemplate = selectedTemplate,
                    onTemplateSelect = { selectedTemplate = it }
                )
                2 -> SDKSelectionStep(
                    minSDK = minSDK,
                    onMinSDKChange = { minSDK = it },
                    targetSDK = selectedSDK,
                    onTargetSDKClick = { showSDKSelector = true }
                )
                3 -> ProjectSummaryStep(
                    projectName = projectName,
                    packageName = packageName,
                    template = selectedTemplate,
                    minSDK = minSDK,
                    targetSDK = selectedSDK
                )
            }
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (currentStep > 0) {
                    TextButton(onClick = { currentStep-- }) {
                        Text("Back")
                    }
                }
                Button(
                    onClick = {
                        if (currentStep < 3) {
                            currentStep++
                        } else {
                            onCreate(
                                ProjectConfig(
                                    name = projectName,
                                    packageName = packageName,
                                    template = selectedTemplate,
                                    minSDK = minSDK,
                                    targetSDK = selectedSDK?.apiLevel ?: 35
                                )
                            )
                        }
                    },
                    enabled = when (currentStep) {
                        0 -> projectName.isNotBlank() && packageName.isNotBlank()
                        else -> true
                    }
                ) {
                    Text(if (currentStep < 3) "Next" else "Create")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )

    if (showSDKSelector) {
        SDKSelectorDialog(
            onDismiss = { showSDKSelector = false },
            onSelect = { selectedSDK = it }
        )
    }
}

@Composable
fun ProjectInfoStep(
    projectName: String,
    onProjectNameChange: (String) -> Unit,
    packageName: String,
    onPackageNameChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Step 1: Project Information", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = projectName,
            onValueChange = onProjectNameChange,
            label = { Text("Project Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = packageName,
            onValueChange = onPackageNameChange,
            label = { Text("Package Name") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("com.example.app") }
        )
    }
}

@Composable
fun TemplateSelectionStep(
    selectedTemplate: String,
    onTemplateSelect: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Step 2: Select Template", style = MaterialTheme.typography.titleMedium)
        listOf(
            "Empty Activity" to "A blank activity with basic setup",
            "Basic Activity" to "Activity with toolbar and FAB",
            "Bottom Navigation" to "Activity with bottom navigation",
            "Navigation Drawer" to "Activity with navigation drawer",
            "Tabbed Activity" to "Activity with tabs"
        ).forEach { (template, description) ->
            TemplateCard(
                name = template,
                description = description,
                isSelected = selectedTemplate == template,
                onClick = { onTemplateSelect(template) }
            )
        }
    }
}

@Composable
fun SDKSelectionStep(
    minSDK: Int,
    onMinSDKChange: (Int) -> Unit,
    targetSDK: SDKVersion?,
    onTargetSDKClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Step 3: SDK Selection", style = MaterialTheme.typography.titleMedium)
        Text("Minimum SDK: API $minSDK")
        Slider(
            value = minSDK.toFloat(),
            onValueChange = { onMinSDKChange(it.toInt()) },
            valueRange = 21f..35f,
            steps = 14
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = onTargetSDKClick
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Target SDK")
                    Text(
                        text = targetSDK?.let { "API ${it.apiLevel} - Android ${it.version}" } ?: "Not selected",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Icon(Icons.Default.ArrowForward, null)
            }
        }
    }
}

@Composable
fun ProjectSummaryStep(
    projectName: String,
    packageName: String,
    template: String,
    minSDK: Int,
    targetSDK: SDKVersion?
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Step 4: Review & Create", style = MaterialTheme.typography.titleMedium)
        SummaryItem("Project Name", projectName)
        SummaryItem("Package", packageName)
        SummaryItem("Template", template)
        SummaryItem("Min SDK", "API $minSDK")
        SummaryItem("Target SDK", targetSDK?.let { "API ${it.apiLevel}" } ?: "API 35")
    }
}

@Composable
fun TemplateCard(
    name: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
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
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(name, style = MaterialTheme.typography.titleSmall)
                Text(
                    description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (isSelected) {
                Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.titleSmall)
    }
}

data class ProjectConfig(
    val name: String,
    val packageName: String,
    val template: String,
    val minSDK: Int,
    val targetSDK: Int
)
