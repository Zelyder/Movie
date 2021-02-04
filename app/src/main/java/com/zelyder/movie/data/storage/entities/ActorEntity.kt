package com.zelyder.movie.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.zelyder.movie.data.storage.DbContract

@Entity(tableName = DbContract.Actors.TABLE_NAME,
    foreignKeys = [ForeignKey(entity = MovieEntity::class,
    parentColumns = arrayOf(DbContract.Movies.COLUMN_NAME_ID),
    childColumns = arrayOf(DbContract.Actors.COLUMN_NAME_MOVIE_ID),
    onDelete = CASCADE)]
    )
data class ActorEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_CAST_ID)
    val castId: Int = 0,
    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_MOVIE_ID)
    val movieId: Int = 0,
    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_NAME)
    val name: String = "",
    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_ORDER)
    val orderInCredits: Int = 0,
    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_CHARACTER)
    val character: String,
    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_PICTURE_URL)
    val picture: String = ""
)