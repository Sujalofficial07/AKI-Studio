package com.akistudio.core.data

import com.akistudio.core.model.Project
import com.akistudio.core.util.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.UUID

class ProjectRepository {

    suspend fun createProject(basePath: String, name: String): Project = withContext(Dispatchers.IO) {
        val id = UUID.randomUUID().toString()
        val path = "$basePath/$name"
        FileUtils.ensureDir(path)
        Project(
            id = id,
            name = name,
            path = path,
            createdAt = Instant.now(),
            lastOpenedAt = Instant.now()
        )
    }

    suspend fun listProjects(basePath: String): List<Project> = withContext(Dispatchers.IO) {
        FileUtils.listFiles(basePath)
            .filter { it.isDirectory }
            .map { dir ->
                Project(
                    id = dir.name,
                    name = dir.name,
                    path = dir.absolutePath,
                    createdAt = Instant.ofEpochMilli(dir.lastModified()),
                    lastOpenedAt = Instant.ofEpochMilli(dir.lastModified())
                )
            }
    }

    suspend fun deleteProject(path: String): Boolean = withContext(Dispatchers.IO) {
        val dir = java.io.File(path)
        if (!dir.exists()) return@withContext false
        dir.deleteRecursively()
    }
}
