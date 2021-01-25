package com.zelyder.movie.domain

import com.zelyder.movie.domain.models.Movie

interface MoviesDataSource {
    suspend fun getMoviesAsync(): List<Movie>
    suspend fun getMovieByIdAsync(movieId: Int): Movie?
}