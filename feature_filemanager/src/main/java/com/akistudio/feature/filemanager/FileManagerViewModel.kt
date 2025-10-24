package com.akistudio.feature.filemanager

import android.app.Application
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class FileItem(val name: String, val isDir: Boolean, val uri: Uri)

class FileManagerViewModel(app: Application) : AndroidViewModel(app) {
    private val _files = MutableStateFlow<List<FileItem>>(emptyList())
    val files: StateFlow<List<FileItem>> = _files

    private var root: DocumentFile? = null

    fun setRoot(uri: Uri) {
        val ctx = getApplication<Application>()
        root = DocumentFile.fromTreeUri(ctx, uri)
        refresh()
    }

    fun refresh() {
        _files.value = root?.listFiles()?.map {
            FileItem(it.name ?: "?", it.isDirectory, it.uri)
        } ?: emptyList()
    }

    fun createFile(name: String, mime: String = "text/plain") {
        root?.createFile(mime, name)
        refresh()
    }

    fun createDir(name: String) {
        root?.createDirectory(name)
        refresh()
    }

    fun delete(item: FileItem) {
        val ctx = getApplication<Application>()
        DocumentFile.fromSingleUri(ctx, item.uri)?.delete()
        refresh()
    }
}
