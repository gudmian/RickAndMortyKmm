package com.example.rickandmortykmm.feature.location.impl.di

import com.example.rickandmortykmm.feature.location.api.LocationRepository
import com.example.rickandmortykmm.feature.location.impl.LocationDetailViewModel
import com.example.rickandmortykmm.feature.location.impl.LocationRepositoryImpl
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val locationModule = module {
    single<LocationRepository> { LocationRepositoryImpl(get()) }
    viewModel { params ->
        LocationDetailViewModel(
            locationId = params.get(),
            repository = get(),
            dispatchers = get(),
        )
    }
}
