package com.zelyder.movie.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zelyder.movie.R
import com.zelyder.movie.presentation.core.AndroidNotifications
import com.zelyder.movie.presentation.core.NavigationClickListener
import com.zelyder.movie.presentation.core.Notifications
import com.zelyder.movie.presentation.moviedetails.MoviesDetailsFragment
import com.zelyder.movie.presentation.movieslist.MoviesListFragment


class MainActivity : AppCompatActivity(), NavigationClickListener {

    private val notifications: Notifications by lazy { AndroidNotifications(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MoviesListFragment())
                .commit()

            intent?.let(::handleIntent)
            notifications.dismissAllNotifications()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
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

    private fun handleIntent(intent: Intent) {
        when(intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toIntOrNull()
                if (id != null) {
                    navigateToDetails(id)
                    notifications.dismissNotification(id)
                }
            }
        }
    }
}