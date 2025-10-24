package com.akistudio.core.model

import java.time.Instant

data class Project(
    val id: String,
    val name: String,
    val path: String,
    val createdAt: Instant = Instant.now(),
    val lastOpenedAt: Instant = Instant.now()
)
