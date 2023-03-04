package com.dogdduddy.moviesearch.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SearchLogDao {
    @Query("SELECT * FROM SearchLog")
    fun getAll(): LiveData<List<SearchLog>>

    @Query("SELECT * FROM SearchLog WHERE keyword IN(:searchKeyword)")
    fun checkSearchLog(searchKeyword: String): SearchLog

    @Insert
    suspend fun insertLog(searchLog: SearchLog)

    @Query("DELETE FROM SearchLog WHERE date IN (SELECT date FROM SearchLog ORDER BY date ASC LIMIT 1)")
    suspend fun deleteLastLog()

    @Update
    suspend fun updateSearchLogDate(searchLog: SearchLog)
}