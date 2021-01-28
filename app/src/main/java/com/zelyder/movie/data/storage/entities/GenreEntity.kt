package com.zelyder.movie.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zelyder.movie.data.storage.DbContract

@Entity(tableName = DbContract.Genres.TABLE_NAME)
data class GenreEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbContract.Genres.COLUMN_NAME_ID)
    val id: Int,
    @ColumnInfo(name = DbContract.Genres.COLUMN_NAME_NAME)
    val name: String
)