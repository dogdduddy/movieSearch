package com.dogdduddy.moviesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.dogdduddy.moviesearch.model.local.SearchLog
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieViewModel(
    private val movieRepository : MovieRepository,
    private val searchLogRepository: SearchLogRepository
) : ViewModel() {

    private val _movieLiveData : MutableLiveData<String> = MutableLiveData()
    // 라이브 데이터 변경 시 다른 라이브 데이터 발행
    val movieLiveData = _movieLiveData.switchMap { queryString ->
        movieRepository.getPost(queryString)
    }
    // 라이브 데이터 변경
    fun searchPost(searchKeyword: String) {
        _movieLiveData.value = searchKeyword
    }

    val searchLogLiveData = searchLogRepository.getAll()

    suspend fun insertLog(searchLogKeyword: String)  = viewModelScope.launch {
        if(searchLogRepository.checkSearchLog(searchLogKeyword) == null) {
            if (searchLogLiveData.value?.size ?: 0 >= 10) {
                searchLogRepository.deleteLastLog()
            }
            withContext(Dispatchers.IO) {
                searchLogRepository.insertLog(searchLogKeyword)
            }
        } else {
            searchLogRepository.updateSearchLogDate(searchLogKeyword)
        }
    }
}
class MovieViewModelFactory(
    private val movieRepository: MovieRepository,
    private val searchLogRepository: SearchLogRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(movieRepository, searchLogRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}