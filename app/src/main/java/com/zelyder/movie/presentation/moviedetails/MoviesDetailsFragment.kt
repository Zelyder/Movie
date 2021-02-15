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
import com.zelyder.movie.presentation.core.NavigationClickListener
import com.zelyder.movie.viewModelFactoryProvider
import java.util.*


class MoviesDetailsFragment : BaseFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    var navigationClickListener: NavigationClickListener? = null
    var movieDetailsClickListener: MovieDetailsClickListener? = null
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
                showCalendarPermissionExplanationDialog()
            isRationaleShown -> showCalendarPermissionDeniedDialog()
            else -> requestCalendarPermission()
        }
    }


    private fun showCalendarPermissionExplanationDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.permission_dialog_explanation_text)
            .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                isRationaleShown = true
                requestCalendarPermission()
                dialog.dismiss()
            }
            .setNeutralButton(R.string.dialog_negative_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showCalendarPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.permission_dialog_denied_text)
            .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:${requireContext().packageName}")
                    )
                )
                dialog.dismiss()
            }
            .setNeutralButton(R.string.dialog_negative_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun requestCalendarPermission() {
        requestPermissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
    }


    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    private fun onCalendarPermissionGranted() {
        openDateTimePicker()
    }

    private fun getDateTimeCalendar() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        writeToCalendar(
            tvTitle.text.toString(),
            savedYear,
            savedMonth,
            savedDay,
            savedHour,
            savedMinute
        )
    }

    private fun openDateTimePicker() {
        getDateTimeCalendar()

        DatePickerDialog(requireContext(), this, year, month, day).show()
    }

    private fun writeToCalendar(
        movieTitle: String,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hourOfDay: Int,
        minute: Int
    ) {
        val startMillis: Long = Calendar.getInstance().run {
            set(year, month, dayOfMonth, hourOfDay, minute)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(year, month, dayOfMonth, hourOfDay + 2, minute)
            timeInMillis
        }

        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
            .putExtra(CalendarContract.Events.TITLE, requireContext().resources.getString(R.string.reminder_title))
            .putExtra(CalendarContract.Events.DESCRIPTION, requireContext().resources.getString(R.string.reminder_description, movieTitle))
        startActivity(intent)
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