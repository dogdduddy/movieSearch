package com.dogdduddy.moviesearch.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dogdduddy.moviesearch.model.MoviePagingSource
import com.dogdduddy.moviesearch.model.Post
import com.dogdduddy.moviesearch.model.Retrofit

class moviewRepository {
    fun getPost(queryString: String): LiveData<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            // 사용할 메소드 선언
            pagingSourceFactory = { MoviePagingSource(queryString, Retrofit.instance) }
        ).liveData
    }
}