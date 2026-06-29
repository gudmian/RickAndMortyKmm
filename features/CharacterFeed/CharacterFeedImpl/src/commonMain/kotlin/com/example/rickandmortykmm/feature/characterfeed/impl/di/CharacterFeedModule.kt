package com.example.rickandmortykmm.feature.characterfeed.impl.di

import com.example.rickandmortykmm.feature.characterfeed.api.CharacterFeedRepository
import com.example.rickandmortykmm.feature.characterfeed.impl.CharacterFeedRepositoryImpl
import com.example.rickandmortykmm.feature.characterfeed.impl.CharacterFeedViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterFeedModule = module {
    single<CharacterFeedRepository> { CharacterFeedRepositoryImpl(get()) }
    viewModel { CharacterFeedViewModel(get(), get()) }
}
