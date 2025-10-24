package com.akistudio.feature.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akistudio.feature.editor.vm.EditorViewModel

@Composable
fun EditorScreen(vm: EditorViewModel = hiltViewModel()) {
    val state by vm.state.collectAsState()

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        CodeArea(
            value = state.text,
            onChange = vm::onChangeText,
            language = state.language
        )
    }
}

@Composable
private fun CodeArea(value: TextFieldValue, onChange: (TextFieldValue) -> Unit, language: String) {
    val vScroll = rememberScrollState()
    val hScroll = rememberScrollState()

    val highlighted = remember(value.text, language) { highlight(value.text, language) }

    Row(Modifier.fillMaxSize()) {
        // Line numbers
        Column(Modifier.width(48.dp).verticalScroll(vScroll)) {
            val lines = value.text.lines().size
            for (i in 1..lines) Text(i.toString(), color = Color.Gray)
        }
        // Code area
        Column(
            Modifier.weight(1f)
                .verticalScroll(vScroll)
                .horizontalScroll(hScroll)
                .padding(8.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.fillMaxSize()
            )
            // Overlay highlighted text (simplified)
            Text(highlighted)
        }
    }
}

private fun highlight(src: String, lang: String): AnnotatedString {
    return buildAnnotatedString {
        append(src)
        val keywords = when (lang) {
            "kotlin" -> listOf("fun", "class", "val", "var", "if", "else", "import", "package", "return")
            "xml" -> listOf("<", ">", "/>", "</", "android:")
            "json" -> listOf("{", "}", "[", "]", ":")
            else -> emptyList()
        }
        keywords.forEach { k ->
            var index = src.indexOf(k)
            while (index >= 0) {
                addStyle(SpanStyle(color = Color(0xFF00E5FF)), index, index + k.length)
                index = src.indexOf(k, index + k.length)
            }
        }
    }
}
