package com.dogdduddy.moviesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dogdduddy.moviesearch.model.local.SearchLog
import com.dogdduddy.moviesearch.model.local.SearchLogDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class SearchLogRepository(private val searchLogDao: SearchLogDao) {

    // 검색 기록 가져오기
    fun getAll(): LiveData<List<SearchLog>> = searchLogDao.getAll()

    // 검색 키워드를 검색 기록에 추가
    suspend fun insertLog(searchLogKeyword: String) {
        withContext(Dispatchers.IO) {
            searchLogDao.insertLog(SearchLog(searchLogKeyword, Date()))
        }
    }
    // 최근 검색한 키워드가 10개가 넘는다면 가장 오래된 키워드 삭제
    suspend fun deleteLastLog() {
        withContext(Dispatchers.IO) {
            searchLogDao.deleteLastLog()
        }
    }
    // 검색 키워드가 검색 기록에 존재하는지 확인
    suspend fun checkSearchLog(searchKeyword: String): SearchLog? {
        return withContext(Dispatchers.IO) {
            searchLogDao.checkSearchLog(searchKeyword)
        }
    }

    // 최근 검색한 키워드가 검색 기록에 존재한다면 상위로 이동
    suspend fun updateSearchLogDate(searchKeyword: String) {
        withContext(Dispatchers.IO) {
            searchLogDao.updateSearchLogDate(SearchLog(searchKeyword, Date()))
        }
    }
}