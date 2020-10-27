package com.dino.great.module.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dino.great.R
import com.dino.great.databinding.FragmentPostDetailBinding
import com.dino.great.module.list.Post
import com.dino.great.module.list.PostListListener
import com.dino.great.module.list.PostListingFragmentDirections
import com.dino.great.module.list.PostsAdapter
import com.dino.great.utilities.ImageHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.util.*

class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var viewModel: PostDetailViewModel
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var post: Post

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(PostDetailViewModel::class.java)
        binding.viewModel = viewModel
        setAdapter()
        setObservers()
        return binding.root
    }
    private fun setAdapter() {
        commentsAdapter = CommentsAdapter()
        binding.comments.adapter = commentsAdapter
    }

    override fun onStart() {
        super.onStart()
        if (arguments?.get("Post")!=null) {
            val jsonString = arguments?.get("Post") as String?
            post = Gson().fromJson(jsonString, Post::class.java)
            binding.post = post
            ImageHandler.setGlideImage(post.imageUrl, binding.backdrop)
            viewModel.getComments(post.id)
        }
    }
    //Observing live data
    private fun setObservers() {
        viewModel.responseComment.observe(viewLifecycleOwner, {
            it?.let { list ->
                if (list.isNotEmpty()) {
                    commentsAdapter.submitList(list)
                }
            }
        })
    }

}