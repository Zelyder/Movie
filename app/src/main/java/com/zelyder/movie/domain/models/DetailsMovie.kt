package com.zelyder.movie.domain.models

data class DetailsMovie(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val poster: String = "",
    val backdrop: String = "",
    val ratings: Float = 0.0f,
    val numberOfRatings: Int = 0,
    val minimumAge: String = "",
    val runtime: Int = 0,
    val releaseDate: String = "",
    val genres: String = "",
    val actors: List<Actor> = emptyList(),
    var isFavorite: Boolean = false
)