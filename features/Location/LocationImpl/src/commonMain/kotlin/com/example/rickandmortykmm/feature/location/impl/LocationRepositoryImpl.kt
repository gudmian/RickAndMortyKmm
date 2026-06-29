package com.example.rickandmortykmm.feature.location.impl

import com.example.rickandmortykmm.core.network.API_BASE_URL
import com.example.rickandmortykmm.core.network.safeCall
import com.example.rickandmortykmm.feature.location.api.LocationDetail
import com.example.rickandmortykmm.feature.location.api.LocationRepository
import com.example.rickandmortykmm.feature.location.impl.dto.LocationDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class LocationRepositoryImpl(
    private val client: HttpClient,
) : LocationRepository {

    override suspend fun getById(id: Int): Result<LocationDetail> = safeCall {
        client.get("$API_BASE_URL/location/$id").body<LocationDto>().toDomain()
    }

    private fun LocationDto.toDomain(): LocationDetail = LocationDetail(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residentIds = residents.mapNotNull { it.substringAfterLast("/").toIntOrNull() },
    )
}
