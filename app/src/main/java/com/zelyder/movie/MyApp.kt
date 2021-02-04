package com.zelyder.movie

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.zelyder.movie.data.network.MoviesNetworkModule
import com.zelyder.movie.data.storage.db.MoviesDb
import com.zelyder.movie.domain.datasources.MoviesLocalDataSourceImpl
import com.zelyder.movie.domain.datasources.MoviesRemoteDataSourceImpl
import com.zelyder.movie.domain.repositories.MovieDetailsRepository
import com.zelyder.movie.domain.repositories.MovieDetailsRepositoryImpl
import com.zelyder.movie.domain.repositories.MoviesListRepository
import com.zelyder.movie.domain.repositories.MoviesListRepositoryImpl
import com.zelyder.movie.presentation.background.MyWorker
import com.zelyder.movie.presentation.core.ViewModelFactory
import com.zelyder.movie.presentation.core.ViewModelFactoryProvider
import kotlinx.serialization.ExperimentalSerializationApi

class MyApp: Application(), ViewModelFactoryProvider {

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var moviesListRepository: MoviesListRepository
    private lateinit var moviesDetailsRepository: MovieDetailsRepository

    @ExperimentalSerializationApi
    override fun onCreate() {
        super.onCreate()

        initRepositories()

        MyWorker.startWork(applicationContext, moviesListRepository)

        viewModelFactory = ViewModelFactory(moviesListRepository, moviesDetailsRepository)
    }

    override fun viewModelFactory(): ViewModelFactory = viewModelFactory

    @ExperimentalSerializationApi
    private fun initRepositories() {

        val localDataSource = MoviesLocalDataSourceImpl(MoviesDb.create(applicationContext))
        val remoteDateSource = MoviesRemoteDataSourceImpl(MoviesNetworkModule().moviesApi())

        moviesListRepository = MoviesListRepositoryImpl(
            localDataSource,
            remoteDateSource,
            BuildConfig.BASE_IMAGE_URL
        )

        moviesDetailsRepository = MovieDetailsRepositoryImpl(
            localDataSource,
            remoteDateSource,
            BuildConfig.BASE_IMAGE_URL
        )
    }


}

fun Context.viewModelFactoryProvider() = (applicationContext as MyApp)

fun Fragment.viewModelFactoryProvider() = requireContext().viewModelFactoryProvider()