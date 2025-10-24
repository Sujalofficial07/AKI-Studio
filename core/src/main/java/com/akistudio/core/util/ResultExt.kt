package com.akistudio.core.util

inline fun <T> runCatchingLog(block: () -> T, onError: (Throwable) -> Unit = {}): Result<T> {
    return try {
        Result.success(block())
    } catch (t: Throwable) {
        onError(t)
        Result.failure(t)
    }
}

fun Throwable.rootCause(): Throwable {
    var cause: Throwable = this
    while (cause.cause != null) {
        cause = cause.cause!!
    }
    return cause
}
