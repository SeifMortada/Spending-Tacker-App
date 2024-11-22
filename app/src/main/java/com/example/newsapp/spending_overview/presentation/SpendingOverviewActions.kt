package com.example.newsapp.spending_overview.presentation

sealed interface SpendingOverviewActions {

    data object LoadSpendingOverviewAndBalance : SpendingOverviewActions

    data class OnDateChanged(val newDate: Int) : SpendingOverviewActions

    data class OnDeleteSpending(val spendingId: Int) : SpendingOverviewActions

}