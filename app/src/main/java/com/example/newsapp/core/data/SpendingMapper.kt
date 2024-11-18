package com.example.newsapp.core.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.newsapp.core.data.local.SpendingEntity
import com.example.newsapp.core.domain.Spending
import java.time.Instant
import java.time.ZoneId

fun SpendingEntity.toSpending(): Spending {
    return Spending(
        spendingId = spendingId,
        name = name,
        price = price,
        kilograms = kilograms,
        quantity = quantity,
        dateTimeUtc = Instant.parse(dateTimeUtc).atZone(ZoneId.of("UTC"))
    )
}

fun Spending.toSpendingEntity(): SpendingEntity {
    return SpendingEntity(
        name = name,
        price = price,
        kilograms = kilograms,
        quantity = quantity,
        dateTimeUtc = dateTimeUtc.toInstant().toString()
    )
}

fun Spending.toNewSpendingEntity(): SpendingEntity {
    return SpendingEntity(
        spendingId = spendingId,
        name = name,
        price = price,
        kilograms = kilograms,
        quantity = quantity,
        dateTimeUtc = dateTimeUtc.toInstant().toString()
    )
}