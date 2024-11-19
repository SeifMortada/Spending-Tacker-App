package com.example.newsapp.balance.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.domain.CoreRepository
import kotlinx.coroutines.launch

class BalanceViewModel(
    private val coreRepository: CoreRepository
) : ViewModel() {
    var state by mutableStateOf(BalanceState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                balance = coreRepository.getBalance()
            )
        }
    }

    fun onAction(action: BalanceAction) {
        when (action) {
            is BalanceAction.OnBalanceChanged -> {
                state = state.copy(balance = action.balance)
            }

            is BalanceAction.OnBalanceSaved -> {
                viewModelScope.launch {
                    coreRepository.updateBalance(state.balance)
                }
            }
        }
    }
}