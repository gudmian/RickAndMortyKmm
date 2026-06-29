package com.example.rickandmortykmm.feature.episode.impl.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDto(
    val id: Int = 0,
    val name: String = "",
    @SerialName("air_date") val airDate: String = "",
    val episode: String = "",
    val characters: List<String> = emptyList(),
)
