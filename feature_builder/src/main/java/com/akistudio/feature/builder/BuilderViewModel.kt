package com.akistudio.feature.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akistudio.feature.terminal.ProcessRunner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

data class BuilderState(
    val logs: List<String> = emptyList(),
    val isBuilding: Boolean = false,
    val result: BuildResult? = null
)

class BuilderViewModel : ViewModel() {
    private val _state = MutableStateFlow(BuilderState())
    val state: StateFlow<BuilderState> = _state

    private val runner = ProcessRunner()

    fun buildProject(root: File) {
        _state.value = BuilderState(isBuilding = true, logs = listOf("Starting build..."))
        viewModelScope.launch {
            val logs = mutableListOf<String>()
            val exit = runner.run(listOf("./gradlew", "assembleRelease"), root) { line ->
                logs += line
                _state.value = _state.value.copy(logs = logs.toList())
            }
            val apk = root.resolve("app/build/outputs/apk/release")
                .walk().firstOrNull { it.extension == "apk" }?.absolutePath
            val result = BuildResult(exit == 0, apk, listOf("Exit code: $exit"))
            _state.value = _state.value.copy(isBuilding = false, result = result, logs = logs)
        }
    }
}
