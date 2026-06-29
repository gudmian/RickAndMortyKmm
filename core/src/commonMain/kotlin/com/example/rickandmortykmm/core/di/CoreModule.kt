package com.example.rickandmortykmm.core.di

import com.example.rickandmortykmm.core.DefaultDispatcherProvider
import com.example.rickandmortykmm.core.DispatcherProvider
import com.example.rickandmortykmm.core.network.httpClient
import org.koin.dsl.module

val coreModule = module {
    single { httpClient() }
    single<DispatcherProvider> { DefaultDispatcherProvider() }
}
