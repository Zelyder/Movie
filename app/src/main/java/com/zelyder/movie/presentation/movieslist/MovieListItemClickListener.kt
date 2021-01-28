package com.zelyder.movie.presentation.movieslist

import com.zelyder.movie.domain.models.ListMovie

interface MovieListItemClickListener {
    fun onClickLike(movieId: Int, isFavorite: Boolean)
}