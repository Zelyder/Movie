package com.zelyder.movie.presentation.moviedetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zelyder.movie.domain.models.Movie
import com.zelyder.movie.domain.MoviesDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MoviesDetailsViewModel(private val dataSource: MoviesDataSource) : ViewModel(){

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "CoroutineExceptionHandler:$throwable")
    }

    private val _movie = MutableLiveData<Movie>()

    val movie : LiveData<Movie> get() = _movie

    fun loadMovie(movieId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _movie.value = dataSource.getMovieByIdAsync(movieId)
        }
    }
}
