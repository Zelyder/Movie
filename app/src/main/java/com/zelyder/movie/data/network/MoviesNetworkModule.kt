package com.zelyder.movie.data.network

import com.zelyder.movie.BuildConfig
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zelyder.movie.data.network.apis.MovieApi
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

class MoviesNetworkModule(): MoviesNetworkClient {

    private val jsonFormat = Json {
        ignoreUnknownKeys = true
    }

        @ExperimentalSerializationApi
        private val moviesRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(jsonFormat.asConverterFactory("application/json".toMediaType()))
            .build()

    @ExperimentalSerializationApi
    override fun moviesApi(): MovieApi = moviesRetrofit.create()

}