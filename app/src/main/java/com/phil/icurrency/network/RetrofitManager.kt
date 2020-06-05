package com.phil.icurrency.network

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

object RetrofitManager {

    private const val CACHE_FILE_NAME = "retrofit_cache"

    lateinit var client: OkHttpClient

    fun init(context: Context) {
        initOkHttpClient(context)
    }

    private fun initOkHttpClient(context: Context) {
        val cache = Cache(File(context.cacheDir, CACHE_FILE_NAME), 1024 * 1024 * 100)
        val cacheInterceptor = HttpCacheInterceptor(context)
        client = OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(cacheInterceptor)
            .build()
    }

    fun getHttpClient(): OkHttpClient {
        return client
    }
}