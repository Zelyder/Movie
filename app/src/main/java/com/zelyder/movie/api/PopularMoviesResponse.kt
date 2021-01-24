package com.zelyder.movie.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PopularMoviesResponse (
	val page: Long,
	@SerialName("results")
	val popularMovies: List<JsonMovie>,

	@SerialName("total_pages")
	val totalPages: Long,

	@SerialName("total_results")
	val totalResults: Long
)

@Serializable
data class JsonMovie (
	val adult: Boolean,

	@SerialName("backdrop_path")
	val backdropPicture: String,

	@SerialName("genre_ids")
	val genreIds: List<Int>,

	val id: Int,

	val overview: String,

	@SerialName("poster_path")
	val posterPicture: String,

	val title: String,

	@SerialName("vote_average")
	val ratings: Float,

	@SerialName("vote_count")
	val votesCount: Int
)

