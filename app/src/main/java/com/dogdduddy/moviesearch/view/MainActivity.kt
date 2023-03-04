package com.dogdduddy.moviesearch.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.dogdduddy.moviesearch.databinding.ActivityMainBinding
import com.dogdduddy.moviesearch.model.local.AppDatabase
import com.dogdduddy.moviesearch.model.local.SearchLog
import com.dogdduddy.moviesearch.viewmodel.movieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val viewModel: movieViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val myAdapter by lazy { MyAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ///Room Test
        val db = AppDatabase.getInstance(this)
        val dao = db.SearchLogDao()
        ////

        // 어댑터 연결
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 받아온 값을 리싸이클러뷰에 보여줌
        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                launch { dao.insertLog(SearchLog(binding.editTextView.text.toString(), Date())) }.join()
                val log = async { dao.getAll() }.await()
                Log.e("RoomTest", "log : $log")
            }
            /*
            if (binding.editTextView.text.toString() != "") {
                viewModel.searchPost(binding.editTextView.text.toString())

                // 포커스 없애기
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.editTextView.windowToken, 0)
            }

             */
        }

        // 관찰하여 submitData 메소드로 넘겨줌
        viewModel.result.observe(this, Observer {
            myAdapter.submitData(this.lifecycle, it)
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