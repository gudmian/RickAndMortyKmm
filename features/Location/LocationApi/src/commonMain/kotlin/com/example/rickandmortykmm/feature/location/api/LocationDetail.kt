package com.example.rickandmortykmm.feature.location.api

data class LocationDetail(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentIds: List<Int>,
)
