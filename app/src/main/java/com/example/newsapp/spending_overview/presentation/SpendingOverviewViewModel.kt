package com.example.newsapp.spending_overview.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.domain.CoreRepository
import com.example.newsapp.core.domain.LocalSpendingDataSource
import com.example.newsapp.core.domain.Spending
import com.example.newsapp.spending_overview.presentation.SpendingOverviewActions.LoadSpendingOverviewAndBalance
import com.example.newsapp.spending_overview.presentation.SpendingOverviewActions.OnDateChanged
import com.example.newsapp.spending_overview.presentation.SpendingOverviewActions.OnDeleteSpending
import com.example.newsapp.spending_overview.presentation.utils.randomColor
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class SpendingOverviewViewModel(
    private val spendingDataSource: LocalSpendingDataSource,
    private val coreRepository: CoreRepository
) : ViewModel() {

    var state by mutableStateOf(SpendingOverviewState())
        private set


    fun onAction(action: SpendingOverviewActions) {
        viewModelScope.launch {
            when (action) {
                is LoadSpendingOverviewAndBalance -> {
                    loadSpendingListAndBalance()
                }

                is OnDateChanged -> {
                    val newDate = state.dateList[action.newDate]
                    viewModelScope.launch {
                        state = state.copy(
                            selectedDate = newDate,
                            spendingList = getSpendingListByDate(newDate),
                        )
                    }
                }

                is OnDeleteSpending -> {
                    viewModelScope.launch {
                        spendingDataSource.deleteSpending(action.spendingId)
                        state = state.copy(
                            spendingList = getSpendingListByDate(state.selectedDate),
                            dateList = spendingDataSource.getAllDates(),
                            balance = coreRepository.getBalance() - spendingDataSource.getSpendBalance()
                        )
                    }
                }
            }
        }
    }

    private fun loadSpendingListAndBalance() {
        viewModelScope.launch {
            val allDates = spendingDataSource.getAllDates()
            state = state.copy(
                spendingList = getSpendingListByDate(allDates.lastOrNull() ?: ZonedDateTime.now()),
                balance = coreRepository.getBalance() - spendingDataSource.getSpendBalance(),
                selectedDate = allDates.lastOrNull() ?: ZonedDateTime.now(),
                dateList = allDates.reversed()
            )
        }
    }

    private suspend fun getSpendingListByDate(date: ZonedDateTime): List<Spending> {
        return spendingDataSource.getSpendingsByDate(date).reversed()
            .map { it.copy(color = randomColor()) }
    }
}