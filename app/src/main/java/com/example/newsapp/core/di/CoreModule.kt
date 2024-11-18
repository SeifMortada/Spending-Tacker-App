package com.example.newsapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.core.data.RoomSpendingDataSource
import com.example.newsapp.core.data.local.CoreRepositoryImpl
import com.example.newsapp.core.data.local.SpendingDatabase
import com.example.newsapp.core.domain.CoreRepository
import com.example.newsapp.core.domain.LocalSpendingDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    single {
        Room.databaseBuilder(androidApplication(), SpendingDatabase::class.java, "Spending_db")
            .build()
    }
    single { get<SpendingDatabase>().dao }

    single {
        androidApplication().getSharedPreferences(
            "spending_shared_prefrences",
            Context.MODE_PRIVATE
        )
    }

    singleOf(::RoomSpendingDataSource).bind(LocalSpendingDataSource::class)
    singleOf(::CoreRepositoryImpl).bind(CoreRepository::class)
}