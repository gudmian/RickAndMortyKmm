package com.example.rickandmortykmm.feature.episodefeed.impl

import com.example.rickandmortykmm.core.network.API_BASE_URL
import com.example.rickandmortykmm.core.network.PagedResponseDto
import com.example.rickandmortykmm.core.network.safeCall
import com.example.rickandmortykmm.feature.episodefeed.api.EpisodeFeedItem
import com.example.rickandmortykmm.feature.episodefeed.api.EpisodeFeedPage
import com.example.rickandmortykmm.feature.episodefeed.api.EpisodeFeedRepository
import com.example.rickandmortykmm.feature.episodefeed.impl.dto.EpisodeFeedItemDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class EpisodeFeedRepositoryImpl(
    private val client: HttpClient,
) : EpisodeFeedRepository {

    override suspend fun getPage(page: Int): Result<EpisodeFeedPage> = safeCall {
        val response: PagedResponseDto<EpisodeFeedItemDto> =
            client.get("$API_BASE_URL/episode") {
                url.parameters.append("page", page.toString())
            }.body()
        EpisodeFeedPage(
            items = response.results.map { it.toDomain() },
            hasNext = response.info.next != null,
        )
    }

    private fun EpisodeFeedItemDto.toDomain(): EpisodeFeedItem = EpisodeFeedItem(
        id = id,
        name = name,
        episode = episode,
        airDate = airDate,
    )
}
