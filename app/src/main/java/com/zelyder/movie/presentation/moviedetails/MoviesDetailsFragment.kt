package com.zelyder.movie.presentation.moviedetails

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zelyder.movie.R
import com.zelyder.movie.domain.models.DetailsMovie
import com.zelyder.movie.presentation.core.BaseFragment
import com.zelyder.movie.presentation.core.Dialogs
import com.zelyder.movie.presentation.core.NavigationClickListener
import com.zelyder.movie.viewModelFactoryProvider
import java.util.*


class MoviesDetailsFragment : BaseFragment() {

    var navigationClickListener: NavigationClickListener? = null
    private val viewModel by lazy {
        viewModelFactoryProvider()
            .viewModelFactory().create(MoviesDetailsViewModel::class.java)
    }

    private lateinit var ivBigCoverImg: ImageView
    private lateinit var tvStoryline: TextView
    private lateinit var tvGenres: TextView
    private lateinit var tvAgeRating: TextView
    private lateinit var tvReviewsCount: TextView
    private lateinit var tvTitle: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var rvActors: RecyclerView
    private lateinit var btnBack: View
    private lateinit var btnRemind: ImageView

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var isRationaleShown = false
    private val dialogs by lazy { Dialogs(requireContext()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationClickListener) {
            navigationClickListener = context
        }
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isDetached: Boolean ->
            if (isDetached) {
                onCalendarPermissionGranted()
            }
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
        btnRemind = view.findViewById(R.id.btnRemind)

        rvActors.adapter = ActorsListAdapter()

        viewModel.movie.observe(this.viewLifecycleOwner, {
            showMovie(it)
        })

    }

    override fun onStart() {
        super.onStart()
        arguments?.getInt(KEY_MOVIE_ID)?.let { viewModel.loadMovie(it) }
    }


    override fun onDetach() {
        super.onDetach()
        navigationClickListener = null
        requestPermissionLauncher.unregister()
    }


    private fun showMovie(movie: DetailsMovie?) {

        movie?.let { _movie ->
            if (movie.backdrop.isNotEmpty()) {
                Picasso.get().load(movie.backdrop)
                    .into(ivBigCoverImg)
            }
            ratingBar.rating = _movie.ratings
            tvStoryline.text = _movie.overview
            tvGenres.text = _movie.genres
            tvAgeRating.text = movie.minimumAge
            tvTitle.text = _movie.title
            tvReviewsCount.text = requireContext()
                .getString(R.string.reviews_count_template, _movie.numberOfRatings)
            btnBack.setOnClickListener {
                navigationClickListener?.onClickBack()
            }
            btnRemind.setOnClickListener {
                onOpenDialogs()
            }
            rvActors.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvActors.adapter = ActorsListAdapter().also {
                it.bindActors(_movie.actors)
            }
        }
    }

    private fun onOpenDialogs() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED -> onCalendarPermissionGranted()
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR) ->
                dialogs.showCalendarPermissionExplanationDialog {
                    isRationaleShown = true
                    requestCalendarPermission()
                }
            isRationaleShown -> dialogs.showCalendarPermissionDeniedDialog()
            else -> requestCalendarPermission()
        }
    }

    private fun requestCalendarPermission() {
        requestPermissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
    }


    private fun onCalendarPermissionGranted() {
        dialogs.openDateTimePicker(tvTitle.text.toString())
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