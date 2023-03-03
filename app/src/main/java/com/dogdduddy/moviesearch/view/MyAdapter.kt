package com.dogdduddy.moviesearch.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dogdduddy.moviesearch.databinding.ItemLayoutBinding
import com.dogdduddy.moviesearch.model.Post

class MyAdapter
    : PagingDataAdapter<Post, MyAdapter.MyViewHolder>(IMAGE_COMPARATOR) {

    class MyViewHolder(private val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post : Post){
            Log.d("tst5", "bind: ${post} 바인드됨")
            binding.userIdText.text = post.title
            binding.idText.text = post.pubdate
            binding.titleText.text = post.userRating
            binding.bodyText.text = post.link
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Post, newItem: Post) =
                oldItem == newItem
        }
    }
}