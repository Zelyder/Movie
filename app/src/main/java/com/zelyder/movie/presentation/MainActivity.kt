package com.zelyder.movie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zelyder.movie.R
import com.zelyder.movie.presentation.core.NavigationClickListener
import com.zelyder.movie.presentation.moviedetails.MoviesDetailsFragment
import com.zelyder.movie.presentation.movieslist.MoviesListFragment


class MainActivity : AppCompatActivity(), NavigationClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MoviesListFragment())
                .commit()
        }

    }

    override fun onClickBack() {
        onBackPressed()
    }

    override fun navigateToDetails(id: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MoviesDetailsFragment.newInstance(id))
            .addToBackStack("MoviesDetails")
            .commit()
    }
}