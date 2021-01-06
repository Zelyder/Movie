package com.zelyder.movie.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.zelyder.movie.data.JsonGenre


@Serializable
class GenresResponse (
    @SerialName("genres")
    val genres: List<JsonGenre>
)