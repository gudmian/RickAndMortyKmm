package com.example.rickandmortykmm.feature.locationfeed.impl

import com.example.rickandmortykmm.core.network.API_BASE_URL
import com.example.rickandmortykmm.core.network.PagedResponseDto
import com.example.rickandmortykmm.core.network.safeCall
import com.example.rickandmortykmm.feature.locationfeed.api.LocationFeedItem
import com.example.rickandmortykmm.feature.locationfeed.api.LocationFeedPage
import com.example.rickandmortykmm.feature.locationfeed.api.LocationFeedRepository
import com.example.rickandmortykmm.feature.locationfeed.impl.dto.LocationFeedItemDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class LocationFeedRepositoryImpl(
    private val client: HttpClient,
) : LocationFeedRepository {

    override suspend fun getPage(page: Int): Result<LocationFeedPage> = safeCall {
        val response: PagedResponseDto<LocationFeedItemDto> =
            client.get("$API_BASE_URL/location") {
                url.parameters.append("page", page.toString())
            }.body()
        LocationFeedPage(
            items = response.results.map { it.toDomain() },
            hasNext = response.info.next != null,
        )
    }

    private fun LocationFeedItemDto.toDomain(): LocationFeedItem = LocationFeedItem(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
    )
}
