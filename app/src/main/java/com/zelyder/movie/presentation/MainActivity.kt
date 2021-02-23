package com.zelyder.movie.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.transition.MaterialElevationScale
import com.zelyder.movie.R
import com.zelyder.movie.presentation.core.AndroidNotifications
import com.zelyder.movie.presentation.core.NavigationClickListener
import com.zelyder.movie.presentation.core.Notifications
import com.zelyder.movie.presentation.moviedetails.MoviesDetailsFragment
import com.zelyder.movie.presentation.moviedetails.NAV_LIST_TO_DETAILS_DURATION
import com.zelyder.movie.presentation.movieslist.MoviesListFragment


class MainActivity : AppCompatActivity(), NavigationClickListener {

    private val notifications: Notifications by lazy { AndroidNotifications() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notifications.initialize(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MoviesListFragment())
                .commit()

            intent?.let(::handleIntent)
            notifications.dismissAll()
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

    override fun navigateToDetails(id: Int, itemView: View?) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MoviesDetailsFragment.newInstance(id))
            .addToBackStack("MoviesDetails")
            .also {
                if (itemView != null) {
                    val cardView: CardView = itemView.findViewById(R.id.cvMovieItem)
                    it.addSharedElement(cardView, resources.getString(R.string.movie_detail_transition_name))
                }
            }
            .commit()
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toIntOrNull()
                if (id != null) {
                    navigateToDetails(id)
                    notifications.dismiss(id)
                }
            }
        }
    }
}