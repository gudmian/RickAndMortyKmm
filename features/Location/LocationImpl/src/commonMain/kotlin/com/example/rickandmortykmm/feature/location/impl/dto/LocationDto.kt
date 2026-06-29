package com.example.rickandmortykmm.feature.location.impl.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val dimension: String = "",
    val residents: List<String> = emptyList(),
)
