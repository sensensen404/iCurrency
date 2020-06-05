package com.phil.icurrency.currency.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phil.icurrency.R
import com.phil.icurrency.currency.Currency

class CurrencyAdapter(
    val context: Context,
    val presenter: CurrencyItemPresenter
) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_currency_item, null)
        return CurrencyListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presenter.getSize()
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        val currency = presenter.getItem(position)
        holder.currencyName.text = currency.fullName
        holder.currencyAbbr.text = currency.abbr
        holder.container.setOnClickListener {
            presenter.onCurrencyItemClick(position)
        }
    }


    inner class CurrencyListViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {

        val currencyName: TextView = rootView.findViewById(R.id.currencyName)
        val currencyAbbr: TextView = rootView.findViewById(R.id.currencyAbbr)
        val container: RelativeLayout = rootView.findViewById(R.id.container)
    }

    interface CurrencyItemPresenter {
        fun onCurrencyItemClick(position: Int)

        fun getSize(): Int

        fun getItem(position: Int): Currency
    }

}