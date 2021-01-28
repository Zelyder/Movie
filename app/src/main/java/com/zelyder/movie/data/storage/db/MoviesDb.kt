package com.zelyder.movie.data.storage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zelyder.movie.MyApp
import com.zelyder.movie.data.storage.DbContract
import com.zelyder.movie.data.storage.dao.ActorsDao
import com.zelyder.movie.data.storage.dao.GenresDao
import com.zelyder.movie.data.storage.dao.MoviesDao
import com.zelyder.movie.data.storage.entities.ActorEntity
import com.zelyder.movie.data.storage.entities.GenreEntity
import com.zelyder.movie.data.storage.entities.MovieEntity

@Database(entities = [GenreEntity::class, ActorEntity::class, MovieEntity::class], version = 1)
abstract class MoviesDb: RoomDatabase() {

    abstract fun genresDao(): GenresDao
    abstract fun actorsDao(): ActorsDao
    abstract fun moviesDao(): MoviesDao

    companion object {

        fun create(applicationContext: Context): MoviesDb = Room.databaseBuilder(
            applicationContext,
            MoviesDb::class.java,
            DbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}