package com.zelyder.movie.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MoviesResultDto (
	@SerialName("page")
	val page: Long = 1,
	@SerialName("results")
	val popularMovies: List<ListMovieDto> = emptyList(),

	@SerialName("total_pages")
	val totalPages: Long = 0,

	@SerialName("total_results")
	val totalResults: Long = 1
)


