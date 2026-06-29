package com.example.rickandmortykmm.feature.episodefeed.impl.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeFeedItemDto(
    val id: Int = 0,
    val name: String = "",
    val episode: String = "",
    @SerialName("air_date") val airDate: String = "",
)
