package com.phil.icurrency.realtime

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.phil.icurrency.currency.SpacesItemDecoration
import com.phil.icurrency.currency.dpToPx
import com.phil.icurrency.realtime.adapter.RealTimeRateAdapter
import kotlinx.android.synthetic.main.activity_real_time_rate.view.*

class RealTimeRateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CoordinatorLayout(context, attrs, defStyle), RealTimeRateInterator.RealTimeRatePresenter {

    override fun onFinishInflate() {
        super.onFinishInflate()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(SpacesItemDecoration(dpToPx(16f).toInt()))
    }

    override fun showData(realTimeRates: List<RealTimeRate>) {
        val adapter = recyclerView.adapter as RealTimeRateAdapter?
        if (adapter == null) {
            recyclerView.adapter =
                RealTimeRateAdapter(
                    context,
                    realTimeRates
                )
        } else {
            adapter.data = realTimeRates
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCurrencySelectedButtonClick(): LiveData<Void> {
        val mutableLiveData = MutableLiveData<Void>()
        currencySelectedButton.setOnClickListener {
            mutableLiveData.postValue(null)
        }
        return mutableLiveData
    }

    override fun onAmountChanged(): LiveData<Long> {
        val mutableLiveData = MutableLiveData<Long>()
        amountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                val amount = if (text.isBlank()) 1 else text.toLong()
                mutableLiveData.postValue(amount)
            }

        })
        return mutableLiveData
    }

    override fun setIndicatorStatus(isShow: Boolean) {
        if (isShow) {
            indicatorView.show()
        } else {
            indicatorView.hide()
        }
    }
}