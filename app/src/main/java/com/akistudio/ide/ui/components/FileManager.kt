package com.akistudio.ide.ui.components

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.File

data class FileItem(
    val name: String,
    val path: String,
    val isDirectory: Boolean,
    val size: Long = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileManagerScreen() {
    val context = LocalContext.current
    var currentPath by remember { mutableStateOf("/storage/emulated/0/AkiStudio") }
    var fileItems by remember { mutableStateOf<List<FileItem>>(emptyList()) }
    var selectedFile by remember { mutableStateOf<FileItem?>(null) }
    var showFileEditor by remember { mutableStateOf(false) }
    var showCreateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(currentPath) {
        fileItems = loadFiles(currentPath)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("File Manager") },
                navigationIcon = {
                    if (currentPath != "/storage/emulated/0/AkiStudio") {
                        IconButton(onClick = {
                            currentPath = File(currentPath).parent ?: "/storage/emulated/0/AkiStudio"
                        }) {
                            Icon(Icons.Default.ArrowBack, "Back")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { showCreateDialog = true }) {
                        Icon(Icons.Default.Add, "Create File/Folder")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Current Path Display
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = currentPath,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // File List
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(fileItems) { file ->
                    FileItemCard(
                        file = file,
                        onClick = {
                            if (file.isDirectory) {
                                currentPath = file.path
                            } else {
                                selectedFile = file
                                showFileEditor = true
                            }
                        },
                        onDelete = {
                            File(file.path).delete()
                            fileItems = loadFiles(currentPath)
                        }
                    )
                }
            }
        }
    }

    // File Editor Dialog
    if (showFileEditor && selectedFile != null) {
        FileEditorDialog(
            file = selectedFile!!,
            onDismiss = { showFileEditor = false },
            onSave = { content ->
                saveFile(selectedFile!!.path, content)
                showFileEditor = false
            }
        )
    }

    // Create File/Folder Dialog
    if (showCreateDialog) {
        CreateFileDialog(
            currentPath = currentPath,
            onDismiss = { showCreateDialog = false },
            onCreate = { name, isFolder ->
                val newPath = "$currentPath/$name"
                if (isFolder) {
                    File(newPath).mkdirs()
                } else {
                    File(newPath).createNewFile()
                }
                fileItems = loadFiles(currentPath)
                showCreateDialog = false
            }
        )
    }
}

@Composable
fun FileItemCard(
    file: FileItem,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (file.isDirectory) Icons.Default.Folder else Icons.Default.Description,
                contentDescription = null,
                tint = if (file.isDirectory) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = file.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (!file.isDirectory) {
                    Text(
                        text = formatFileSize(file.size),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = { showMenu = true }) {
                Icon(Icons.Default.MoreVert, "More")
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        onDelete()
                        showMenu = false
                    },
                    leadingIcon = { Icon(Icons.Default.Delete, null) }
                )
            }
        }
    }
}

@Composable
fun FileEditorDialog(
    file: FileItem,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var content by remember { mutableStateOf(readFile(file.path)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(file.name) },
        text = {
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            )
        },
        confirmButton = {
            Button(onClick = { onSave(content) }) {
                Text("Save")
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
fun CreateFileDialog(
    currentPath: String,
    onDismiss: () -> Unit,
    onCreate: (String, Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var isFolder by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isFolder,
                        onCheckedChange = { isFolder = it }
                    )
                    Text("Create as folder")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onCreate(name, isFolder) },
                enabled = name.isNotBlank()
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

// Helper Functions
private fun loadFiles(path: String): List<FileItem> {
    val directory = File(path)
    if (!directory.exists()) {
        directory.mkdirs()
    }
    return directory.listFiles()?.map { file ->
        FileItem(
            name = file.name,
            path = file.absolutePath,
            isDirectory = file.isDirectory,
            size = if (file.isFile) file.length() else 0
        )
    }?.sortedWith(compareBy({ !it.isDirectory }, { it.name })) ?: emptyList()
}

private fun readFile(path: String): String {
    return try {
        File(path).readText()
    } catch (e: Exception) {
        ""
    }
}

private fun saveFile(path: String, content: String) {
    try {
        File(path).writeText(content)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun formatFileSize(size: Long): String {
    return when {
        size < 1024 -> "$size B"
        size < 1024 * 1024 -> "${size / 1024} KB"
        else -> "${size / (1024 * 1024)} MB"
    }
}
