package com.example.newsapp.spending_details.presentation

sealed interface SpendingDetailsActions {

    data class UpdateName(val newName: String) : SpendingDetailsActions
    data class UpdatePrice(val newPrice: Double) : SpendingDetailsActions
    data class UpdateKilograms(val newKilograms: Double) : SpendingDetailsActions
    data class UpdateQuantity(val newQuantities: Double) : SpendingDetailsActions
    data object SaveSpending : SpendingDetailsActions
}