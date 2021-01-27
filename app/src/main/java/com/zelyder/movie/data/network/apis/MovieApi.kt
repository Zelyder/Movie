package com.zelyder.movie.data.network.apis

import com.zelyder.movie.BuildConfig
import com.zelyder.movie.data.network.dto.CreditsResultDto
import com.zelyder.movie.data.network.dto.DetailsMovieDto
import com.zelyder.movie.data.network.dto.GenresResultDto
import com.zelyder.movie.data.network.dto.MoviesResultDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getPopularMovies(): MoviesResultDto

    @GET("movie/{movie_id}/credits?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getCreditsByMovieId(@Path("movie_id") id: Int): CreditsResultDto

    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getGenres(): GenresResultDto

    @GET("movie/{movie_id}?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getMovieById(@Path("movie_id") id: Int): DetailsMovieDto
}