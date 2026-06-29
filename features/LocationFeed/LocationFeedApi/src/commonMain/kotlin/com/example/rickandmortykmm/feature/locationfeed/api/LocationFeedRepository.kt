package com.example.rickandmortykmm.feature.locationfeed.api

interface LocationFeedRepository {
    suspend fun getPage(page: Int): Result<LocationFeedPage>
}
