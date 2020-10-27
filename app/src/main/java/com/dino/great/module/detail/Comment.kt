package com.dino.great.module.detail

data class Comment(val id: Int = 0,
                val postId: Int = 0,
                val name: String?,
                val email: String?,
                var body: String?)