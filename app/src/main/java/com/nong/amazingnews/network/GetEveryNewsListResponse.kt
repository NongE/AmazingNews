package com.nong.amazingnews.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class GetEveryNewsListResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Articles>
)

@Parcelize
data class Articles(
    val source: @RawValue Any?,
    val author: String,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) : Parcelable

data class Source(
    val id: String,
    val name: String
)