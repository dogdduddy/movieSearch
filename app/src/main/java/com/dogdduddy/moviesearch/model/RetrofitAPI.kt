package com.dogdduddy.moviesearch.model

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitAPI {
    @Headers("Content-Type: application/json")
    @GET("search/{type}")
    suspend fun getSearchResult(
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") pw: String?,
        @Path("type") type: String?,
        @Query("query") query: String?,
        @Query("display") display: Int?
    ): Response<PostResponse>
}