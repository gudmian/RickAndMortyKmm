package com.example.rickandmortykmm.feature.character.impl

import com.example.rickandmortykmm.core.network.API_BASE_URL
import com.example.rickandmortykmm.core.network.safeCall
import com.example.rickandmortykmm.feature.character.api.CharacterDetail
import com.example.rickandmortykmm.feature.character.api.CharacterRepository
import com.example.rickandmortykmm.feature.character.impl.dto.CharacterDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CharacterRepositoryImpl(
    private val client: HttpClient,
) : CharacterRepository {

    override suspend fun getById(id: Int): Result<CharacterDetail> = safeCall {
        client.get("$API_BASE_URL/character/$id").body<CharacterDto>().toDomain()
    }

    private fun CharacterDto.toDomain(): CharacterDetail = CharacterDetail(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        originName = origin.name,
        locationName = location.name,
        image = image,
        episodeIds = episode.mapNotNull { it.substringAfterLast("/").toIntOrNull() },
    )
}
