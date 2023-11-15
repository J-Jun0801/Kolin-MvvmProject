package com.jjg.mvvmproject.network

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object HttpInterceptorProvider {
    fun get(): List<Interceptor> {
        return listOf(HttpLoggingInterceptor(ApiLogger()).apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }
}
