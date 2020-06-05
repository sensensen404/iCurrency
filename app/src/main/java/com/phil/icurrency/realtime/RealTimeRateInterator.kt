package com.phil.icurrency.realtime

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.phil.icurrency.currency.Currency
import com.rxjava.rxlife.RxLife
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RealTimeRateInterator(
    val lifecycleOwner: LifecycleOwner,
    val service: RealTimeRateService,
    val presenter: RealTimeRatePresenter,
    val router: RealTimeRouter
) {

    var realTimeRates = listOf<RealTimeRate>()

    var amount = 1L

    var defaultCurrency = RealTimeRate("USD", 1.0f, "1 USD / USD")

    var selectedCurrency = defaultCurrency

    init {
        fetchRealTimeRateList()
        setFloatingButtonListener()
        setOnAmountChangedListener()
    }

    private fun setOnAmountChangedListener() {
        presenter.onAmountChanged().observe(lifecycleOwner, Observer { amount ->
            this.amount = amount
            presenter.showData(getRealTimePrice())
        })
    }

    private fun setFloatingButtonListener() {
        presenter.onCurrencySelectedButtonClick().observe(lifecycleOwner, Observer {
            router.startCurrencyActivity()
        })
    }

    private fun fetchRealTimeRateList() {
        presenter.setIndicatorStatus(true)
        service.fetchRealTimeRates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(RxLife.`as`(lifecycleOwner))
            .subscribe({ response ->
                response.body()?.let {
                    realTimeRates = it.quotes.map { rate ->
                        val currencyAbbr = rate.key.removePrefix(defaultCurrency.name)
                        RealTimeRate(
                            currencyAbbr,
                            rate.value,
                            "$amount ${defaultCurrency.name} / $currencyAbbr"
                        )
                    }
                    presenter.showData(realTimeRates)
                    presenter.setIndicatorStatus(false)
                }
            }, {
                //ShowError
                presenter.setIndicatorStatus(false)
            })
    }

    fun changeCurrency(currency: Currency) {
        selectedCurrency = realTimeRates.find { it.name == currency.abbr }!!
        presenter.showData(getRealTimePrice())
    }

    private fun getRealTimePrice(): List<RealTimeRate> {
        return realTimeRates.map {
            RealTimeRate(
                it.name,
                amount * it.rate / selectedCurrency.rate,
                "$amount ${selectedCurrency.name} / ${it.name}"
            )
        }
    }

    interface RealTimeRatePresenter {
        fun showData(realTimeRates: List<RealTimeRate>)

        fun onCurrencySelectedButtonClick(): LiveData<Void>

        fun onAmountChanged(): LiveData<Long>

        fun setIndicatorStatus(isShow: Boolean)
    }
}

data class RealTimeRate(val name: String, val rate: Float, val amountDescription: String)