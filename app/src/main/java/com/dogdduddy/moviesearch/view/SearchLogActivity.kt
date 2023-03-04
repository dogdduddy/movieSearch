package com.dogdduddy.moviesearch.view

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.dogdduddy.moviesearch.App
import com.dogdduddy.moviesearch.R
import com.dogdduddy.moviesearch.databinding.ActivitySearchLogBinding
import com.dogdduddy.moviesearch.model.local.AppDatabase
import com.dogdduddy.moviesearch.model.local.SearchLog
import com.dogdduddy.moviesearch.viewmodel.MovieViewModel
import com.dogdduddy.moviesearch.viewmodel.MovieViewModelFactory
import com.google.android.material.chip.Chip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.util.Date

class SearchLogActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchLogBinding
    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as App).movieRepository,(application as App).searchLogRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.searchLogLiveData.observe(this, Observer { listData ->
            listData.forEach { data ->
                binding.logChipGroup.addView(Chip(this).apply {
                    chipBackgroundColor = ColorStateList.valueOf(resources.getColor(R.color.grey_200, null))
                    text = data.keyword // chip 텍스트 설정
                    setOnClickListener {
                        val intent = Intent(this@SearchLogActivity, MainActivity::class.java)
                        intent.putExtra("keyword", data.keyword)
                        startActivity(intent)
                    }
                }, 0)
            }
        })
    }
}