package com.dino.great.module.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dino.great.R
import com.dino.great.databinding.FragmentPostBinding
import com.google.gson.Gson
import java.util.*

class PostListingFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private lateinit var viewModel: PostListViewModel
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var post: List<Post>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)
        viewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
        binding.viewModel = viewModel

        setAdapter()
        setObservers()

        return binding.root
    }
    

    //Observing live data
    private fun setObservers() {
        viewModel.responsePosts.observe(viewLifecycleOwner, {
            it?.let { list ->
                if (list.isNotEmpty()) {
                    this.post = list
                    viewModel.getPhotos()
                }
            }
        })
        viewModel.responsePhotos.observe(viewLifecycleOwner, {
            it?.let { list ->
                if (list.isNotEmpty()) {
                    for (item in post){
                        val index = Random().nextInt(it.size - 1)
                        val imageUrl: String? = it[index].thumbnailUrl
                        item.imageUrl = imageUrl
                    }
                    postsAdapter.submitList(post)

                }
            }
        })
        viewModel.eventNavigateDetail.observe(viewLifecycleOwner, { post ->
            post?.let {
                val mData = Gson().toJson(it)
                findNavController().navigate(
                    PostListingFragmentDirections
                        .actionListFragmentToDetailFragment(mData)
                )
                viewModel.onPostNavigated()
            }
        })
    }

    private fun setAdapter() {
        binding.posts.setHasFixedSize(true)
        postsAdapter = PostsAdapter(
            PostListListener { post ->
                viewModel.onPostClicked(post)
            })
        binding.posts.adapter = postsAdapter
    }
}