package com.dogdduddy.moviesearch.model

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dogdduddy.moviesearch.network.Retrofit
import com.dogdduddy.moviesearch.viewmodel.movieRepository
import javax.inject.Inject

class movieRepositoryImpl @Inject constructor(): movieRepository {
    override fun getPost(queryString: String): LiveData<PagingData<Post>> {
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