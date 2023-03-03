package com.dogdduddy.moviesearch.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dogdduddy.moviesearch.BuildConfig
import com.dogdduddy.moviesearch.databinding.ActivityMainBinding
import com.dogdduddy.moviesearch.model.MyLoadStateAdapter
import com.dogdduddy.moviesearch.model.PostResponse
import com.dogdduddy.moviesearch.model.Retrofit
import com.dogdduddy.moviesearch.model.RetrofitAPI
import com.dogdduddy.moviesearch.viewmodel.movieViewModel
import com.dogdduddy.moviesearch.viewmodel.moviewRepository
import com.dogdduddy.moviesearch.viewmodel.viewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var viewModel: movieViewModel
    private lateinit var binding: ActivityMainBinding
    private val myAdapter by lazy { MyAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 어댑터 연결
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val repository = moviewRepository()
        val viewModelFactory = viewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(movieViewModel::class.java)

        // 받아온 값을 리싸이클러뷰에 보여줌
        binding.button.setOnClickListener {
            if (binding.editTextView.text.toString() != "") {
                viewModel.searchPost(binding.editTextView.text.toString())
                Log.d("tst5", "클릭됐음.")

                // 포커스 없애기
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.editTextView.windowToken, 0)
            }
        }

        // 관찰하여 submitData 메소드로 넘겨줌
        viewModel.result.observe(this, Observer {
            myAdapter.submitData(this.lifecycle, it)
            Log.d("tst5", "호출됐음.")
        })

        // 로딩 상태 리스너
        myAdapter.addLoadStateListener { combinedLoadStates ->
            binding.apply {
                // 로딩 중 일 때
                progressBar.isVisible = combinedLoadStates.source.refresh is LoadState.Loading

                // 로딩 중이지 않을 때 (활성 로드 작업이 없고 에러가 없음)
                recyclerView.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading

                // 로딩 에러 발생 시
                retryButton.isVisible = combinedLoadStates.source.refresh is LoadState.Error
                errorText.isVisible = combinedLoadStates.source.refresh is LoadState.Error

                // 활성 로드 작업이 없고 에러가 없음 & 로드할 수 없음 & 개수 1 미만 (empty)
                if (combinedLoadStates.source.refresh is LoadState.NotLoading
                    && combinedLoadStates.append.endOfPaginationReached
                    && myAdapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    emptyText.isVisible = true
                } else {
                    emptyText.isVisible = false
                }
            }
        }

        // 다시 시도하기 버튼
        binding.retryButton.setOnClickListener {
            myAdapter.retry()
        }

        binding.apply {
            recyclerView.setHasFixedSize(true) // 사이즈 고정
            // header, footer 설정
            recyclerView.adapter = myAdapter.withLoadStateHeaderAndFooter(
                header = MyLoadStateAdapter { myAdapter.retry() },
                footer = MyLoadStateAdapter { myAdapter.retry() }
            )
        }

    }
}