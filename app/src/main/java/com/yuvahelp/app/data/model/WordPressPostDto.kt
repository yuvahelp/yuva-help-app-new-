package com.yuvahelp.app.data.model

import com.squareup.moshi.Json

data class WordPressPostDto(
    val id: Long,
    val date: String,
    val title: RenderedText,
    val excerpt: RenderedText,
    val content: RenderedText,
    val link: String,
    @Json(name = "_embedded") val embedded: Embedded?
)

data class RenderedText(val rendered: String)

data class Embedded(
    @Json(name = "wp:featuredmedia") val featuredMedia: List<FeaturedMedia>?
)

data class FeaturedMedia(
    @Json(name = "source_url") val sourceUrl: String?
)
