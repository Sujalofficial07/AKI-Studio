package com.akistudio.feature.terminal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File

@Composable
fun TerminalScreen(vm: TerminalViewModel = viewModel()) {
    val state by vm.state.collectAsState()
    var input by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(state.lines.size) { idx ->
                Text(state.lines[idx])
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BasicTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f).padding(8.dp)
            )
            Button(
                onClick = {
                    if (input.isNotBlank()) {
                        vm.runCommand(input, File("/sdcard")) // Example workDir
                        input = ""
                    }
                },
                enabled = !state.isRunning
            ) {
                Text("Run")
            }
        }
    }
}
