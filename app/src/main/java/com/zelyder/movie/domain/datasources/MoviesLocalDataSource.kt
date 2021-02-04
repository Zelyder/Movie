package com.zelyder.movie.domain.datasources

import com.zelyder.movie.data.network.dto.CreditsResultDto
import com.zelyder.movie.data.network.dto.DetailsMovieDto
import com.zelyder.movie.data.network.dto.GenresResultDto
import com.zelyder.movie.data.network.dto.MoviesResultDto
import com.zelyder.movie.data.storage.entities.ActorEntity
import com.zelyder.movie.data.storage.entities.GenreEntity
import com.zelyder.movie.data.storage.entities.MovieEntity
import com.zelyder.movie.data.storage.entities.MovieWithActors

interface MoviesLocalDataSource {
    suspend fun getMoviesAsync(): List<MovieEntity>
    suspend fun getMovieWithActorsByIdAsync(movieId: Int): MovieWithActors
    suspend fun getGenresAsync(): List<GenreEntity>
    suspend fun getActorsByMovieId(movieId: Int): List<ActorEntity>
    suspend fun saveMovies(movies: List<MovieEntity>)
    suspend fun saveMovie(movie: MovieEntity)
    suspend fun updateMovie(movie: MovieEntity)
    suspend fun updateMovieIsFavorite(id: Int, isFavorite: Boolean)
    suspend fun saveGenres(genres: List<GenreEntity>)
    suspend fun saveActors(actors: List<ActorEntity>)
}