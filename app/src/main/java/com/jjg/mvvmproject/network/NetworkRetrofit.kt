package com.jjg.mvvmproject.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkRetrofit {
    private const val TIMEOUT_CONNECT: Long = 30
    private const val TIMEOUT_WRITE: Long = TIMEOUT_CONNECT
    private const val TIMEOUT_READ: Long = TIMEOUT_CONNECT

    fun getRetrofit(timeoutSec: Long = -1): Retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .client(provideOkHttpClient(ApiInterceptor(), timeoutSec))
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()


    private fun provideOkHttpClient(
        interceptor: Interceptor,
        timeoutSec: Long = -1
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(if (timeoutSec == -1L) TIMEOUT_CONNECT else timeoutSec, TimeUnit.SECONDS)
        .readTimeout(if (timeoutSec == -1L) TIMEOUT_READ else timeoutSec, TimeUnit.SECONDS)
        .writeTimeout(if (timeoutSec == -1L) TIMEOUT_WRITE else timeoutSec, TimeUnit.SECONDS)
        .apply {
            addInterceptor(interceptor)
            HttpInterceptorProvider.get().forEach { addInterceptor(it) }
        }.build()

}
