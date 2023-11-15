package com.jjg.mvvmproject.repository.remote.services

import com.jjg.mvvmproject.network.NetworkResponse
import com.jjg.mvvmproject.network.NetworkRetrofit
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto
import com.jjg.mvvmproject.repository.remote.models.WebDocumentDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/v2/search/web")
    suspend fun getWebSearch(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String? = "accuracy",
        @Query("query") query: String? = null,
    ): Response<NetworkResponse<List<WebDocumentDto>>>

    @GET("/v2/search/image")
    suspend fun getImageSearch(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String? = "accuracy",
        @Query("query") query: String? = null,
    ): Response<NetworkResponse<List<ImageDocumentDto>>>

    companion object {
        val service: SearchService = NetworkRetrofit.getRetrofit().create(SearchService::class.java)
    }
}