package com.zelyder.movie.moviedetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var ivBigCoverImg: ImageView
    private lateinit var tvStoryline: TextView
    private lateinit var tvGenres: TextView
    private lateinit var tvAgeRating: TextView
    private lateinit var tvReviewsCount: TextView
    private lateinit var tvTitle: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var rvActors: RecyclerView
    private lateinit var btnBack: View

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

        ivBigCoverImg = view.findViewById(R.id.imageDetailsPoster)
        tvStoryline = view.findViewById(R.id.tvDetailsStorylineContent)
        tvGenres = view.findViewById(R.id.tvDetailsGenres)
        tvAgeRating = view.findViewById(R.id.tvDetailsAgeRating)
        tvReviewsCount = view.findViewById(R.id.tvDetailsReviewsCount)
        tvTitle = view.findViewById(R.id.tvDetailsTitle)
        ratingBar = view.findViewById(R.id.detailsRatingBar)
        rvActors = view.findViewById(R.id.rvDetailsActors)
        btnBack = view.findViewById(R.id.btnBack)

        rvActors.adapter = ActorsListAdapter()

        coroutineScope.launch {
            showMovie(getMovie())
        }

    }

    override fun onDetach() {
        super.onDetach()
        navigationClickListener = null
    }

    private suspend fun getMovie(): Movie? = withContext(Dispatchers.IO) {
        arguments?.getInt(KEY_MOVIE_ID)?.let { dataProvider?.dataSource()?.getMovieByIdAsync(it) }
    }

    private suspend fun showMovie(movie: Movie?) = withContext(Dispatchers.Main) {
        if (!movie?.backdrop.isNullOrEmpty()) {
            Picasso.get().load(movie?.backdrop)
                .into(ivBigCoverImg)
        }
        movie?.ratings?.let { ratingBar.rating = it }
        tvStoryline.text = movie?.overview
        tvGenres.text = movie?.genres?.joinToString(",") { it.name }
        tvAgeRating.text = requireContext()
            .getString(R.string.minimumAge_template, movie?.minimumAge)
        tvTitle.text = movie?.title
        tvReviewsCount.text = requireContext()
            .getString(R.string.reviews_count_template, movie?.numberOfRatings)
        btnBack.setOnClickListener {
            navigationClickListener?.onClickBack()
        }
        rvActors.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvActors.adapter = ActorsListAdapter().also {
            movie?.actors?.let { it1 ->
                it.bindActors(
                    it1
                )
            }
        }
    }


    companion object {
        fun newInstance(id: Int): MoviesDetailsFragment {
            val args = Bundle()
            args.putInt(KEY_MOVIE_ID, id)
            val fragment = MoviesDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}

const val KEY_MOVIE_ID = "movie_id"