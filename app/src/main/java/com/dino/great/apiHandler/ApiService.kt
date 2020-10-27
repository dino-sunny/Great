package com.dino.great.apiHandler

import com.dino.great.BuildConfig
import com.dino.great.module.detail.Comment
import com.dino.great.module.list.Photo
import com.dino.great.module.list.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    /* creating a singleton object for retrofit client */
    companion object {
        val instance: ApiService by lazy {
            ApiFactory.retrofit(BuildConfig.BASEURL).create(ApiService::class.java)
        }
    }

    /**
     * Post List*/
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>?>

    /**
     * Photo List*/
    @GET("photos")
    suspend fun getPhotos(): Response<List<Photo>?>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") postId: Int): Response<List<Comment>?>
}