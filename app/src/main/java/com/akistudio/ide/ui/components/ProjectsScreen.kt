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
import java.text.SimpleDateFormat
import java.util.*

data class Project(
    val name: String,
    val path: String,
    val lastModified: Date,
    val type: String
)

@Composable
fun ProjectsScreen() {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showPreview by remember { mutableStateOf(false) }
    var selectedProject by remember { mutableStateOf<Project?>(null) }
    var projects by remember {
        mutableStateOf(
            listOf(
                Project("MyFirstApp", "/storage/emulated/0/AkiStudio/MyFirstApp", Date(), "Android App"),
                Project("HelloWorld", "/storage/emulated/0/AkiStudio/HelloWorld", Date(), "Android App")
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "My Projects",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, "Create Project")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (projects.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.FolderOpen,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No projects yet",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Create your first Android project",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(projects) { project ->
                    ProjectCard(
                        project = project,
                        onPreview = {
                            selectedProject = project
                            showPreview = true
                        }
                    )
                }
            }
        }
    }

    if (showCreateDialog) {
        CreateProjectDialog(
            onDismiss = { showCreateDialog = false },
            onCreate = { name, type ->
                projects = projects + Project(name, "/storage/emulated/0/AkiStudio/$name", Date(), type)
                showCreateDialog = false
            }
        )
    }

    if (showPreview && selectedProject != null) {
        AppPreviewScreen(
            projectName = selectedProject!!.name,
            onClose = { showPreview = false }
        )
    }
}

@Composable
fun ProjectCard(
    project: Project,
    onPreview: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Folder,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        project.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        project.type,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(project.lastModified),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Row {
                IconButton(onClick = onPreview) {
                    Icon(Icons.Default.Visibility, "Preview")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.MoreVert, "More options")
                }
            }
        }
    }
}

@Composable
fun CreateProjectDialog(
    onDismiss: () -> Unit,
    onCreate: (String, String) -> Unit
) {
    var projectName by remember { mutableStateOf("") }
    var selectedTemplate by remember { mutableStateOf("Empty Activity") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New Project") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = projectName,
                    onValueChange = { projectName = it },
                    label = { Text("Project Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Select Template:", style = MaterialTheme.typography.titleSmall)
                
                listOf("Empty Activity", "Basic Activity", "Bottom Navigation").forEach { template ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedTemplate = template }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedTemplate == template,
                            onClick = { selectedTemplate = template }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(template)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onCreate(projectName, selectedTemplate) },
                enabled = projectName.isNotBlank()
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
