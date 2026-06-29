package com.example.rickandmortykmm.core.network

import io.ktor.client.HttpClient

/**
 * Platform-specific [HttpClient] (OkHttp on Android, Darwin on iOS) sharing the
 * same JSON negotiation and timeout configuration.
 */
expect fun httpClient(): HttpClient
