package com.zelyder.movie.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("cast_id")
    val castId: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("order")
    val orderInCredits: Int = 0,
    @SerialName("character")
    val character: String = "",
    @SerialName("profile_path")
    val profilePicture: String? = null,
)