package com.phil.icurrency.currency

import com.phil.icurrency.realtime.CURRENCY_LAYER_ACCESS_KEY
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("/list")
    fun fetchSupportedCurrencies(@Query("access_key") accessKey: String = CURRENCY_LAYER_ACCESS_KEY): Observable<Response<SupportedCurrency>>
}

data class SupportedCurrency(
    val success: Boolean,
    val term: String,
    val privacy: String,
    val currencies: Map<String, String>
)