package com.zelyder.movie

interface NavigationClickListener {
    fun onClickBack()
    fun navigateToDetails(id: Int)
}