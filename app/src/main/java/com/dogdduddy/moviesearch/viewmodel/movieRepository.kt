package com.dogdduddy.moviesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dogdduddy.moviesearch.model.remote.Post

interface movieRepository {
    fun getPost(queryString: String): LiveData<PagingData<Post>>
}