package com.yuvahelp.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY date DESC")
    fun observePosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE category = :category ORDER BY date DESC")
    fun observeCategory(category: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE title LIKE '%' || :query || '%' OR excerpt LIKE '%' || :query || '%' ORDER BY date DESC")
    fun searchPosts(query: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): PostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)
}
