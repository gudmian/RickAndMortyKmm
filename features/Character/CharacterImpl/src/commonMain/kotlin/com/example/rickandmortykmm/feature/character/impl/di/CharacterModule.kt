package com.example.rickandmortykmm.feature.character.impl.di

import com.example.rickandmortykmm.feature.character.api.CharacterRepository
import com.example.rickandmortykmm.feature.character.impl.CharacterDetailViewModel
import com.example.rickandmortykmm.feature.character.impl.CharacterRepositoryImpl
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterModule = module {
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
    viewModel { params ->
        CharacterDetailViewModel(
            characterId = params.get(),
            repository = get(),
            dispatchers = get(),
        )
    }
}
