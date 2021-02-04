package com.zelyder.movie.domain.datasources

import com.zelyder.movie.data.storage.db.MoviesDb
import com.zelyder.movie.data.storage.entities.ActorEntity
import com.zelyder.movie.data.storage.entities.GenreEntity
import com.zelyder.movie.data.storage.entities.MovieEntity
import com.zelyder.movie.data.storage.entities.MovieWithActors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesLocalDataSourceImpl(private val moviesDb: MoviesDb): MoviesLocalDataSource {
    override suspend fun getMoviesAsync(): List<MovieEntity>  = withContext(Dispatchers.IO){
        moviesDb.moviesDao().getAllMovies()
    }

    override suspend fun getMovieWithActorsByIdAsync(movieId: Int): MovieWithActors = withContext(Dispatchers.IO){
        moviesDb.moviesDao().getMovieWithActorsById(movieId)
    }

    override suspend fun getGenresAsync(): List<GenreEntity> = withContext(Dispatchers.IO){
        moviesDb.genresDao().getAllGenres()
    }

    override suspend fun getActorsByMovieId(movieId: Int): List<ActorEntity> = withContext(Dispatchers.IO){
        moviesDb.actorsDao().getActorsByMovieId(movieId)
    }

    override suspend fun saveMovies(movies: List<MovieEntity>) = withContext(Dispatchers.IO){
        moviesDb.moviesDao().addAllMovies(movies)
    }

    override suspend fun saveMovie(movie: MovieEntity) = withContext(Dispatchers.IO){
        moviesDb.moviesDao().addMovie(movie)
    }

    override suspend fun updateMovie(movie: MovieEntity) {
        moviesDb.moviesDao().updateMovie(movie)
    }

    override suspend fun updateMovieIsFavorite(id: Int, isFavorite: Boolean) {
        moviesDb.moviesDao().updateIsFavoriteById(id, isFavorite)
    }

    override suspend fun saveGenres(genres: List<GenreEntity>) = withContext(Dispatchers.IO){
        moviesDb.genresDao().addAllGenres(genres)
    }

    override suspend fun saveActors(actors: List<ActorEntity>) = withContext(Dispatchers.IO){
        moviesDb.actorsDao().addAllActors(actors)
    }
}