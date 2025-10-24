package com.akistudio.feature.builder

data class BuildResult(
    val success: Boolean,
    val apkPath: String?,
    val summary: List<String>
)
