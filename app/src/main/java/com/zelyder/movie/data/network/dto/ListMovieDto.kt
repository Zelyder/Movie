package com.zelyder.movie.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListMovieDto (
    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPicture: String = "",

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("id")
    val id: Int = 0,

    @SerialName("overview")
    val overview: String = "",

    @SerialName("poster_path")
    val posterPicture: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("vote_average")
    val rating: Float = 0.0f,

    @SerialName("vote_count")
    val votesCount: Int = 0,

    @SerialName("release_date")
    val releaseDate: String = ""
)