package com.example.rickandmortykmm.feature.character.impl.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacterRefDto(
    val name: String = "",
    val url: String = "",
)

@Serializable
data class CharacterDto(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin: CharacterRefDto = CharacterRefDto(),
    val location: CharacterRefDto = CharacterRefDto(),
    val image: String = "",
    val episode: List<String> = emptyList(),
)
