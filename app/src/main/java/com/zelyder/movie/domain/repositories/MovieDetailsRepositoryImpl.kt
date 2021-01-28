package com.zelyder.movie.domain.repositories

import com.zelyder.movie.data.mappers.*
import com.zelyder.movie.domain.datasources.MoviesLocalDataSource
import com.zelyder.movie.domain.datasources.MoviesRemoteDataSource
import com.zelyder.movie.domain.models.DetailsMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsRepositoryImpl(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val imagesBaseUrl: String
) : MovieDetailsRepository {
    override suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean): DetailsMovie =
        withContext(Dispatchers.IO) {

            var actors = localDataSource.getActorsByMovieId(movieId).map { it.toActor() }

            if (forceRefresh || actors.isEmpty()) {
                actors = toActorsList(
                    remoteDataSource.getCreditsByMovieId(movieId).cast,
                    movieId,
                    imagesBaseUrl
                )
                localDataSource.saveActors(actors.map { it.toActorEntity() })
            }

            var movies = localDataSource.getMovieWithActorsByIdAsync(movieId).toDetailsMovie()

            if (forceRefresh || movies.backdrop.isEmpty()) {
                movies = remoteDataSource.getMovieByIdAsync(movieId)
                    .toDetailsMovie(actors, imagesBaseUrl, movies.isFavorite)
                localDataSource.updateMovie(movies.toMovieEntity().movie)
            }

            movies
        }
}