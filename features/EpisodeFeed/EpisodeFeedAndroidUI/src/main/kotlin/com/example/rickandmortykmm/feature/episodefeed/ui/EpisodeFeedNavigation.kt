package com.example.rickandmortykmm.feature.episodefeed.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object EpisodeFeedRoutes {
    const val FEED = "episode_feed"
}

fun NavGraphBuilder.episodeFeedGraph(
    onEpisodeClick: (Int) -> Unit,
) {
    composable(EpisodeFeedRoutes.FEED) {
        EpisodeFeedScreen(onEpisodeClick = onEpisodeClick)
    }
}
