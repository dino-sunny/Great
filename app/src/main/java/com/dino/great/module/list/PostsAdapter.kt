package com.dino.great.module.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dino.great.databinding.PostItemBinding
import com.dino.great.utilities.ImageHandler

class PostsAdapter(private val clickListener: PostListListener) : ListAdapter<Post, ViewHolder>(
    RecentDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        ImageHandler.setGlideImage(item.imageUrl, holder.binding.postImage)
        holder.bind((item),clickListener)
    }
}

class ViewHolder(val binding: PostItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Post, clickListener: PostListListener) {
        binding.post = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}

//Listener to handle item click actions
class PostListListener(val clickListener: (post: Post) -> Unit) {
    fun onClick(post: Post) = clickListener(post)
}

class RecentDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}