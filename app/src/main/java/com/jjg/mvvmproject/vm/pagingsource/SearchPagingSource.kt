package com.jjg.mvvmproject.vm.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jjg.mvvmproject.network.ResponseWrapper
import com.jjg.mvvmproject.repository.SearchRepository
import com.jjg.mvvmproject.repository.remote.models.WebDocumentDto
import timber.log.Timber

class SearchPagingSource(private val searchRepository: SearchRepository, private val query: String, private val size: Int) : PagingSource<Int, WebDocumentDto>() {

    private var isEnd = false

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WebDocumentDto> {
        val nextPageNumber = params.key ?: 1

        return if (isEnd) {
            LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
        } else {
            when (val response = searchRepository.getWebSearch(page = nextPageNumber, sort = null, size = size, query = query)) {
                is ResponseWrapper.Success -> {

                    val totalPage = response.body.meta.totalCount
                    isEnd = response.body.meta.isEnd
                    Timber.e(">>>>${response.body.meta}")
                    LoadResult.Page(
                        data = response.body.documents ?: emptyList(),
                        prevKey = null, // 이전 페이지는 불러오지 않음
                        nextKey = if (totalPage < nextPageNumber) null else nextPageNumber + 1
                    )
                }
                else -> {
                    LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
                }
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, WebDocumentDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}