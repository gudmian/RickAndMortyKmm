package com.example.rickandmortykmm.feature.locationfeed.impl.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationFeedItemDto(
    val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val dimension: String = "",
)
