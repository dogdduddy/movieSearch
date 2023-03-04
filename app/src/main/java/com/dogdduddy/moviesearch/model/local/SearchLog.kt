package com.dogdduddy.moviesearch.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class SearchLog(
    @PrimaryKey val keyword: String,
    @ColumnInfo(name = "date") val date: Date
)
