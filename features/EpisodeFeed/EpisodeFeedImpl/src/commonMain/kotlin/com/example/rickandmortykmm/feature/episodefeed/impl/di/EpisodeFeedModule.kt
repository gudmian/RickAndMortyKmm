package com.example.rickandmortykmm.feature.episodefeed.impl.di

import com.example.rickandmortykmm.feature.episodefeed.api.EpisodeFeedRepository
import com.example.rickandmortykmm.feature.episodefeed.impl.EpisodeFeedRepositoryImpl
import com.example.rickandmortykmm.feature.episodefeed.impl.EpisodeFeedViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val episodeFeedModule = module {
    single<EpisodeFeedRepository> { EpisodeFeedRepositoryImpl(get()) }
    viewModel { EpisodeFeedViewModel(get(), get()) }
}
