package com.zelyder.movie.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int = 0,
    val releaseDate: String = "",
    val genres: List<Genre>,
    val actors: List<Actor>,
    var isFavorite: Boolean,

)