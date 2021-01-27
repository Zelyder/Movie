package com.zelyder.movie.domain.repositories

import com.zelyder.movie.domain.models.DetailsMovie

interface MovieDetailsRepository {
    suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean = false): DetailsMovie
}