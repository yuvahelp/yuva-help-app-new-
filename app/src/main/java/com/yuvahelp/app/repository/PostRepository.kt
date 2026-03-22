package com.yuvahelp.app.repository

import android.text.Html
import com.yuvahelp.app.data.local.PostDao
import com.yuvahelp.app.data.local.toDomain
import com.yuvahelp.app.data.local.toEntity
import com.yuvahelp.app.data.model.Post
import com.yuvahelp.app.data.model.WordPressPostDto
import com.yuvahelp.app.data.remote.WordPressApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepository(
    private val api: WordPressApi,
    private val dao: PostDao
) {
    fun observeAll(): Flow<List<Post>> = dao.observePosts().map { list -> list.map { it.toDomain() } }

    fun observeCategory(category: String): Flow<List<Post>> =
        dao.observeCategory(category).map { list -> list.map { it.toDomain() } }

    fun search(query: String): Flow<List<Post>> = dao.searchPosts(query).map { list -> list.map { it.toDomain() } }

    suspend fun getPost(id: Long): Post? = dao.getById(id)?.toDomain()

    suspend fun refreshPosts(): List<Post> {
        val apiPosts = api.getPosts()
        val mapped = apiPosts.map { it.toDomainPost() }
        dao.insertAll(mapped.map { it.toEntity() })
        return mapped
    }

    private fun WordPressPostDto.toDomainPost(): Post {
        val plainTitle = Html.fromHtml(title.rendered, Html.FROM_HTML_MODE_COMPACT).toString()
        val plainExcerpt = Html.fromHtml(excerpt.rendered, Html.FROM_HTML_MODE_COMPACT).toString()
        val thumb = embedded?.featuredMedia?.firstOrNull()?.sourceUrl.orEmpty()
        val categoryGuess = classifyCategory("$plainTitle $plainExcerpt")

        return Post(
            id = id,
            title = plainTitle,
            excerpt = plainExcerpt,
            content = content.rendered,
            date = date,
            thumbnailUrl = thumb,
            url = link,
            category = categoryGuess
        )
    }

    private fun classifyCategory(text: String): String {
        val lower = text.lowercase()
        return when {
            "result" in lower -> "Results"
            "admit" in lower || "hall ticket" in lower -> "Admit Cards"
            "scheme" in lower || "yojana" in lower -> "Government Schemes"
            "job" in lower || "recruitment" in lower || "vacancy" in lower -> "Latest Jobs"
            else -> "Education News"
        }
    }
}
