package com.zelyder.movie.data.storage.dao

import androidx.room.*
import com.zelyder.movie.data.storage.DbContract
import com.zelyder.movie.data.storage.entities.ActorEntity

@Dao
interface ActorsDao {

    @Query("SELECT * FROM ${DbContract.Actors.TABLE_NAME}")
    suspend fun getAllActors(): List<ActorEntity>

    @Query("SELECT * FROM ${DbContract.Actors.TABLE_NAME} WHERE ${DbContract.Actors.COLUMN_NAME_CAST_ID} == :castId")
    suspend fun getActorById(castId: Int): ActorEntity

    @Query("SELECT * FROM ${DbContract.Actors.TABLE_NAME} WHERE ${DbContract.Actors.COLUMN_NAME_MOVIE_ID} == :movieId")
    suspend fun getActorsByMovieId(movieId: Int): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllActors(actors: List<ActorEntity>)

    @Query("DELETE FROM ${DbContract.Actors.TABLE_NAME} WHERE ${DbContract.Actors.COLUMN_NAME_CAST_ID} == :castId")
    suspend fun deleteActorById(castId: Int)

    @Query("DELETE FROM ${DbContract.Actors.TABLE_NAME} WHERE ${DbContract.Actors.COLUMN_NAME_MOVIE_ID} == :movieId")
    suspend fun deleteActorByMovieId(movieId: Int)
}