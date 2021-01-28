package com.zelyder.movie.data.storage

import android.provider.BaseColumns

object DbContract {
    const val DATABASE_NAME = "Movies.db"

    object Genres {
        const val TABLE_NAME = "genres"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME =  "name"
    }

    object Actors {
        const val TABLE_NAME = "actors"

        const val COLUMN_NAME_CAST_ID = "castId"
        const val COLUMN_NAME_MOVIE_ID = "movieId"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_ORDER = "orderInCredits"
        const val COLUMN_NAME_CHARACTER = "character"
        const val COLUMN_NAME_PICTURE_URL = "pictureUrl"
    }

    object Movies {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER_URL = "posterUrl"
        const val COLUMN_NAME_BACKDROP = "backdrop"
        const val COLUMN_NAME_RATINGS = "ratings"
        const val COLUMN_NAME_NUMBER_OF_RATINGS = "numberOfRatings"
        const val COLUMN_NAME_MINIMUM_AGE = "minimumAge"
        const val COLUMN_NAME_DURATION = "duration"
        const val COLUMN_NAME_RELEASE_DATE = "releaseDate"
        const val COLUMN_NAME_GENRES = "genres"
        const val COLUMN_NAME_FAVORITE = "isFavorite"
    }
}