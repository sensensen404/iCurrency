package com.phil.icurrency.realtime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phil.icurrency.R
import com.phil.icurrency.network.ServiceFactory
import com.phil.icurrency.currency.Currency
import com.phil.icurrency.currency.CurrencyActivity

class RealTimeRateActivity : AppCompatActivity(), RealTimeRouter {

    private lateinit var interator: RealTimeRateInterator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        build()
    }

    private fun build() {
        val view = layoutInflater.inflate(R.layout.activity_real_time_rate, null)
        setContentView(view)
        interator = RealTimeRateInterator(
            this@RealTimeRateActivity,
            ServiceFactory().newService(
                RealTimeRateService::class.java,
                BASE_URL
            ),
            view as RealTimeRateInterator.RealTimeRatePresenter,
            this
        )
    }

    override fun startCurrencyActivity() {
        val intent = Intent(this, CurrencyActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_CURRENCY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val currency = data.getSerializableExtra(KEY_CURRENCY) as Currency
            interator.changeCurrency(currency)
        }
    }

    companion object {
        const val KEY_CURRENCY = "KEY_CURRENCY"
        const val REQUEST_CODE_CURRENCY = 0
    }

}

const val BASE_URL = "http://api.currencylayer.com"
