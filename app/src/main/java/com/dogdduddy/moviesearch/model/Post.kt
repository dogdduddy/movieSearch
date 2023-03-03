package com.dogdduddy.moviesearch.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("title")
    val title : String,
    @SerializedName("link")
    val link : String,
    @SerializedName("image")
    val image : String,
    @SerializedName("userRating")
    val userRating : String,
    @SerializedName("pubDate")
    val pubdate : String,
)

