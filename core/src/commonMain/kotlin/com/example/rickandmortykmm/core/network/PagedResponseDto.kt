package com.example.rickandmortykmm.core.network

import kotlinx.serialization.Serializable

/** `info` block of a paginated Rick & Morty list response. */
@Serializable
data class PageInfoDto(
    val count: Int? = null,
    val pages: Int? = null,
    val next: String? = null,
    val prev: String? = null,
)

/** Generic envelope: `{ "info": {...}, "results": [...] }`. */
@Serializable
data class PagedResponseDto<T>(
    val info: PageInfoDto = PageInfoDto(),
    val results: List<T> = emptyList(),
)
