package com.example.rickandmortykmm.feature.locationfeed.impl.di

import com.example.rickandmortykmm.feature.locationfeed.api.LocationFeedRepository
import com.example.rickandmortykmm.feature.locationfeed.impl.LocationFeedRepositoryImpl
import com.example.rickandmortykmm.feature.locationfeed.impl.LocationFeedViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val locationFeedModule = module {
    single<LocationFeedRepository> { LocationFeedRepositoryImpl(get()) }
    viewModel { LocationFeedViewModel(get(), get()) }
}
