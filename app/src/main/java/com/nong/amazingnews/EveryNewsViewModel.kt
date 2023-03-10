package com.nong.amazingnews

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.nong.amazingnews.fragment.EveryNewsPagingSource
import com.nong.amazingnews.repository.NewsRepository

class EveryNewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    val everyNewsFlow = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            maxSize = 50,
            prefetchDistance = 5,
            initialLoadSize = 30
        )
    ) { EveryNewsPagingSource(repository) }
        .flow
}