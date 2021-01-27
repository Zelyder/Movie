package com.zelyder.movie.domain.repositories

import com.zelyder.movie.data.mappers.toListMovie
import com.zelyder.movie.data.network.dto.GenreDto
import com.zelyder.movie.data.network.dto.ListMovieDto
import com.zelyder.movie.domain.datasources.MoviesRemoteDataSource
import com.zelyder.movie.domain.models.ListMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesListRepositoryImpl(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val imagesBaseUrl: String
) : MoviesListRepository {

    // TODO: Remove after cache implementation.
    private var cachedGenres = mutableListOf<GenreDto>()

    override suspend fun getMoviesAsync(forceRefresh: Boolean): List<ListMovie> =
        withContext(Dispatchers.IO) {
            val moviesDto = remoteDataSource.getMoviesAsync().popularMovies


            if(cachedGenres.isEmpty()){
                cachedGenres = remoteDataSource.getGenresAsync().genres.toMutableList()
            }
            moviesDto.map { it.toListMovie(cachedGenres, imagesBaseUrl) }
        }

}