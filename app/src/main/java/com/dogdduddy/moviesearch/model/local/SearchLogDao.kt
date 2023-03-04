package com.dogdduddy.moviesearch.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchLogDao {
    @Query("SELECT * FROM SearchLog")
    fun getAll(): List<SearchLog>

    @Query("SELECT * FROM SearchLog WHERE keyword IN(:searchKeyword)")
    fun checkSearchLog(searchKeyword: String): SearchLog

    @Insert
    fun insertLog(searchLog: SearchLog)



}