package com.phil.icurrency.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory {

    fun <T> newService(clazz: Class<T>?, url: String?): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(RetrofitManager.getHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }
}