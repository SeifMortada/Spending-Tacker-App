package com.example.newsapp.spending_details.di

import com.example.newsapp.spending_details.domain.UpsertSpendingUseCase
import com.example.newsapp.spending_details.presentation.SpendingDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val spendingDetailsModule = module {

    single { UpsertSpendingUseCase(spendingDataSource = get()) }
    viewModel { SpendingDetailsViewModel(upsertSpendingUseCase = get()) }
}