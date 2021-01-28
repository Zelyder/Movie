package com.zelyder.movie.data.storage.dao

import androidx.room.*
import com.zelyder.movie.data.storage.DbContract
import com.zelyder.movie.data.storage.entities.MovieEntity
import com.zelyder.movie.data.storage.entities.MovieWithActors

@Dao
interface MoviesDao {

    @Query("SELECT * FROM ${DbContract.Movies.TABLE_NAME}")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM ${DbContract.Movies.TABLE_NAME} WHERE ${DbContract.Movies.COLUMN_NAME_ID} == :id")
    suspend fun getMovieWithActorsById(id: Int): MovieWithActors

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieEntity)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Query("UPDATE ${DbContract.Movies.TABLE_NAME} SET ${DbContract.Movies.COLUMN_NAME_FAVORITE} = :isFavorite WHERE ${DbContract.Movies.COLUMN_NAME_ID} == :id")
    suspend fun updateIsFavoriteById(id: Int, isFavorite: Boolean)

    @Query("DELETE FROM ${DbContract.Movies.TABLE_NAME} WHERE ${DbContract.Movies.COLUMN_NAME_ID} == :id")
    suspend fun deleteMovieById(id: Int)

}