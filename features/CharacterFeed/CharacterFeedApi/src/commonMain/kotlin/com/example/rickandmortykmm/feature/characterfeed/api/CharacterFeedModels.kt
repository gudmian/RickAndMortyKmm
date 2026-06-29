package com.example.rickandmortykmm.feature.characterfeed.api

data class CharacterFeedItem(
    val id: Int,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
)

data class CharacterFeedPage(
    val items: List<CharacterFeedItem>,
    val hasNext: Boolean,
)
