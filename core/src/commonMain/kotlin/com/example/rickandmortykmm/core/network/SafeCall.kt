package com.example.rickandmortykmm.core.network

import kotlin.coroutines.cancellation.CancellationException

/**
 * Wraps a suspending block into [Result], rethrowing [CancellationException] so
 * structured concurrency is not broken. Use at repository boundaries.
 */
suspend fun <T> safeCall(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (c: CancellationException) {
    throw c
} catch (t: Throwable) {
    Result.failure(t)
}
