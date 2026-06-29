package com.example.rickandmortykmm.feature.characterfeed.impl.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacterFeedItemDto(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val status: String = "",
    val species: String = "",
)
