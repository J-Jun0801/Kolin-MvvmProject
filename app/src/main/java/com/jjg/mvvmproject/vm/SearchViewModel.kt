package com.jjg.mvvmproject.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jjg.mvvmproject.repository.SearchRepository
import com.jjg.mvvmproject.repository.remote.models.WebDocumentDto
import com.jjg.mvvmproject.vm.pagingsource.SearchPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository = SearchRepository()) : ViewModel() {
    private var _webDocuments = MutableLiveData<Flow<PagingData<WebDocumentDto>>>()
    val webDocuments: LiveData<Flow<PagingData<WebDocumentDto>>> get() = _webDocuments

    fun reqWebSearch(wordSearch: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val pageSize = 20

            _webDocuments.postValue(Pager(PagingConfig(pageSize = pageSize)) {
                SearchPagingSource(searchRepository = searchRepository, query = wordSearch, size = pageSize)
            }.flow.cachedIn(viewModelScope))
        }
    }
}