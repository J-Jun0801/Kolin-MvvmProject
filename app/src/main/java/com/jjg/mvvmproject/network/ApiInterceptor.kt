package com.jjg.mvvmproject.network

import okhttp3.*
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset


class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "KakaoAK 393eb89c4e03ed1062a9b30c11ee5e74111")
//            .addHeader("Authorization", "KakaoAK 393eb89c4e03ed1062a9b30c11ee5e74")
            .build()

        return chain.proceed(request)
    }
}