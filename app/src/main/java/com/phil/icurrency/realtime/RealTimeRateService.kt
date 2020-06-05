package com.phil.icurrency.realtime

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RealTimeRateService {

    @GET("/live")
    fun fetchRealTimeRates(@Query("access_key") accessKey: String = CURRENCY_LAYER_ACCESS_KEY): Observable<Response<RealTimeRateResponse>>
}

data class RealTimeRateResponse(
    val success: Boolean,
    val term: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Float>
)

const val CURRENCY_LAYER_ACCESS_KEY = "78068efb5fe39b6b2c487d3009c023a7"