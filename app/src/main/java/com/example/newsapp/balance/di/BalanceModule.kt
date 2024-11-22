package com.example.newsapp.balance.di

import com.example.newsapp.balance.presentation.BalanceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val balanceModule = module {

    viewModel { BalanceViewModel(get())  }
}