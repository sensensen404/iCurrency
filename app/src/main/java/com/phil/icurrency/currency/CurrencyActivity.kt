package com.phil.icurrency.currency

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phil.icurrency.*
import com.phil.icurrency.network.ServiceFactory
import com.phil.icurrency.realtime.BASE_URL
import com.phil.icurrency.realtime.RealTimeRateActivity

class CurrencyActivity : AppCompatActivity(), CurrencyRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        build()
    }

    private fun build() {
        val view = layoutInflater.inflate(R.layout.activity_currency, null)
        setContentView(view)
        CurrencyInterator(
            this@CurrencyActivity,
            ServiceFactory().newService(CurrencyService::class.java,
                BASE_URL
            ),
            view as CurrencyInterator.CurrencyPresenter,
            this
        )
    }

    override fun finishPage(currency: Currency) {
        val intent = Intent()
        intent.putExtra(RealTimeRateActivity.KEY_CURRENCY, currency)
        setResult(Activity.RESULT_OK, intent)
        this.finish()
    }
}