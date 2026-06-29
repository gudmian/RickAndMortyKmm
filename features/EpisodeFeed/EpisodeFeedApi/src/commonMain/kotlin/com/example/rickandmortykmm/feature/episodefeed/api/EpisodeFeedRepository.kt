package com.example.rickandmortykmm.feature.episodefeed.api

interface EpisodeFeedRepository {
    suspend fun getPage(page: Int): Result<EpisodeFeedPage>
}
