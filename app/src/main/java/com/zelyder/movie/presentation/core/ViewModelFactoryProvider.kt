package com.zelyder.movie.presentation.core

import com.zelyder.movie.presentation.core.ViewModelFactory

interface ViewModelFactoryProvider {
    fun viewModelFactory(): ViewModelFactory
}