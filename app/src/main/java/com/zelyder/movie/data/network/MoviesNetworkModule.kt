package com.zelyder.movie.data.network

import com.zelyder.movie.BuildConfig
import com.zelyder.movie.domain.models.Actor
import com.zelyder.movie.domain.models.Genre
import com.zelyder.movie.domain.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zelyder.movie.data.network.apis.MovieApi
import com.zelyder.movie.data.network.dto.MovieDto
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create


private val jsonFormat = Json {
    ignoreUnknownKeys = true }


private suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {
    val genres = RetrofitModule.movieApi.getGenres().genres
    genres.map { Genre(id = it.id, name = it.name) }
}

private suspend fun loadActors(movieId: Int): List<Actor> = withContext(Dispatchers.IO) {
    val cast = RetrofitModule.movieApi.getCredits(movieId).cast
    cast.map { Actor(id = it.id, name = it.name,
        picture = BuildConfig.BASE_IMAGE_URL + "w342/"+ it.profilePicture) }
}

private fun normalizedRating(rating: Float?): Float {
    return (rating ?: 1f) / 2
}

@Suppress("unused")
internal suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres()
    val data = RetrofitModule.movieApi.getPopularMovies().popularMovies

    parseMovies(data, genresMap)
}

private object RetrofitModule {
    @ExperimentalSerializationApi
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(jsonFormat.asConverterFactory("application/json".toMediaType()))
        .build()

    val movieApi: MovieApi = retrofit.create()
}

internal suspend fun parseMovies(
    moviesDto: List<MovieDto>,
    genres: List<Genre>
): List<Movie> = withContext(Dispatchers.IO) {
    val genresMap = genres.associateBy { it.id }

    moviesDto.map { movie ->
        @Suppress("unused")
        (Movie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            poster = BuildConfig.BASE_IMAGE_URL + "w342/" + movie.posterPicture,
            backdrop = BuildConfig.BASE_IMAGE_URL + "w342/" + movie.backdropPicture,
            ratings = normalizedRating(movie.ratings),
            numberOfRatings = movie.votesCount,
            minimumAge = if (movie.adult) 16 else 13,
            releaseDate = movie.releaseDate,
            genres = movie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            },
            actors = loadActors(movie.id),
            isFavorite = false
        ))
    }
}