package com.zelyder.movie

data class Movie(
    val id: Int,
    val title: String,
    val smallCoverImg: Int,
    val ageRating: String,
    val isFavorite: Boolean,
    val genres: String,
    val rating: Float,
    val reviewsCount: Int,
    val duration: Int,
)