package com.zelyder.movie.presentation.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.zelyder.movie.R
import com.zelyder.movie.domain.models.ListMovie
import com.zelyder.movie.presentation.MainActivity


interface Notifications {
    fun initialize()
    fun showNotification(movie: ListMovie)
    fun dismissNotification(movieId: Int)
    fun dismissAllNotifications()
}

class AndroidNotifications(val context: Context) : Notifications {

    companion object {
        private const val CHANNEL_RECOMMENDATION = "recommendationChannel"
        const val RECOMMENDATION_TAG = "RecommendationTag"
        private const val REQUEST_CODE = 1
    }

    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    override fun initialize() {
        if(notificationManager.getNotificationChannel(CHANNEL_RECOMMENDATION) == null) {
            val notificationChannel = NotificationChannelCompat.Builder(
                CHANNEL_RECOMMENDATION,
                NotificationManagerCompat.IMPORTANCE_HIGH
            )
                .setName(context.resources.getString(R.string.recommendation_channel_name))
                .setDescription(context.resources.getString(R.string.recommendation_channel_description))
                .build()

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun showNotification(movie: ListMovie) {
        val contentUri = "com.zelyder.movie://movie/${movie.id}".toUri()
        val poster = Picasso.get().load(movie.poster).resize(100,100).get()

        val notification = NotificationCompat.Builder(context, CHANNEL_RECOMMENDATION)
            .setContentTitle(movie.title)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentText(context.resources.getString(R.string.notification_text))
            .setLargeIcon(poster)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CODE,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .build()

        notificationManager.notify(
            RECOMMENDATION_TAG,
            movie.id,
            notification
        )
    }

    override fun dismissNotification(movieId: Int) {
        NotificationManagerCompat.from(context).cancel(RECOMMENDATION_TAG, movieId)
    }

    override fun dismissAllNotifications() {
        NotificationManagerCompat.from(context).cancelAll()
    }
}