package com.zelyder.movie.domain


interface DataProvider {
    fun dataSource(): MoviesDataSource
}