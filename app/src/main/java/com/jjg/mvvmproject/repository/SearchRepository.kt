package com.jjg.mvvmproject.repository

import com.jjg.mvvmproject.repository.remote.services.SearchService

class SearchRepository(private val searchService: SearchService = SearchService.service) : NetworkRepository() {

    suspend fun getWebSearch(
        page: Int,
        size: Int,
        sort: String?,
        query: String?
    ) = apiCall {
        searchService.getWebSearch(page = page, size = size, sort = sort, query = query)
    }


    suspend fun getImageSearch(
        page: Int,
        size: Int,
        sort: String?,
        query: String?
    ) = apiCall {
        searchService.getImageSearch(page = page, size = size, sort = sort, query = query)
    }


}