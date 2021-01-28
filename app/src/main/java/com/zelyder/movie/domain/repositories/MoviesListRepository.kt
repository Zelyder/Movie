package com.zelyder.movie.domain.repositories

import com.zelyder.movie.domain.models.ListMovie

interface MoviesListRepository {
    suspend fun getMoviesAsync(forceRefresh: Boolean = false): List<ListMovie>
    suspend fun updateMovieIsFavoriteAsync(movieId: Int, isFavorite: Boolean)
}