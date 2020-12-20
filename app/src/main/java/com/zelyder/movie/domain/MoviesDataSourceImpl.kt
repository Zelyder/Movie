package com.zelyder.movie.domain

import android.content.Context
import com.zelyder.movie.data.loadMovies
import com.zelyder.movie.data.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesDataSourceImpl(val context: Context) : MoviesDataSource {

    override suspend fun getMoviesAsync(): List<Movie> =
        withContext(Dispatchers.IO) {
            loadMovies(context)
        }

    override suspend fun getMovieByIdAsync(movieId: Int): Movie? =
        withContext(Dispatchers.IO) {
            loadMovies(context).toMutableList().find { movieId == it.id }
        }

}