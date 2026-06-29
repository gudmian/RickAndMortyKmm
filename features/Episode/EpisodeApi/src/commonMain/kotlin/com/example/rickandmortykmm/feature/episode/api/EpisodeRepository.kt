package com.example.rickandmortykmm.feature.episode.api

interface EpisodeRepository {
    suspend fun getById(id: Int): Result<EpisodeDetail>
}
