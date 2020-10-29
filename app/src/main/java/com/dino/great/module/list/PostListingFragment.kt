package com.dino.great.module.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dino.great.R
import com.dino.great.databinding.FragmentPostBinding
import com.dino.great.utilities.AppUtils.Companion.postAndPhotos
import com.dino.great.utilities.NetworkCheck
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_post.*
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
        binding = FragmentPostBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
        binding.viewModel = viewModel

        setAdapter()
        setObservers()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getPosts()
    }

    //Get the posts if network connection available else .
    //network error message
    private fun getPosts() {
        if (NetworkCheck.isOnline(requireContext())) {
            animationView.visibility = View.VISIBLE
            noInternetLayout.visibility = View.GONE
            viewModel.getPosts()
        }else{
            noInternetLayout.visibility = View.VISIBLE
        }
    }

    //Post list Adapter
    private fun setAdapter() {
        binding.posts.setHasFixedSize(true)
        postsAdapter = PostsAdapter(
            PostListListener { post ->
                viewModel.onPostClicked(post)
            })
        binding.posts.adapter = postsAdapter
//        postsAdapter.submitList(FakePostData.posts.toMutableList())
    }

    //Observing live data
    private fun setObservers() {
        viewModel.retry.observe(viewLifecycleOwner, { isClicked ->
            if (isClicked) {
                getPosts()
                viewModel.onRetryComplete()
            }
        })
        viewModel.responsePosts.observe(viewLifecycleOwner, {
            it?.let { list ->
                if (list.isNotEmpty()) {
                    this.post = list
                    viewModel.getPhotos()
                }else connectionAlerts()

            }
        })
        viewModel.responsePhotos.observe(viewLifecycleOwner, {
            it?.let { list ->
                animationView.visibility = View.GONE
                if (list.isNotEmpty()) {
                    postsAdapter.submitList(postAndPhotos(it,post))
                }else connectionAlerts()

            }
        })
        //Navigate action handling
        viewModel.eventNavigateDetail.observe(viewLifecycleOwner, { post ->
            post?.let { navigateToDetails(post)}
        })
    }

    //Navigate to Details with post data
    private fun navigateToDetails(post: Post) {
        val mData = Gson().toJson(post)
        findNavController().navigate(
            PostListingFragmentDirections
                .actionListFragmentToDetailFragment(mData)
        )
        viewModel.onPostNavigated()
    }

    //Connection timeout alert and retry dialog
    private fun  connectionAlerts(){
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(getString(R.string.timeout_message))
            // if the dialog is cancelable
            .setCancelable(false)
            .setPositiveButton(getString(R.string.retry)) { dialog, _ ->
                dialog.dismiss()
                getPosts()
            }

        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.timeout))
        alert.show()
    }
}