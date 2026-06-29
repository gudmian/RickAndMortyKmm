package com.example.rickandmortykmm.feature.episode.api

data class EpisodeDetail(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characterIds: List<Int>,
)
