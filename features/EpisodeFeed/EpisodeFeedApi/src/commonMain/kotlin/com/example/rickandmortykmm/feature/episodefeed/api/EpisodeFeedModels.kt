package com.example.rickandmortykmm.feature.episodefeed.api

data class EpisodeFeedItem(
    val id: Int,
    val name: String,
    val episode: String,
    val airDate: String,
)

data class EpisodeFeedPage(
    val items: List<EpisodeFeedItem>,
    val hasNext: Boolean,
)
