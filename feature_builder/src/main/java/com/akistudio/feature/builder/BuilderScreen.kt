package com.akistudio.feature.builder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File

@Composable
fun BuilderScreen(vm: BuilderViewModel = viewModel()) {
    val state by vm.state.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { vm.buildProject(File("/sdcard/MyProject")) },
                enabled = !state.isBuilding
            ) {
                Text("Build Project")
            }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(Modifier.weight(1f)) {
            items(state.logs.size) { idx ->
                Text(state.logs[idx])
            }
        }
        state.result?.let {
            Spacer(Modifier.height(8.dp))
            Text("Build ${if (it.success) "succeeded" else "failed"}")
            it.apkPath?.let { path -> Text("APK: $path") }
        }
    }
}
