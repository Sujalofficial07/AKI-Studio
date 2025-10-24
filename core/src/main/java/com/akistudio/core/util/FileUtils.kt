package com.akistudio.core.util

import java.io.File

object FileUtils {
    fun exists(path: String): Boolean = File(path).exists()

    fun ensureDir(path: String): Boolean {
        val dir = File(path)
        return if (dir.exists()) dir.isDirectory else dir.mkdirs()
    }

    fun listFiles(path: String): List<File> {
        val dir = File(path)
        if (!dir.exists() || !dir.isDirectory) return emptyList()
        return dir.listFiles()?.toList().orEmpty()
    }
}
