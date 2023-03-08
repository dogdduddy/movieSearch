package com.dogdduddy.moviesearch

import android.app.Application
import com.dogdduddy.moviesearch.model.local.AppDatabase
import com.dogdduddy.moviesearch.viewmodel.MovieRepository
import com.dogdduddy.moviesearch.viewmodel.SearchLogRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class App:Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getInstance(this) }

    val movieRepository by lazy { MovieRepository() }
    val searchLogRepository by lazy { SearchLogRepository(database.SearchLogDao()) }


}