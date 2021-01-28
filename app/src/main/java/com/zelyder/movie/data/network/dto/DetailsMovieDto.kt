package com.zelyder.movie.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailsMovieDto(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("title")
    val title: String = "",

    @SerialName("overview")
    val overview: String = "",

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("vote_average")
    val rating: Float = 0.0f,

    @SerialName("vote_count")
    val votesCount: Int = 0,

    @SerialName("release_date")
    val releaseDate: String = "",

    @SerialName("runtime")
    val duration: Int = 0,

    @SerialName("genres")
    val genres: List<GenreDto> = emptyList(),

    @SerialName("backdrop_path")
    val backdropPicture: String = "",

    @SerialName("poster_path")
    val posterPicture: String = ""
)