package com.zelyder.movie.presentation.movieslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zelyder.movie.domain.models.DetailsMovie
import com.zelyder.movie.domain.datasources.MoviesRemoteDataSource
import com.zelyder.movie.domain.models.ListMovie
import com.zelyder.movie.domain.repositories.MoviesListRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MoviesListViewModel(private val moviesListRepository: MoviesListRepository) : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "CoroutineExceptionHandler:$throwable")
    }

    private val _moviesList = MutableLiveData<List<ListMovie>>()

    val moviesList: LiveData<List<ListMovie>> get() = _moviesList

    fun updateList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _moviesList.value = moviesListRepository.getMoviesAsync()
        }
    }

    fun updateMovie(movie: ListMovie){
        viewModelScope.launch(coroutineExceptionHandler)  {
            moviesListRepository.updateMovieAsync(movie)
        }
    }

}