package com.example.rickandmortykmm

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortykmm.feature.character.ui.CharacterRoutes
import com.example.rickandmortykmm.feature.character.ui.characterDetailGraph
import com.example.rickandmortykmm.feature.characterfeed.ui.CharacterFeedRoutes
import com.example.rickandmortykmm.feature.characterfeed.ui.characterFeedGraph
import com.example.rickandmortykmm.feature.episode.ui.EpisodeRoutes
import com.example.rickandmortykmm.feature.episode.ui.episodeDetailGraph
import com.example.rickandmortykmm.feature.episodefeed.ui.EpisodeFeedRoutes
import com.example.rickandmortykmm.feature.episodefeed.ui.episodeFeedGraph
import com.example.rickandmortykmm.feature.location.ui.LocationRoutes
import com.example.rickandmortykmm.feature.location.ui.locationDetailGraph
import com.example.rickandmortykmm.feature.locationfeed.ui.LocationFeedRoutes
import com.example.rickandmortykmm.feature.locationfeed.ui.locationFeedGraph

private data class Tab(val route: String, val label: String, val icon: String)

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()
    val tabs = listOf(
        Tab(CharacterFeedRoutes.FEED, "Characters", "👥"),
        Tab(LocationFeedRoutes.FEED, "Locations", "📍"),
        Tab(EpisodeFeedRoutes.FEED, "Episodes", "🎬"),
    )

    Scaffold(bottomBar = { AppBottomBar(navController, tabs) }) { padding ->
        NavHost(
            navController = navController,
            startDestination = CharacterFeedRoutes.FEED,
            modifier = Modifier.padding(padding),
        ) {
            characterFeedGraph(onCharacterClick = { id -> navController.navigate(CharacterRoutes.detail(id)) })
            characterDetailGraph()
            locationFeedGraph(onLocationClick = { id -> navController.navigate(LocationRoutes.detail(id)) })
            locationDetailGraph()
            episodeFeedGraph(onEpisodeClick = { id -> navController.navigate(EpisodeRoutes.detail(id)) })
            episodeDetailGraph()
        }
    }
}

@Composable
private fun AppBottomBar(navController: NavController, tabs: List<Tab>) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Text(tab.icon) },
                label = { Text(tab.label) },
            )
        }
    }
}
