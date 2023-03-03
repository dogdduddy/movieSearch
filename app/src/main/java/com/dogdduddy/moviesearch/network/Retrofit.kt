package com.dogdduddy.moviesearch.network

import com.dogdduddy.moviesearch.model.RetrofitAPI
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object Retrofit {
    private const val BASE_URL = "https://openapi.naver.com/v1/"
    private var retrofit: Retrofit? = null

    val instance: RetrofitAPI
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!.create(RetrofitAPI::class.java)
        }
}