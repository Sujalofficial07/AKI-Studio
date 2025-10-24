package com.akistudio.feature.terminal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

data class TerminalState(
    val lines: List<String> = emptyList(),
    val isRunning: Boolean = false
)

class TerminalViewModel : ViewModel() {
    private val _state = MutableStateFlow(TerminalState())
    val state: StateFlow<TerminalState> = _state

    private val runner = ProcessRunner()

    fun runCommand(cmd: String, workDir: File) {
        _state.value = _state.value.copy(isRunning = true, lines = _state.value.lines + "> $cmd")
        viewModelScope.launch {
            val exit = runner.run(cmd.split(" "), workDir) { line ->
                _state.value = _state.value.copy(lines = _state.value.lines + line)
            }
            _state.value = _state.value.copy(
                isRunning = false,
                lines = _state.value.lines + "[exit $exit]"
            )
        }
    }
}
