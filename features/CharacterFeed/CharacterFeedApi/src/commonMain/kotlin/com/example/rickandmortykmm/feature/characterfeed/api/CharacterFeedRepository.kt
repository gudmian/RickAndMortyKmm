package com.example.rickandmortykmm.feature.characterfeed.api

interface CharacterFeedRepository {
    suspend fun getPage(page: Int): Result<CharacterFeedPage>
}
