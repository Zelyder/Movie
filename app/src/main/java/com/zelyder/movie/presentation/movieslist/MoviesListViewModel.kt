package com.zelyder.movie.presentation.movieslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zelyder.movie.domain.models.Movie
import com.zelyder.movie.domain.MoviesDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MoviesListViewModel(private val dataSource: MoviesDataSource) : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "CoroutineExceptionHandler:$throwable")
    }

    private val _moviesList = MutableLiveData<List<Movie>>()

    val moviesList: LiveData<List<Movie>> get() = _moviesList

    fun updateList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _moviesList.value = dataSource.getMoviesAsync()
        }
    }

}