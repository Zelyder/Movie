package com.zelyder.movie.presentation.core

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.provider.Settings
import android.widget.DatePicker
import android.widget.TimePicker
import com.zelyder.movie.R
import java.util.*

class Dialogs: DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

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

    private lateinit var title: String

    fun showCalendarPermissionExplanationDialog(context: Context, block: () -> Unit) {
        AlertDialog.Builder(context)
            .setMessage(R.string.permission_dialog_explanation_text)
            .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                block()
                dialog.dismiss()
            }
            .setNeutralButton(R.string.dialog_negative_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showCalendarPermissionDeniedDialog(context: Context) {
        AlertDialog.Builder(context)
            .setMessage(R.string.permission_dialog_denied_text)
            .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                context.startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:${context.packageName}")
                    )
                )
                dialog.dismiss()
            }
            .setNeutralButton(R.string.dialog_negative_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun openDateTimePicker(context: Context, movieTitle: String) {
        getDateTimeCalendar()
        title = movieTitle

        DatePickerDialog(context, this, year, month, day).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(view?.context, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        view?.context?.let {
            writeToCalendar(
                it,
                title,
                savedYear,
                savedMonth,
                savedDay,
                savedHour,
                savedMinute
            )
        }
    }

    private fun getDateTimeCalendar() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    private fun writeToCalendar(
        context: Context,
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
            .putExtra(CalendarContract.Events.TITLE, context.resources.getString(R.string.reminder_title))
            .putExtra(CalendarContract.Events.DESCRIPTION, context.resources.getString(R.string.reminder_description, movieTitle))
        context.startActivity(intent)
    }
}