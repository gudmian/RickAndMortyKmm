package com.example.rickandmortykmm.feature.characterfeed.impl

import com.example.rickandmortykmm.core.network.API_BASE_URL
import com.example.rickandmortykmm.core.network.PagedResponseDto
import com.example.rickandmortykmm.core.network.safeCall
import com.example.rickandmortykmm.feature.characterfeed.api.CharacterFeedItem
import com.example.rickandmortykmm.feature.characterfeed.api.CharacterFeedPage
import com.example.rickandmortykmm.feature.characterfeed.api.CharacterFeedRepository
import com.example.rickandmortykmm.feature.characterfeed.impl.dto.CharacterFeedItemDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CharacterFeedRepositoryImpl(
    private val client: HttpClient,
) : CharacterFeedRepository {

    override suspend fun getPage(page: Int): Result<CharacterFeedPage> = safeCall {
        val response: PagedResponseDto<CharacterFeedItemDto> =
            client.get("$API_BASE_URL/character") {
                url.parameters.append("page", page.toString())
            }.body()
        CharacterFeedPage(
            items = response.results.map { it.toDomain() },
            hasNext = response.info.next != null,
        )
    }

    private fun CharacterFeedItemDto.toDomain(): CharacterFeedItem = CharacterFeedItem(
        id = id,
        name = name,
        image = image,
        status = status,
        species = species,
    )
}
