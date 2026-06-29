package com.example.rickandmortykmm.feature.characterfeed.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object CharacterFeedRoutes {
    const val FEED = "character_feed"
}

fun NavGraphBuilder.characterFeedGraph(
    onCharacterClick: (Int) -> Unit,
) {
    composable(CharacterFeedRoutes.FEED) {
        CharacterFeedScreen(onCharacterClick = onCharacterClick)
    }
}
