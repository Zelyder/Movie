package com.zelyder.movie

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.zelyder.movie.domain.MoviesDataSourceImpl

class MyApp: Application(), ViewModelFactoryProvider {

    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()
        viewModelFactory = ViewModelFactory(MoviesDataSourceImpl(applicationContext))
    }

    override fun viewModelFactory(): ViewModelFactory = viewModelFactory
}

fun Context.viewModelFactoryProvider() = (applicationContext as MyApp)

fun Fragment.viewModelFactoryProvider() = requireContext().viewModelFactoryProvider()