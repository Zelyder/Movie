package com.zelyder.movie.domain.repositories

import com.zelyder.movie.data.mappers.toDetailsMovie
import com.zelyder.movie.data.network.dto.CastDto
import com.zelyder.movie.data.network.dto.DetailsMovieDto
import com.zelyder.movie.domain.datasources.MoviesRemoteDataSource
import com.zelyder.movie.domain.models.DetailsMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val imagesBaseUrl: String
) : MovieDetailsRepository {
    override suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean): DetailsMovie =
        withContext(Dispatchers.IO) {
            val movieDto = remoteDataSource.getMovieByIdAsync(movieId)

            val actorsDto = remoteDataSource.getCreditsByMovieId(movieId).cast

            movieDto.toDetailsMovie(actorsDto, imagesBaseUrl)
        }
}