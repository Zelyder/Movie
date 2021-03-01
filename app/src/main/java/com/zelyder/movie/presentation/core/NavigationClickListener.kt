package com.zelyder.movie.presentation.core

import android.view.View

interface NavigationClickListener {
    fun onClickBack()
    fun navigateToDetails(id: Int, itemView: View? = null)
}