package com.example.newsapp.balance.presentation

sealed interface BalanceAction {
    data class OnBalanceChanged(val balance: Double) : BalanceAction
    data object OnBalanceSaved: BalanceAction
}