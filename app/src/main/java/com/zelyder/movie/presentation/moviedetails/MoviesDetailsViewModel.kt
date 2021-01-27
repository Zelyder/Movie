package com.zelyder.movie.presentation.moviedetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zelyder.movie.domain.models.DetailsMovie
import com.zelyder.movie.domain.datasources.MoviesRemoteDataSource
import com.zelyder.movie.domain.repositories.MovieDetailsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MoviesDetailsViewModel(private val moviesDetailsRepository: MovieDetailsRepository) : ViewModel(){

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "CoroutineExceptionHandler:$throwable")
    }

    private val _movie = MutableLiveData<DetailsMovie>()

    val movie : LiveData<DetailsMovie> get() = _movie

    fun loadMovie(movieId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _movie.value = moviesDetailsRepository.getMovieByIdAsync(movieId)
        }
    }
}
