package com.dino.great.module.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dino.great.databinding.PostItemBinding

class PostsAdapter(private val clickListener: PostListListener) : ListAdapter<PostAndImages, ViewHolder>(
    RecentDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind((item),clickListener)
    }
}

class ViewHolder(val binding: PostItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: PostAndImages, clickListener: PostListListener) {
        binding.post = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}

class PostListListener(val clickListener: (employee: PostAndImages) -> Unit) {
    fun onClick(employee: PostAndImages) = clickListener(employee)
}

class RecentDiffCallback : DiffUtil.ItemCallback<PostAndImages>() {
    override fun areItemsTheSame(oldItem: PostAndImages, newItem: PostAndImages): Boolean {
        return oldItem.mPosts == newItem.mPosts
    }

    override fun areContentsTheSame(oldItem: PostAndImages, newItem: PostAndImages): Boolean {
        return oldItem.mPosts == newItem.mPosts
    }
}