package com.example.rickandmortykmm.core.network

import kotlinx.serialization.json.Json

/** Shared JSON config. `ignoreUnknownKeys` keeps the client resilient to API additions. */
val appJson: Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    explicitNulls = false
}

/** Rick & Morty REST API root. */
const val API_BASE_URL: String = "https://rickandmortyapi.com/api"
