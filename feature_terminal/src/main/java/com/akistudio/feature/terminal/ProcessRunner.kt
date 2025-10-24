package com.akistudio.feature.terminal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ProcessRunner {
    suspend fun run(
        command: List<String>,
        workDir: File,
        onLine: (String) -> Unit
    ): Int = withContext(Dispatchers.IO) {
        val pb = ProcessBuilder(command)
        pb.directory(workDir)
        pb.redirectErrorStream(true)
        val process = pb.start()
        process.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { onLine(it) }
        }
        process.waitFor()
    }
}
