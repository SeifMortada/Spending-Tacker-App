package com.example.newsapp.spending_overview.di

import com.example.newsapp.core.domain.LocalSpendingDataSource
import com.example.newsapp.spending_overview.presentation.SpendingOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val spendingOverviewModule = module {


    viewModel { SpendingOverviewViewModel(spendingDataSource = get(), coreRepository = get()) }
}