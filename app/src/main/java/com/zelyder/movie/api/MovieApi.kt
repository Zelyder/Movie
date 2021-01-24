package com.zelyder.movie.api

import com.zelyder.movie.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getPopularMovies():PopularMoviesResponse

    @GET("movie/{movie_id}/credits?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getCredits(@Path("movie_id") id: Int): Credits

    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getGenres(): GenresResponse
}