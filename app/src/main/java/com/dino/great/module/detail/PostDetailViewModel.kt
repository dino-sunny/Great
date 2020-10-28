package com.dino.great.module.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dino.great.apiHandler.ApiService
import kotlinx.coroutines.launch

class PostDetailViewModel : ViewModel(){

    private var commentResponse =  MutableLiveData<List<Comment>?>()
    val responseComment: LiveData<List<Comment>?> get() = commentResponse

    // Event which triggers
    private val eventRetry = MutableLiveData<Boolean>()
    val retry: LiveData<Boolean> get() = eventRetry

    fun onRetryClick(){
        eventRetry.value = true
    }
    fun onRetryComplete(){
        eventRetry.value = false
    }

    //Get the comments from API
    fun getComments(postId: Int) {
        viewModelScope.launch {
            val response =  ApiService.instance.getComments(postId)
            try {
                if (response.isSuccessful){
                    commentResponse.value = response.body()
                }
            } catch (e: Exception){
                commentResponse.value = null
            }
        }
    }
}