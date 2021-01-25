package com.zelyder.movie.presentation.core

interface NavigationClickListener {
    fun onClickBack()
    fun navigateToDetails(id: Int)
}