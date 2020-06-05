package com.phil.icurrency.currency

import androidx.lifecycle.LifecycleOwner
import com.phil.icurrency.currency.adapter.CurrencyAdapter
import com.rxjava.rxlife.RxLife
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable

class CurrencyInterator(
    val lifecycleOwner: LifecycleOwner,
    val service: CurrencyService,
    val presenter: CurrencyPresenter,
    val router: CurrencyRouter
) : CurrencyAdapter.CurrencyItemPresenter {

    private var currencyList: List<Currency> = mutableListOf()

    init {
        fetchSupportedCurrencies()
    }

    private fun fetchSupportedCurrencies() {
        presenter.setIndicator(true)
        service.fetchSupportedCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(RxLife.`as`(lifecycleOwner))
            .subscribe({ response ->
                if (response.isSuccessful) {
                    response.body()?.currencies?.let {
                        currencyList = it.map { currency ->
                            Currency(currency.key, currency.value)
                        }
                        presenter.showData(currencyList, this)
                    }
                }
                presenter.setIndicator(false)
            }, {
                //ShowError
                presenter.setIndicator(false)
            })
    }

    override fun onCurrencyItemClick(position: Int) {
        router.finishPage(currencyList[position])
    }

    override fun getSize(): Int {
        return currencyList.size
    }

    override fun getItem(position: Int): Currency {
        return currencyList[position]
    }

    interface CurrencyPresenter {
        fun showData(
            currencies: List<Currency>, presenter: CurrencyAdapter.CurrencyItemPresenter
        )

        fun setIndicator(isShow: Boolean)
    }
}

data class Currency(val abbr: String, val fullName: String) : Serializable
