package com.zelyder.movie.data.network

import com.zelyder.movie.data.network.apis.MovieApi

interface MoviesNetworkClient {
    fun moviesApi(): MovieApi
}