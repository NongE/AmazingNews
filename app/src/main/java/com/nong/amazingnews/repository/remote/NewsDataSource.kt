package com.nong.amazingnews.repository.remote

import com.nong.amazingnews.BuildConfig
import com.nong.amazingnews.network.GetEveryNewsListResponse
import com.nong.amazingnews.network.NewsResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsDataSource {

    inner class NewsServiceInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val a = chain.request().newBuilder()
                .addHeader(BuildConfig.API_HEADER_NAME, BuildConfig.API_HEADER_VALUE)
                .build()
            return chain.proceed(a)
        }
    }

    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(NewsServiceInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsService: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }

    suspend fun getEveryNews(
        domain: String = "engadget.com",
        page: Int,
        pageSize: Int
    ): NewsResponse<GetEveryNewsListResponse> {
        return newsServiceHandler { newsService.getEveryNews(domain, page, pageSize) }
    }

    suspend fun searchNewsFromEveryNews(
        domain: String = "engadget.com",
        page: Int,
        q: String,
        pageSize: Int
    ): NewsResponse<GetEveryNewsListResponse> {
        return newsServiceHandler { newsService.searchNewsFromEveryNews(domain, page, q, pageSize) }
    }

    private inline fun <T> newsServiceHandler(api: () -> retrofit2.Response<T>): NewsResponse<T> {
        return try {
            NewsResponse.success(api.invoke())
        } catch (e: Exception) {
            NewsResponse.failure(e)
        }
    }
}