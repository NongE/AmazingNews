package com.nong.amazingnews.fragment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nong.amazingnews.network.Articles
import com.nong.amazingnews.repository.NewsRepository

class EveryNewsPagingSource(
    private val repository: NewsRepository
) : PagingSource<Int, Articles>() {

    override fun getRefreshKey(state: PagingState<Int, Articles>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
        return try {
            val pageNumber = params.key ?: 1
            val preKey = if (pageNumber == 1) null else pageNumber - 1

            val response = repository.getEveryNews(page = pageNumber, pageSize = 10)
                ?: throw Exception("Response is null")

            return LoadResult.Page(
                data = response.articles,
                prevKey = preKey,
                nextKey = if (response.articles.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}