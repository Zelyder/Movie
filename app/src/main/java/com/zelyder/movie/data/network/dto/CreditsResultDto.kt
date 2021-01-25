package com.zelyder.movie.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResultDto(
    @SerialName("id")
    val id: Int,
    @SerialName("cast")
    val cast: List<CastDto>
)


