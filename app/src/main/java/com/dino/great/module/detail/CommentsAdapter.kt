package com.dino.great.module.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dino.great.databinding.CommentItemBinding

class CommentsAdapter : ListAdapter<Comment, ViewHolder>(DetailsDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CommentItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind((item))
    }
}

class ViewHolder(private val binding: CommentItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Comment) {
        binding.comment = item
        binding.executePendingBindings()
    }
}

class DetailsDiffCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}