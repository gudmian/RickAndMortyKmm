package com.example.rickandmortykmm.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Bridges a Kotlin [StateFlow] to Swift/UIKit: a non-suspending callback is
 * invoked on every emitted value. Returns the [Job] so the Swift holder can
 * cancel the subscription (and free the coroutine) on deinit.
 */
fun <T> StateFlow<T>.subscribe(scope: CoroutineScope, onEach: (T) -> Unit): Job =
    scope.launch { collect(onEach) }
