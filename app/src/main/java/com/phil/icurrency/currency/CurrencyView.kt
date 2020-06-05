package com.phil.icurrency.currency

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phil.icurrency.currency.adapter.CurrencyAdapter
import kotlinx.android.synthetic.main.activity_currency.view.*

class CurrencyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CoordinatorLayout(context, attrs, defStyle), CurrencyInterator.CurrencyPresenter {

    override fun onFinishInflate() {
        super.onFinishInflate()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(SpacesItemDecoration(dpToPx(16f).toInt()))
    }

    override fun showData(
        currencies: List<Currency>,
        presenter: CurrencyAdapter.CurrencyItemPresenter
    ) {
        val adapter = recyclerView.adapter
        if (adapter == null) {
            recyclerView.adapter =
                CurrencyAdapter(
                    context,
                    presenter
                )
        }
    }

    override fun setIndicator(isShow: Boolean) {
        if (isShow) {
            indicatorView.show()
        } else {
            indicatorView.hide()
        }
    }
}

fun View.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        if (parent.getChildPosition(view) == 0) outRect.top = space
    }
}