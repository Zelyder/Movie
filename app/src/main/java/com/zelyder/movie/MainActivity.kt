package com.zelyder.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


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

    override fun navigateToDetails() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MoviesDetailsFragment())
            .addToBackStack("MoviesDetails")
            .commit()
    }
}