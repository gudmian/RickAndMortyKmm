package com.example.rickandmortykmm.feature.locationfeed.api

data class LocationFeedItem(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
)

data class LocationFeedPage(
    val items: List<LocationFeedItem>,
    val hasNext: Boolean,
)
