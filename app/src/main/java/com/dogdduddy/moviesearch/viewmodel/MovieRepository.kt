package com.dogdduddy.moviesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dogdduddy.moviesearch.model.local.SearchLog
import com.dogdduddy.moviesearch.model.local.SearchLogDao
import com.dogdduddy.moviesearch.model.remote.MoviePagingSource
import com.dogdduddy.moviesearch.model.remote.Post
import com.dogdduddy.moviesearch.network.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {
    fun getPost(queryString: String): LiveData<PagingData<Post>> = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ),
        // 사용할 메소드 선언
        pagingSourceFactory = { MoviePagingSource(queryString, Retrofit.instance) }
    ).liveData

}