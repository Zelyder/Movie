package com.zelyder.movie.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zelyder.movie.domain.datasources.MoviesRemoteDataSource
import com.zelyder.movie.domain.repositories.MovieDetailsRepository
import com.zelyder.movie.domain.repositories.MoviesListRepository
import com.zelyder.movie.presentation.moviedetails.MoviesDetailsViewModel
import com.zelyder.movie.presentation.movieslist.MoviesListViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val moviesListRepository: MoviesListRepository,
    private val moviesDetailsRepository: MovieDetailsRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(moviesListRepository)
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(moviesDetailsRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}