package com.zelyder.movie.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class GenresResultDto (
    @SerialName("genres")
    val genres: List<GenreDto> = emptyList()
)