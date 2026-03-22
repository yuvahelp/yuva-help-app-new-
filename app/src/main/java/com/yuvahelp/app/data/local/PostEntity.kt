package com.yuvahelp.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yuvahelp.app.data.model.Post

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val excerpt: String,
    val content: String,
    val date: String,
    val thumbnailUrl: String,
    val url: String,
    val category: String
)

fun PostEntity.toDomain() = Post(id, title, excerpt, content, date, thumbnailUrl, url, category)
fun Post.toEntity() = PostEntity(id, title, excerpt, content, date, thumbnailUrl, url, category)
