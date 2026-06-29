package com.example.rickandmortykmm.core

/**
 * Generic UI state for a single load (used by detail screens and as the
 * base for feed screens which add pagination fields on top).
 */
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Content<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}
