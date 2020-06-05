package com.phil.icurrency

import android.app.Application
import com.phil.icurrency.network.RetrofitManager

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitManager.init(this)
    }
}