package com.zelyder.movie.presentation.moviedetails

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.Settings
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialContainerTransform
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
    private val dialogs by lazy { Dialogs() }
    private lateinit var prefs: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        isRationaleShown = prefs.getBoolean(PREF_KEY_RATIONAL, false)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.main_container
            duration = NAV_LIST_TO_DETAILS_DURATION
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(ContextCompat.getColor(requireContext(), R.color.dark_purple_blue))
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

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

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
                dialogs.showCalendarPermissionExplanationDialog(requireContext()) {
                    isRationaleShown = true
                    prefs.edit().apply{
                        putBoolean(PREF_KEY_RATIONAL, isRationaleShown)
                        apply()
                    }
                    requestCalendarPermission()
                }
            isRationaleShown -> dialogs.showCalendarPermissionDeniedDialog(requireContext())
            else -> requestCalendarPermission()
        }
    }

    private fun requestCalendarPermission() {
        requestPermissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
    }


    private fun onCalendarPermissionGranted() {
        dialogs.openDateTimePicker(requireContext(), tvTitle.text.toString())
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
const val SHARED_PREF_NAME = "MOVIES_SHARED_PREF"
const val PREF_KEY_RATIONAL = "KEY_RATIONAL"
const val NAV_LIST_TO_DETAILS_DURATION = 1000L