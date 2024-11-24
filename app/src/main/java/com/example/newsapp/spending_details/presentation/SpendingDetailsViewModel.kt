package com.example.newsapp.spending_details.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.domain.Spending
import com.example.newsapp.spending_details.domain.UpsertSpendingUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class SpendingDetailsViewModel(
    private val upsertSpendingUseCase: UpsertSpendingUseCase
) : ViewModel() {

    var state by mutableStateOf(SpendingDetailsState())
        private set

    private val _eventChannel = Channel<SpendingDetailsEvent>()
    val event = _eventChannel.receiveAsFlow()


    fun onAction(action: SpendingDetailsActions) {
        when (action) {
            is SpendingDetailsActions.UpdateKilograms -> {
                state = state.copy(kilograms = action.newKilograms)
            }

            is SpendingDetailsActions.UpdateName -> {
                state = state.copy(name = action.newName)
            }

            is SpendingDetailsActions.UpdatePrice -> {
                state = state.copy(price = action.newPrice)
            }

            is SpendingDetailsActions.UpdateQuantity -> {
                state = state.copy(quantity = action.newQuantities)
            }

            SpendingDetailsActions.SaveSpending -> {
                viewModelScope.launch {
                    if (saveSpending()) {
                        _eventChannel.send(SpendingDetailsEvent.saveSuccessed)
                    } else _eventChannel.send(SpendingDetailsEvent.savedFailed)
                }
            }
        }
    }

    private suspend fun saveSpending(): Boolean {
        val spending = Spending(
            spendingId = null,
            name = state.name,
            price = state.price,
            kilograms = state.kilograms,
            quantity = state.quantity,
            dateTimeUtc = ZonedDateTime.now()
        )
        return upsertSpendingUseCase(spending = spending)
    }
}