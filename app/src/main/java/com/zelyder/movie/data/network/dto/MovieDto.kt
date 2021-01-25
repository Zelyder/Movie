package com.zelyder.movie.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto (
    @SerialName("adult")
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPicture: String,

    @SerialName("genre_ids")
    val genreIds: List<Int>,

    @SerialName("id")
    val id: Int,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val posterPicture: String,

    @SerialName("title")
    val title: String,

    @SerialName("vote_average")
    val ratings: Float,

    @SerialName("vote_count")
    val votesCount: Int,

    @SerialName("release_date")
    val releaseDate: String = ""
)