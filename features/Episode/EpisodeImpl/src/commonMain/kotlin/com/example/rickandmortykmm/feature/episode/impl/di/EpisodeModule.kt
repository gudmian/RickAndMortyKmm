package com.example.rickandmortykmm.feature.episode.impl.di

import com.example.rickandmortykmm.feature.episode.api.EpisodeRepository
import com.example.rickandmortykmm.feature.episode.impl.EpisodeDetailViewModel
import com.example.rickandmortykmm.feature.episode.impl.EpisodeRepositoryImpl
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val episodeModule = module {
    single<EpisodeRepository> { EpisodeRepositoryImpl(get()) }
    viewModel { params ->
        EpisodeDetailViewModel(
            episodeId = params.get(),
            repository = get(),
            dispatchers = get(),
        )
    }
}
