package com.example.newsapp

import android.app.Application
import com.example.newsapp.balance.di.balanceModule
import com.example.newsapp.core.di.coreModule
import com.example.newsapp.spending_overview.di.spendingOverviewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(coreModule, balanceModule, spendingOverviewModule)
        }
    }
}