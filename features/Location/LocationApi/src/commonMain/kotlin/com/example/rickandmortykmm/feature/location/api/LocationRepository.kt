package com.example.rickandmortykmm.feature.location.api

interface LocationRepository {
    suspend fun getById(id: Int): Result<LocationDetail>
}
