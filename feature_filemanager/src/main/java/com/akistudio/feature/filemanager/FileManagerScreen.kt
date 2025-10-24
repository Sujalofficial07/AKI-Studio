package com.akistudio.feature.filemanager

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FileManagerScreen(vm: FileManagerViewModel = viewModel()) {
    val files by vm.files.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
        onResult = { uri: Uri? -> uri?.let { vm.setRoot(it) } }
    )

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { launcher.launch(null) }) { Text("Open Folder") }
            Button(onClick = { vm.createFile("NewFile.txt") }) { Text("New File") }
            Button(onClick = { vm.createDir("NewFolder") }) { Text("New Folder") }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(files.size) { idx ->
                val f = files[idx]
                Row(
                    Modifier.fillMaxWidth()
                        .clickable { /* open file or navigate */ }
                        .padding(8.dp)
                ) {
                    Text(if (f.isDir) "📁 ${f.name}" else "📄 ${f.name}")
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { vm.delete(f) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}
