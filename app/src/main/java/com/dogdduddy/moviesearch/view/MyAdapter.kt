package com.dogdduddy.moviesearch.view

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dogdduddy.moviesearch.R
import com.dogdduddy.moviesearch.databinding.ItemLayoutBinding
import com.dogdduddy.moviesearch.model.remote.Post

class MyAdapter
    : PagingDataAdapter<Post, MyAdapter.MyViewHolder>(IMAGE_COMPARATOR) {

    class MyViewHolder(private val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post : Post){
            val context = binding.root.context
            binding.titleTextView.text = context.getString(R.string.set_title) + Html.fromHtml(post.title, 0).toString()
            binding.pubdateTextView.text = context.getString(R.string.set_pubdate) + post.pubdate
            binding.ratingTextView.text = context.getString(R.string.set_rating) + post.userRating
            Glide.with(binding.root).load(post.image).into(binding.imageView)
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // item 클릭 시 웹뷰로 이동
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, WebViewActivity::class.java)
            intent.putExtra("url", getItem(position)!!.link)
            holder.itemView.context.startActivity(intent)
        }
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
