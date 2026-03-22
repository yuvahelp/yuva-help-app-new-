package com.yuvahelp.app.data.model

data class Post(
    val id: Long,
    val title: String,
    val excerpt: String,
    val content: String,
    val date: String,
    val thumbnailUrl: String,
    val url: String,
    val category: String
)
