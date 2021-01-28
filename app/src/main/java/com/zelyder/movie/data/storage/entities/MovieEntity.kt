package com.zelyder.movie.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.zelyder.movie.data.storage.DbContract

@Entity(tableName = DbContract.Movies.TABLE_NAME)
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_ID)
    val id: Int = 0,
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_TITLE)
    val title: String = "",
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_OVERVIEW)
    val overview: String = "",
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_POSTER_URL)
    val poster: String = "",
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_BACKDROP)
    val backdrop: String = "",
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_RATINGS)
    val ratings: Float = 0.0f,
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_NUMBER_OF_RATINGS)
    val numberOfRatings: Int = 0,
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_MINIMUM_AGE)
    val minimumAge: String = "",
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_DURATION)
    val duration: Int = 0,
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_RELEASE_DATE)
    val releaseDate: String = "",
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_GENRES)
    val genres: String = "",
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_FAVORITE)
    var isFavorite: Boolean = false
)