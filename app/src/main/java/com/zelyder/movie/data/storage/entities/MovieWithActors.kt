package com.zelyder.movie.data.storage.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.zelyder.movie.data.storage.DbContract

data class MovieWithActors (
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = DbContract.Movies.COLUMN_NAME_ID,
        entityColumn = DbContract.Actors.COLUMN_NAME_MOVIE_ID
    )
    val actors: List<ActorEntity> = emptyList()
)