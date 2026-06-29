package com.example.rickandmortykmm.feature.episode.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

object EpisodeRoutes {
    const val DETAIL = "episode_detail/{id}"
    fun detail(id: Int) = "episode_detail/$id"
}

fun NavGraphBuilder.episodeDetailGraph() {
    composable(
        route = EpisodeRoutes.DETAIL,
        arguments = listOf(navArgument("id") { type = NavType.IntType }),
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id") ?: 0
        EpisodeDetailScreen(episodeId = id)
    }
}
