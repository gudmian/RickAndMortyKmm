package com.example.rickandmortykmm.feature.character.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

object CharacterRoutes {
    const val DETAIL = "character_detail/{id}"
    fun detail(id: Int) = "character_detail/$id"
}

fun NavGraphBuilder.characterDetailGraph() {
    composable(
        route = CharacterRoutes.DETAIL,
        arguments = listOf(navArgument("id") { type = NavType.IntType }),
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id") ?: 0
        CharacterDetailScreen(characterId = id)
    }
}
