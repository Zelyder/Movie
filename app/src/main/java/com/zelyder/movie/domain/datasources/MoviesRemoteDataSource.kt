package com.zelyder.movie.domain.datasources

import com.zelyder.movie.data.network.dto.CreditsResultDto
import com.zelyder.movie.data.network.dto.DetailsMovieDto
import com.zelyder.movie.data.network.dto.GenresResultDto
import com.zelyder.movie.data.network.dto.MoviesResultDto

interface MoviesRemoteDataSource {
    suspend fun getMoviesAsync(): MoviesResultDto
    suspend fun getMovieByIdAsync(movieId: Int): DetailsMovieDto
    suspend fun getGenresAsync(): GenresResultDto
    suspend fun getCreditsByMovieId(movieId: Int): CreditsResultDto
}