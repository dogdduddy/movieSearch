package com.dogdduddy.moviesearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

// 뷰모델에 인자를 넘겨주기 위한 팩토리 메서드
class viewModelFactory(
    private val repository : moviewRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return movieViewModel(repository) as T
    }
}