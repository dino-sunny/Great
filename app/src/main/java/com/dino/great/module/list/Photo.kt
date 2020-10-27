package com.dino.great.module.list

data class Photo(val id: Int = 0,
                 val albumId: Int = 0,
                 val title: String?,
                 val url: String?,
                 val thumbnailUrl: String?)