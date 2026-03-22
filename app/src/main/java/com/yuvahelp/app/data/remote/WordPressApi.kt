package com.yuvahelp.app.data.remote

import com.yuvahelp.app.data.model.WordPressPostDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WordPressApi {
    @GET("wp-json/wp/v2/posts?_embed")
    suspend fun getPosts(
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1,
        @Query("search") search: String? = null
    ): List<WordPressPostDto>
}
