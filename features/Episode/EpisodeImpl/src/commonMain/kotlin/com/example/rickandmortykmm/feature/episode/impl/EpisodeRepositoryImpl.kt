package com.example.rickandmortykmm.feature.episode.impl

import com.example.rickandmortykmm.core.network.API_BASE_URL
import com.example.rickandmortykmm.core.network.safeCall
import com.example.rickandmortykmm.feature.episode.api.EpisodeDetail
import com.example.rickandmortykmm.feature.episode.api.EpisodeRepository
import com.example.rickandmortykmm.feature.episode.impl.dto.EpisodeDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class EpisodeRepositoryImpl(
    private val client: HttpClient,
) : EpisodeRepository {

    override suspend fun getById(id: Int): Result<EpisodeDetail> = safeCall {
        client.get("$API_BASE_URL/episode/$id").body<EpisodeDto>().toDomain()
    }

    private fun EpisodeDto.toDomain(): EpisodeDetail = EpisodeDetail(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        characterIds = characters.mapNotNull { it.substringAfterLast("/").toIntOrNull() },
    )
}
