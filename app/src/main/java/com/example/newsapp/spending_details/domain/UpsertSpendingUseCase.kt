package com.example.newsapp.spending_details.domain

import com.example.newsapp.core.domain.LocalSpendingDataSource
import com.example.newsapp.core.domain.Spending

class UpsertSpendingUseCase(private val spendingDataSource: LocalSpendingDataSource) {

    suspend operator fun invoke(spending: Spending): Boolean {
        if (spending.name.isEmpty() || spending.price < 0
            || spending.kilograms < 0 || spending.quantity < 0)
           return false

        spendingDataSource.upsertSpending(spending)
        return true

    }
}