package com.example.rickandmortykmm.feature.character.api

interface CharacterRepository {
    suspend fun getById(id: Int): Result<CharacterDetail>
}
