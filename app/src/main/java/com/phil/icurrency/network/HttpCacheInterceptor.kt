package com.phil.icurrency.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response


class HttpCacheInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        if (!isNetworkConnected(context)) {
            requestBuilder.cacheControl(CacheControl.FORCE_CACHE)
        }
        val response = chain.proceed(requestBuilder.build())
        return response.newBuilder()
            .removeHeader(HEADER_NAME_PRAGMA)
            .header(HEADER_NAME_CACHE_CONTROL, "$MAX_AGE_PREFIX$MAX_AGE")
            .build()
    }


    private fun isNetworkConnected(context: Context): Boolean {
        val mConnectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable
        }
        return false
    }

    companion object {
        const val HEADER_NAME_PRAGMA = "Pragma"
        const val HEADER_NAME_CACHE_CONTROL = "Cache-Control"
        const val MAX_AGE = 60 * 30
        const val MAX_AGE_PREFIX = "public, max-age="
    }

}