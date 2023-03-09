package com.nong.amazingnews.repository

import com.nong.amazingnews.network.GetEveryNewsListResponse
import com.nong.amazingnews.repository.remote.NewsDataSource

class NewsRepository {

    private val newsDataSource = NewsDataSource()

    suspend fun getEveryNews(
        domain: String = "engadget.com",
        page: Int = 10,
        pageSize: Int
    ): GetEveryNewsListResponse? {
        val req = newsDataSource.getEveryNews(domain, page, pageSize)

        if (req.isFailed || !req.isSuccess) return null

        return req.body
    }
}