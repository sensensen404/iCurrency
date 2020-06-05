package com.phil.icurrency.realtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phil.icurrency.R
import com.phil.icurrency.realtime.RealTimeRate

class RealTimeRateAdapter(val context: Context, var data: List<RealTimeRate>) :
    RecyclerView.Adapter<RealTimeRateAdapter.CurrencyListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_real_time_rate_item, null)
        return CurrencyListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        holder.currencyName.text = data[position].name
        holder.currencyPrice.text = data[position].rate.toString()
        holder.subtitle.text = data[position].amountDescription
    }


    inner class CurrencyListViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        val currencyName: TextView = rootView.findViewById(R.id.currencyName)
        val currencyPrice: TextView = rootView.findViewById(R.id.priceView)
        val subtitle: TextView = rootView.findViewById(R.id.subtitle)
    }

}