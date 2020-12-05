package com.zelyder.movie

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView


class MoviesDetailsFragment : Fragment() {

    var navigationClickListener: NavigationClickListener? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getInt(KEY_MOVIE_ID)?.let { MoviesDataSource().getMovieById(it) }
        val ivBigCoverImg: ImageView = view.findViewById(R.id.imageDetailsPoster)
        val tvStoryline : TextView = view.findViewById(R.id.tvDetailsStorylineContent)
        val tvGenres : TextView = view.findViewById(R.id.tvDetailsGenres)
        val tvAgeRating : TextView = view.findViewById(R.id.tvDetailsAgeRating)
        val tvReviewsCount : TextView = view.findViewById(R.id.tvDetailsReviewsCount)
        val tvTitle : TextView = view.findViewById(R.id.tvDetailsTitle)
        val ratingBar : RatingBar = view.findViewById(R.id.detailsRatingBar)

        if (movie?.bigCoverImg != -1) {
            movie?.bigCoverImg?.let { ivBigCoverImg.setImageResource(it) }
        }
        movie?.rating?.let { ratingBar.rating = it }
        tvStoryline.text = movie?.Storyline
        tvGenres.text = movie?.genres
        tvAgeRating.text = movie?.ageRating
        tvTitle.text = movie?.title
        tvReviewsCount.text = view.context
            .getString(R.string.reviews_count_template, movie?.reviewsCount)

        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            navigationClickListener?.onClickBack()
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationClickListener = null
    }

    companion object {
        fun newInstance(id: Int) : MoviesDetailsFragment{
            val args = Bundle()
            args.putInt(KEY_MOVIE_ID, id)
            val fragment = MoviesDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}

const val KEY_MOVIE_ID = "movie_id"