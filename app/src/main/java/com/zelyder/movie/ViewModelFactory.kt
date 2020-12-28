package com.zelyder.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zelyder.movie.domain.MoviesDataSource
import com.zelyder.movie.moviedetails.MoviesDetailsViewModel
import com.zelyder.movie.movieslist.MoviesListViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val dataSource: MoviesDataSource) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(dataSource)
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(dataSource)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}