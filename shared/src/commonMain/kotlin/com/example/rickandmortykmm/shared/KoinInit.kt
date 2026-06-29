package com.example.rickandmortykmm.shared

import com.example.rickandmortykmm.core.di.coreModule
import com.example.rickandmortykmm.feature.character.impl.di.characterModule
import com.example.rickandmortykmm.feature.characterfeed.impl.di.characterFeedModule
import com.example.rickandmortykmm.feature.episode.impl.di.episodeModule
import com.example.rickandmortykmm.feature.episodefeed.impl.di.episodeFeedModule
import com.example.rickandmortykmm.feature.location.impl.di.locationModule
import com.example.rickandmortykmm.feature.locationfeed.impl.di.locationFeedModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module

/**
 * All Koin modules in dependency order: infrastructure first, then features.
 * Single source of truth used by both platforms.
 */
fun allKoinModules(): List<Module> = listOf(
    coreModule,
    characterModule,
    characterFeedModule,
    locationModule,
    locationFeedModule,
    episodeModule,
    episodeFeedModule,
)

/** Starts the global Koin context with every feature module. Call once at app launch. */
fun setupKoin(): Unit {
    startKoin { modules(allKoinModules()) }
}
