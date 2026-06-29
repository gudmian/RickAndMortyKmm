package com.example.rickandmortykmm.feature.locationfeed.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object LocationFeedRoutes {
    const val FEED = "location_feed"
}

fun NavGraphBuilder.locationFeedGraph(
    onLocationClick: (Int) -> Unit,
) {
    composable(LocationFeedRoutes.FEED) {
        LocationFeedScreen(onLocationClick = onLocationClick)
    }
}
