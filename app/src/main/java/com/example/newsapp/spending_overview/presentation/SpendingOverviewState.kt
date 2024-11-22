package com.example.newsapp.spending_overview.presentation

import com.example.newsapp.core.domain.Spending
import java.time.ZonedDateTime

data class SpendingOverviewState(
    var spendingList: List<Spending> = emptyList(),
    var dateList: List<ZonedDateTime> = emptyList(),
    var balance: Double = 0.0,
    var selectedDate: ZonedDateTime = ZonedDateTime.now(),
    var isDatePickerDropDownMenuVisible: Boolean = false
)
