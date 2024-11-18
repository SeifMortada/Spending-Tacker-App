package com.example.newsapp.core.data.local

import android.content.SharedPreferences
import com.example.newsapp.core.domain.CoreRepository

class CoreRepositoryImpl(private val preds: SharedPreferences) : CoreRepository {
    override suspend fun updateBalance(balance: Double) {
        preds.edit().putFloat(KEY_BALANCE, balance.toFloat()).apply()
    }

    override suspend fun getBalance(): Double {
        return preds.getFloat(KEY_BALANCE, 0f).toDouble()
    }

    companion object {
        const val KEY_BALANCE = "KEY_BALANCE"
    }
}

