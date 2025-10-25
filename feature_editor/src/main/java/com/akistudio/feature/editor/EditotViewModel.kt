package com.akistudio.feature.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

data class EditorState(
    val openFiles: List<OpenFile> = emptyList(),
    val currentFileIndex: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDirty: Boolean = false,
    val theme: EditorTheme = EditorTheme.NEON_BLUE
)

data class OpenFile(
    val path: String,
    val name: String,
    val content: String,
    val language: Language,
    val cursorPosition: Int = 0
)

enum class Language {
    KOTLIN, XML, JSON, JAVA, GRADLE, MARKDOWN, PLAIN_TEXT
}

enum class EditorTheme {
    ONE_DARK, DRACULA, NEON_BLUE, SOLARIZED_DARK
}

class EditorViewModel : ViewModel() {
    private val _state = MutableStateFlow(EditorState())
    val state: StateFlow<EditorState> = _state.asStateFlow()

    fun openFile(file: File) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                
                val content = file.readText()
                val language = detectLanguage(file.extension)
                
                val openFile = OpenFile(
                    path = file.absolutePath,
                    name = file.name,
                    content = content,
                    language = language
                )
                
                val currentFiles = _state.value.openFiles.toMutableList()
                val existingIndex = currentFiles.indexOfFirst { it.path == file.absolutePath }
                
                if (existingIndex >= 0) {
                    _state.value = _state.value.copy(
                        currentFileIndex = existingIndex,
                        isLoading = false
                    )
                } else {
                    currentFiles.add(openFile)
                    _state.value = _state.value.copy(
                        openFiles = currentFiles,
                        currentFileIndex = currentFiles.size - 1,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to open file: ${e.message}"
                )
            }
        }
    }

    fun saveCurrentFile() {
        viewModelScope.launch {
            try {
                val currentFile = _state.value.openFiles.getOrNull(_state.value.currentFileIndex)
                    ?: return@launch
                
                File(currentFile.path).writeText(currentFile.content)
                _state.value = _state.value.copy(isDirty = false, error = null)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Failed to save: ${e.message}")
            }
        }
    }

    fun updateContent(newContent: String) {
        val currentIndex = _state.value.currentFileIndex
        val currentFiles = _state.value.openFiles.toMutableList()
        
        if (currentIndex < currentFiles.size) {
            currentFiles[currentIndex] = currentFiles[currentIndex].copy(content = newContent)
            _state.value = _state.value.copy(
                openFiles = currentFiles,
                isDirty = true
            )
        }
    }

    fun closeFile(index: Int) {
        val currentFiles = _state.value.openFiles.toMutableList()
        if (index < currentFiles.size) {
            currentFiles.removeAt(index)
            val newIndex = if (currentFiles.isEmpty()) 0 
                          else if (index <= _state.value.currentFileIndex) 
                              (_state.value.currentFileIndex - 1).coerceAtLeast(0)
                          else _state.value.currentFileIndex
            
            _state.value = _state.value.copy(
                openFiles = currentFiles,
                currentFileIndex = newIndex
            )
        }
    }

    fun switchToFile(index: Int) {
        if (index in _state.value.openFiles.indices) {
            _state.value = _state.value.copy(currentFileIndex = index)
        }
    }

    fun changeTheme(theme: EditorTheme) {
        _state.value = _state.value.copy(theme = theme)
    }

    private fun detectLanguage(extension: String): Language {
        return when (extension.lowercase()) {
            "kt", "kts" -> Language.KOTLIN
            "xml" -> Language.XML
            "json" -> Language.JSON
            "java" -> Language.JAVA
            "gradle" -> Language.GRADLE
            "md" -> Language.MARKDOWN
            else -> Language.PLAIN_TEXT
        }
    }

    fun getCurrentFile(): OpenFile? {
        return _state.value.openFiles.getOrNull(_state.value.currentFileIndex)
    }
}
