package com.dogdduddy.moviesearch.model

data class PostResponse(
    val lastBuildDate : String,
    val total : Int,
    val start : Int,
    val display : Int,
    val items : List<Post>
)