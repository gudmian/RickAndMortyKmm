package com.example.rickandmortykmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform