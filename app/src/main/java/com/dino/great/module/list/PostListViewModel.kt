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

    init {
        getPosts()
    }

    private fun getPosts() {
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