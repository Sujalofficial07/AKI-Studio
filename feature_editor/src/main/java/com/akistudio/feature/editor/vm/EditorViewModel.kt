package com.akistudio.feature.editor.vm

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class EditorState(
    val text: TextFieldValue = TextFieldValue(""),
    val language: String = "kotlin"
)

class EditorViewModel : ViewModel() {
    private val _state = MutableStateFlow(EditorState())
    val state: StateFlow<EditorState> = _state

    fun onChangeText(v: TextFieldValue) {
        _state.value = _state.value.copy(text = v)
    }

    fun setLanguage(lang: String) {
        _state.value = _state.value.copy(language = lang)
    }
}
