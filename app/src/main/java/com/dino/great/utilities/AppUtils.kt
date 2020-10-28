package com.dino.great.utilities

import com.dino.great.module.list.Photo
import com.dino.great.module.list.Post
import java.util.*

class AppUtils {
    companion object {

        //Adding images to post in a random order.
        fun postAndPhotos(it: List<Photo>, post: List<Post>): List<Post> {
            for (item in post){
                val index = Random().nextInt(it.size - 1)
                val imageUrl: String? = it[index].thumbnailUrl
                item.imageUrl = imageUrl
            }
            return post
        }
    }
}