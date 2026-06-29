package com.example.rickandmortykmm.shared

import com.example.rickandmortykmm.feature.character.impl.CharacterDetailViewModel
import com.example.rickandmortykmm.feature.characterfeed.impl.CharacterFeedViewModel
import com.example.rickandmortykmm.feature.episode.impl.EpisodeDetailViewModel
import com.example.rickandmortykmm.feature.episodefeed.impl.EpisodeFeedViewModel
import com.example.rickandmortykmm.feature.location.impl.LocationDetailViewModel
import com.example.rickandmortykmm.feature.locationfeed.impl.LocationFeedViewModel
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform

/**
 * Top-level entry points for Swift/UIKit to obtain ViewModels from the global
 * Koin context. Exposed as Swift free functions (avoids object-access ambiguity).
 */
fun characterFeedViewModel(): CharacterFeedViewModel = KoinPlatform.getKoin().get()
fun characterDetailViewModel(id: Int): CharacterDetailViewModel = KoinPlatform.getKoin().get { parametersOf(id) }

fun locationFeedViewModel(): LocationFeedViewModel = KoinPlatform.getKoin().get()
fun locationDetailViewModel(id: Int): LocationDetailViewModel = KoinPlatform.getKoin().get { parametersOf(id) }

fun episodeFeedViewModel(): EpisodeFeedViewModel = KoinPlatform.getKoin().get()
fun episodeDetailViewModel(id: Int): EpisodeDetailViewModel = KoinPlatform.getKoin().get { parametersOf(id) }
