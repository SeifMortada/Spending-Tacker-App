package com.example.newsapp.spending_overview.presentation.utils

import java.time.ZonedDateTime

fun ZonedDateTime.formatDate(): String{
    return "$dayOfMonth-$monthValue-$year"
}