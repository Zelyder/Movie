package com.zelyder.movie

import android.app.Application
import com.zelyder.movie.domain.DataProvider
import com.zelyder.movie.domain.MoviesDataSource
import com.zelyder.movie.domain.MoviesDataSourceImpl

class MyApp: Application(), DataProvider {

    private lateinit var moviesDataSource: MoviesDataSource

    override fun onCreate() {
        super.onCreate()
        moviesDataSource = MoviesDataSourceImpl(applicationContext)
    }

    override fun dataSource(): MoviesDataSource = moviesDataSource
}