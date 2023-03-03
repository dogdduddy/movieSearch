package com.dogdduddy.moviesearch.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dogdduddy.moviesearch.R
import com.dogdduddy.moviesearch.model.Retrofit
import com.dogdduddy.moviesearch.model.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getResultSearch()
    }
    fun getResultSearch() {
        val apiInterface: RetrofitAPI = Retrofit.instance.create(RetrofitAPI::class.java)
        val call: Call<String> =
                apiInterface.getSearchResult(getString(R.string.client_id), getString(R.string.client_secret), "movie.json", "안드로이드")
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful() && response.body() != null) {
                    val result: String = response.body()!!
                    Log.e(TAG, "성공 : $result")
                } else {
                    Log.e(TAG, "실패 : " + response.body())
                }
            }

            override fun onFailure(call: Call<String?>?, t: Throwable) {
                Log.e(TAG, "에러 : " + t.message)
            }
        })
    }
}