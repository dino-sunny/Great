package com.dino.great.utilities

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dino.great.R

object ImageHandler{

    /**
     * Load the image from the url to image view using Glide
     */
    fun setGlideImage(imageUrl: String?, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.picture)
            .error(R.drawable.picture)
            .into(imageView)
    }
}