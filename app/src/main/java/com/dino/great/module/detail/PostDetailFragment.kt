package com.dino.great.module.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dino.great.R
import com.dino.great.databinding.FragmentPostDetailBinding
import com.dino.great.module.list.Post
import com.dino.great.utilities.ImageHandler
import com.dino.great.utilities.NetworkCheck
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_post_detail.*

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

    //Adapter to set comments
    private fun setAdapter() {
        commentsAdapter = CommentsAdapter()
        binding.comments.adapter = commentsAdapter
    }

    override fun onStart() {
        super.onStart()
        getDataFromIntent()
    }

    //Get post details from arguments passed.
    private fun getDataFromIntent() {
        if (arguments?.get(getString(R.string.post))!=null) {
            val jsonString = arguments?.get(getString(R.string.post)) as String?
            post = Gson().fromJson(jsonString, Post::class.java)
            setData()
        }
    }

    //Set data to the UI using binding
    private fun setData() {
        binding.post = post
        ImageHandler.setGlideImage(post.imageUrl, binding.backdrop)
        getComments()
    }

    //Get the posts if network connection available else .
    //network error message
    private fun getComments() {
        if (NetworkCheck.isOnline(requireContext())) {
            animationView.visibility = View.VISIBLE
            binding.noInternetLayout.visibility = View.GONE
            viewModel.getComments(post.id)

        }else{
            binding.noInternetLayout.visibility = View.VISIBLE
        }
    }

    //Observing live data
    private fun setObservers() {
        viewModel.responseComment.observe(viewLifecycleOwner, {
            it?.let { list ->
                animationView.visibility = View.GONE
                updateComments(list)}
        })
        viewModel.retry.observe(viewLifecycleOwner, { isClicked ->
            if (isClicked) {
                getComments()
                viewModel.onRetryComplete()
            }
        })
        viewModel.back.observe(viewLifecycleOwner, { isClicked ->
            if (isClicked) {
                findNavController().popBackStack()
                viewModel.onBackComplete()
            }
        })
    }

    //Update the adapter with 1st 3 comments
    //if the list size more than 3 else update the list.
    private fun updateComments(list: List<Comment>) {
        if (list.isNotEmpty() && list.size>3) {
            commentsAdapter.submitList(list.subList(0,3))
        }else{
            commentsAdapter.submitList(list)
        }
    }

}