package com.example.newsapp.core.data.local

import androidx.room.Entity

@Entity
data class SpendingEntity(
    val spendingId: Int?=null,
    val name: String,
    val price: Double,
    val kilograms: Double,
    val quantity: Double,
    val dateTimeUtc: String
)
