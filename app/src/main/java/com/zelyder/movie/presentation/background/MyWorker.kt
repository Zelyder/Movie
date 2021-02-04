package com.zelyder.movie.presentation.background

import android.content.Context
import androidx.work.*
import com.zelyder.movie.domain.repositories.MoviesListRepository
import java.util.concurrent.TimeUnit

class MyWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            updateData()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "MyWorker"
        private const val TIME_PERIOD_IN_HOURS = 8L
        private lateinit var moviesListRepository: MoviesListRepository

        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        private val request =
            PeriodicWorkRequest.Builder(MyWorker::class.java, TIME_PERIOD_IN_HOURS, TimeUnit.HOURS)
                .addTag(TAG)
                .setConstraints(constraints)
                .build()

        fun startWork(context: Context, _moviesListRepository: MoviesListRepository) {
            moviesListRepository = _moviesListRepository
            WorkManager.getInstance(context).enqueue(request)
        }

        private suspend fun updateData() {
            moviesListRepository.getMoviesAsync(true)
        }
    }
}

