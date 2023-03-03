package com.dogdduddy.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class movieViewModel @Inject constructor(
    val repository : movieRepository
) : ViewModel() {

    private val myCustomPosts2 : MutableLiveData<String> = MutableLiveData()
    // 라이브 데이터 변경 시 다른 라이브 데이터 발행
    val result = myCustomPosts2.switchMap { queryString ->
        repository.getPost(queryString)
    }
    // 라이브 데이터 변경
    fun searchPost(userId: String) {
        myCustomPosts2.value = userId
    }
}