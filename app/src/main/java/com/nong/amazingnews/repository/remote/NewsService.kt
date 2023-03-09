package com.nong.amazingnews.repository.remote

import com.nong.amazingnews.network.GetEveryNewsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/v2/everything?language=en")
    suspend fun getEveryNews(
        @Query("domains") domain: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<GetEveryNewsListResponse>
}