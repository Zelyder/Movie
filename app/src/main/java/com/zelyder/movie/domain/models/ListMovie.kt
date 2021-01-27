package com.zelyder.movie.domain.models

data class ListMovie(
    val id: Int = 0,
    val title: String = "",
    val poster: String = "",
    val ratings: Float = 0.0f,
    val numberOfRatings: Int = 0,
    val minimumAge: String = "",
    val releaseDate: String = "",
    val genres: String = "",
    var isFavorite: Boolean = false
)