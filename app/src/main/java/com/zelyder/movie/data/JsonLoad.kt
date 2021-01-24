package com.zelyder.movie.data

import com.zelyder.movie.BuildConfig
import com.zelyder.movie.data.models.Actor
import com.zelyder.movie.data.models.Genre
import com.zelyder.movie.data.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zelyder.movie.api.MovieApi
import com.zelyder.movie.api.JsonMovie
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create


private val jsonFormat = Json {
    ignoreUnknownKeys = true }

@Serializable
class JsonGenre(val id: Int, val name: String)

private suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {
    val jsonGenres = RetrofitModule.movieApi.getGenres().genres
    jsonGenres.map { Genre(id = it.id, name = it.name) }
}

private suspend fun loadActors(movieId: Int): List<Actor> = withContext(Dispatchers.IO) {
    val jsonActors = RetrofitModule.movieApi.getCredits(movieId).cast
    jsonActors.map { Actor(id = it.id, name = it.name,
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
    jsonMovies: List<JsonMovie>,
    genres: List<Genre>
): List<Movie> = withContext(Dispatchers.IO) {
    val genresMap = genres.associateBy { it.id }

    jsonMovies.map { jsonMovie ->
        @Suppress("unused")
        (Movie(
            id = jsonMovie.id,
            title = jsonMovie.title,
            overview = jsonMovie.overview,
            poster = BuildConfig.BASE_IMAGE_URL + "w342/" + jsonMovie.posterPicture,
            backdrop = BuildConfig.BASE_IMAGE_URL + "w342/" + jsonMovie.backdropPicture,
            ratings = normalizedRating(jsonMovie.ratings),
            numberOfRatings = jsonMovie.votesCount,
            minimumAge = if (jsonMovie.adult) 16 else 13,
            runtime = 0,
            genres = jsonMovie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            },
            actors = loadActors(jsonMovie.id),
            isFavorite = false
        ))
    }
}