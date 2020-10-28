package com.dino.great.module.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dino.great.apiHandler.ApiService
import kotlinx.coroutines.launch

class PostListViewModel : ViewModel(){

    private var postResponse =  MutableLiveData<List<Post>?>()
    val responsePosts: LiveData<List<Post>?> get() = postResponse

    private var photoResponse =  MutableLiveData<List<Photo>?>()
    val responsePhotos: LiveData<List<Photo>?> get() = photoResponse

    private val _navigateToDetail = MutableLiveData<Post>()
    val eventNavigateDetail: LiveData<Post> get() = _navigateToDetail

    // Event which triggers
    private val eventRetry = MutableLiveData<Boolean>()
    val retry: LiveData<Boolean> get() = eventRetry

    fun onPostClicked(post: Post) {
        _navigateToDetail.value = post
    }

    fun onPostNavigated() {
        _navigateToDetail.value = null
    }

    fun onRetryClick(){
        eventRetry.value = true
    }
    fun onRetryComplete(){
        eventRetry.value = false
    }

    //Get Posts from API
    fun getPosts() {
        viewModelScope.launch {
            val response =  ApiService.instance.getPosts()
            try {
                if (response.isSuccessful){
                    postResponse.value = response.body()
                }
            } catch (e: Exception){
                postResponse.value = null
            }
        }
    }

    //Get Photos from API
    fun getPhotos() {
        viewModelScope.launch {
            val response =  ApiService.instance.getPhotos()
            try {
                if (response.isSuccessful){
                    photoResponse.value = response.body()
                }
            } catch (e: Exception){
                photoResponse.value = null
            }
        }
    }
}