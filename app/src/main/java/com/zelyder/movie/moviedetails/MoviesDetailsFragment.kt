package com.zelyder.movie.moviedetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zelyder.movie.BaseFragment
import com.zelyder.movie.domain.MoviesDataSourceImpl
import com.zelyder.movie.NavigationClickListener
import com.zelyder.movie.R
import com.zelyder.movie.data.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoviesDetailsFragment : BaseFragment() {

    var navigationClickListener: NavigationClickListener? = null
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationClickListener) {
            navigationClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    private suspend fun getMovie(): Movie? = withContext(Dispatchers.IO) {
        arguments?.getInt(KEY_MOVIE_ID)?.let { dataProvider?.dataSource()?.getMovieByIdAsync(it)  }
    }

    private suspend fun showMovie(view: View) = withContext(Dispatchers.Main){
        val movie: Movie? = getMovie()
        val ivBigCoverImg: ImageView = view.findViewById(R.id.imageDetailsPoster)
        val tvStoryline : TextView = view.findViewById(R.id.tvDetailsStorylineContent)
        val tvGenres : TextView = view.findViewById(R.id.tvDetailsGenres)
        val tvAgeRating : TextView = view.findViewById(R.id.tvDetailsAgeRating)
        val tvReviewsCount : TextView = view.findViewById(R.id.tvDetailsReviewsCount)
        val tvTitle : TextView = view.findViewById(R.id.tvDetailsTitle)
        val ratingBar : RatingBar = view.findViewById(R.id.detailsRatingBar)
        val rvActors : RecyclerView = view.findViewById(R.id.rvDetailsActors)

        if (!movie?.backdrop.isNullOrEmpty()) {
            Picasso.get().load(movie?.backdrop)
                .into(ivBigCoverImg)
        }
        movie?.ratings?.let { ratingBar.rating = it }
        tvStoryline.text = movie?.overview
        tvGenres.text = movie?.genres?.joinToString(",") { it.name }
        tvAgeRating.text = view.context
            .getString(R.string.minimumAge_template, movie?.minimumAge)
        tvTitle.text = movie?.title
        tvReviewsCount.text = view.context
            .getString(R.string.reviews_count_template, movie?.numberOfRatings)
        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            navigationClickListener?.onClickBack()
        }
        rvActors.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        rvActors.adapter = ActorsListAdapter().also {
            movie?.actors?.let { it1 ->
                it.bindActors(
                    it1
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coroutineScope.launch {
            showMovie(view)
        }

    }

    override fun onDetach() {
        super.onDetach()
        navigationClickListener = null
    }

    companion object {
        fun newInstance(id: Int) : MoviesDetailsFragment {
            val args = Bundle()
            args.putInt(KEY_MOVIE_ID, id)
            val fragment = MoviesDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}

const val KEY_MOVIE_ID = "movie_id"