package com.example.rickandmortykmm.feature.location.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

object LocationRoutes {
    const val DETAIL = "location_detail/{id}"
    fun detail(id: Int) = "location_detail/$id"
}

fun NavGraphBuilder.locationDetailGraph() {
    composable(
        route = LocationRoutes.DETAIL,
        arguments = listOf(navArgument("id") { type = NavType.IntType }),
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id") ?: 0
        LocationDetailScreen(locationId = id)
    }
}
