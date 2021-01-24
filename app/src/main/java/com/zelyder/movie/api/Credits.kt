package com.zelyder.movie.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credits(
    val id: Int,
    @SerialName("cast")
    val cast: List<JsonActor>
)

@Serializable
data class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String? = null,

)
