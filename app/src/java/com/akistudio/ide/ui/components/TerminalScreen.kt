package com.akistudio.ide.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TerminalLine(
    val text: String,
    val isCommand: Boolean = false
)

@Composable
fun TerminalScreen() {
    var terminalLines by remember {
        mutableStateOf(
            listOf(
                TerminalLine("Aki Studio Terminal v1.0.0"),
                TerminalLine("Type 'help' for available commands"),
                TerminalLine("")
            )
        )
    }
    var currentCommand by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Terminal Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Terminal",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Row {
                    IconButton(onClick = {
                        terminalLines = terminalLines + TerminalLine("")
                    }) {
                        Icon(Icons.Default.Clear, "Clear Terminal")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Settings, "Terminal Settings")
                    }
                }
            }
        }

        // Terminal Output
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(terminalLines) { line ->
                Text(
                    text = if (line.isCommand) "$ ${line.text}" else line.text,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        color = if (line.isCommand) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }

        // Command Input
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "$",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = currentCommand,
                    onValueChange = { currentCommand = it },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    singleLine = true
                )
                IconButton(
                    onClick = {
                        if (currentCommand.isNotBlank()) {
                            terminalLines = terminalLines + TerminalLine(currentCommand, true)
                            
                            // Process command
                            val output = when (currentCommand.trim()) {
                                "help" -> "Available commands:\n  build - Build project\n  run - Run application\n  clean - Clean build files\n  version - Show version\n  clear - Clear terminal"
                                "build" -> "Building project...\nBUILD SUCCESSFUL in 2s"
                                "run" -> "Starting application...\nApp launched successfully"
                                "clean" -> "Cleaning build files...\nClean completed"
                                "version" -> "Aki Studio v1.0.0\nAndroid SDK 34"
                                "clear" -> {
                                    terminalLines = listOf(
                                        TerminalLine("Aki Studio Terminal v1.0.0"),
                                        TerminalLine("Type 'help' for available commands"),
                                        TerminalLine("")
                                    )
                                    currentCommand = ""
                                    return@IconButton
                                }
                                else -> "Command not found: $currentCommand"
                            }
                            
                            terminalLines = terminalLines + TerminalLine(output)
                            currentCommand = ""
                        }
                    }
                ) {
                    Icon(Icons.Default.Send, "Execute Command")
                }
            }
        }
    }
