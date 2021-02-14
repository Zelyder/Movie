package com.zelyder.movie.presentation.background

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import androidx.core.app.*
import androidx.core.net.toUri
import com.zelyder.movie.domain.models.ListMovie
import com.zelyder.movie.domain.repositories.MoviesListRepository
import java.util.concurrent.TimeUnit
import com.zelyder.movie.R
import com.zelyder.movie.presentation.MainActivity

class MyWorker(val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            updateData()
            sendNotification(context)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "MyWorker"
        private const val RECOMMENDATION_CHANNEL = "recommendationChannel"
        const val RECOMMENDATION_TAG = "RecommendationTag"
        private const val REQUEST_CODE = 1
        private const val TIME_PERIOD_IN_HOURS = 8L
        private lateinit var moviesListRepository: MoviesListRepository
        private var mostRatedMovie: ListMovie? = null

        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        private val request =
            PeriodicWorkRequest.Builder(
                MyWorker::class.java,
                TIME_PERIOD_IN_HOURS,
                TimeUnit.HOURS
            )
                .addTag(TAG)
                .setConstraints(constraints)
                .build()

        fun startWork(context: Context, _moviesListRepository: MoviesListRepository) {
            moviesListRepository = _moviesListRepository
            WorkManager.getInstance(context).enqueue(request)
        }

        private suspend fun updateData() {
            mostRatedMovie = moviesListRepository.updateAndGetHighestRatedNewMovieAsync()
        }

        private fun sendNotification(context: Context) {
            Log.d(TAG, "Movie = $mostRatedMovie")
            if (mostRatedMovie != null) {
                val contentUri = "com.zelyder.movie://movie/${mostRatedMovie?.id}".toUri()

                val notificationManager = NotificationManagerCompat.from(context)

                val notificationChannel = NotificationChannelCompat.Builder(
                    RECOMMENDATION_CHANNEL,
                    NotificationManagerCompat.IMPORTANCE_HIGH
                )
                    .setName(context.resources.getString(R.string.recommendation_channel_name))
                    .setDescription(context.resources.getString(R.string.recommendation_channel_description))
                    .build()

                notificationManager.createNotificationChannel(notificationChannel)

                val notification = NotificationCompat.Builder(context, RECOMMENDATION_CHANNEL)
                    .setContentTitle(mostRatedMovie?.title)
                    .setSmallIcon(R.drawable.ic_movie)
                    .setContentText(context.resources.getString(R.string.notification_text))
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
                    mostRatedMovie?.id ?: 0,
                    notification
                )
            }
        }
    }
}

