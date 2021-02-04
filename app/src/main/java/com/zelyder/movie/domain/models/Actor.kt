package com.zelyder.movie.domain.models

data class Actor (
    val castId: Int = 0,
    val movieId: Int = 0,
    val name: String = "",
    val orderInCredits: Int = 0,
    val character: String,
    val picture: String = ""
)