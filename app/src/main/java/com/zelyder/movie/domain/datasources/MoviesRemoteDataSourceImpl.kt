package com.zelyder.movie.domain.datasources

import com.zelyder.movie.data.network.apis.MovieApi
import com.zelyder.movie.data.network.dto.*

class MoviesRemoteDataSourceImpl(private val moviesApi: MovieApi) : MoviesRemoteDataSource {

    override suspend fun getMoviesAsync(): MoviesResultDto =
        moviesApi.getPopularMovies()

    override suspend fun getMovieByIdAsync(movieId: Int): DetailsMovieDto =
        moviesApi.getMovieById(movieId)

    override suspend fun getGenresAsync(): GenresResultDto =
        moviesApi.getGenres()

    override suspend fun getCreditsByMovieId(movieId: Int): CreditsResultDto =
        moviesApi.getCreditsByMovieId(movieId)

}