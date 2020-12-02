package com.zelyder.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(isFirstOpen){
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MoviesListFragment())
                .addToBackStack("MoviesList")
                .commit()
        }

        isFirstOpen = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_FIRST_OPEN, isFirstOpen)


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isFirstOpen = savedInstanceState.getBoolean(KEY_FIRST_OPEN)

    }
    companion object {
        var isFirstOpen = true
    }

}

private const val KEY_FIRST_OPEN = "isFirstOpen"