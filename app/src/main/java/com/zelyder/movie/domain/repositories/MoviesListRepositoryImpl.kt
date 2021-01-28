package com.zelyder.movie.domain.repositories

import com.zelyder.movie.data.mappers.toGenreDto
import com.zelyder.movie.data.mappers.toGenreEntity
import com.zelyder.movie.data.mappers.toListMovie
import com.zelyder.movie.data.mappers.toMovieEntity
import com.zelyder.movie.domain.datasources.MoviesLocalDataSource
import com.zelyder.movie.domain.datasources.MoviesRemoteDataSource
import com.zelyder.movie.domain.models.ListMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesListRepositoryImpl(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val imagesBaseUrl: String
) : MoviesListRepository {

    override suspend fun getMoviesAsync(forceRefresh: Boolean): List<ListMovie> =
        withContext(Dispatchers.IO) {

            var cachedGenres = localDataSource.getGenresAsync()
            if (cachedGenres.isEmpty()) {
                cachedGenres = remoteDataSource.getGenresAsync().genres.map { it.toGenreEntity() }
                localDataSource.saveGenres(cachedGenres)
            }

            var movies = localDataSource.getMoviesAsync().map { it.toListMovie() }
            if(forceRefresh || movies.isEmpty()){
                movies = remoteDataSource.getMoviesAsync().popularMovies.map {
                    it.toListMovie(
                        cachedGenres.map { genre -> genre.toGenreDto() },
                        imagesBaseUrl
                    )
                }
                localDataSource.saveMovies(movies.map { it.toMovieEntity() })
            }

            movies
        }

    override suspend fun updateMovieAsync(movie: ListMovie) = withContext(Dispatchers.IO){
        localDataSource.updateMovie(movie.toMovieEntity())
    }
}