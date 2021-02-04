package com.zelyder.movie.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zelyder.movie.data.storage.DbContract
import com.zelyder.movie.data.storage.entities.GenreEntity

@Dao
interface GenresDao {

    @Query("SELECT * FROM ${DbContract.Genres.TABLE_NAME}")
    suspend fun getAllGenres(): List<GenreEntity>

    @Query("SELECT * FROM ${DbContract.Genres.TABLE_NAME} WHERE ${DbContract.Genres.COLUMN_NAME_ID} == :id")
    suspend fun getGenreById(id: Int): GenreEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllGenres(genres: List<GenreEntity>)
}