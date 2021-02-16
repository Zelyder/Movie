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

    private var mostRatedMovie: ListMovie? = null

    override suspend fun getMoviesAsync(forceRefresh: Boolean): List<ListMovie> =
        withContext(Dispatchers.IO) {

            var cachedGenres = localDataSource.getGenresAsync()
            if (cachedGenres.isEmpty()) {
                cachedGenres = remoteDataSource.getGenresAsync().genres.map { it.toGenreEntity() }
                localDataSource.saveGenres(cachedGenres)
            }

            var movies = localDataSource.getMoviesAsync().map { it.toListMovie() }
            val localMovies = movies
            if (forceRefresh || movies.isEmpty()) {
                movies = remoteDataSource.getMoviesAsync().popularMovies.map {
                    it.toListMovie(
                        cachedGenres.map { genre -> genre.toGenreDto() },
                        imagesBaseUrl
                    )
                }

                val remoteMovies = movies
                val newMovies =
                    remoteMovies.filter { newMovie -> localMovies.none { it.id == newMovie.id } }
                mostRatedMovie = newMovies.maxByOrNull { it.ratings }

                localDataSource.saveMovies(movies.map { it.toMovieEntity() })
            }

            movies
        }

    override suspend fun updateMovieIsFavoriteAsync(movieId: Int, isFavorite: Boolean) =
        withContext(Dispatchers.IO) {
            localDataSource.updateMovieIsFavorite(movieId, isFavorite)
        }

    override suspend fun updateAndGetHighestRatedNewMovieAsync(): ListMovie? =
        withContext(Dispatchers.Default) {
            getMoviesAsync(true)
            mostRatedMovie
        }
}