package com.example.newsapp.spending_details.presentation

sealed interface SpendingDetailsEvent {
    data object savedFailed : SpendingDetailsEvent
    data object saveSuccessed : SpendingDetailsEvent
}