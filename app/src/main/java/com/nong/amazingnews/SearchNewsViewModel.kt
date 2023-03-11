package com.nong.amazingnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.nong.amazingnews.fragment.EveryNewsPagingSource
import com.nong.amazingnews.fragment.SearchNewsPagingSource
import com.nong.amazingnews.repository.NewsRepository

class SearchNewsViewModel : ViewModel() {

    private val repository = NewsRepository()
    private var inputKeyword = ""
    private var searchingNewsPagingSource: SearchNewsPagingSource? = null
        get() {
            if(field == null || field?.invalid == true){
                field = SearchNewsPagingSource(repository, inputKeyword) { exception ->
                    _searchExceptionLiveData.postValue(Event(exception))
                }
            }
            return field
        }

    val flow = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            maxSize = 20,
            prefetchDistance = 5,
            initialLoadSize = 5
        )
    ) { searchingNewsPagingSource!! }
        .flow

    private val _searchExceptionLiveData = MutableLiveData<Event<SearchNewsPagingSource.SearchException>>()
    val searchExceptionLiveData: LiveData<Event<SearchNewsPagingSource.SearchException>> = _searchExceptionLiveData

    fun query(keyword: String){
        inputKeyword = keyword
        searchingNewsPagingSource?.invalidate()
    }
}