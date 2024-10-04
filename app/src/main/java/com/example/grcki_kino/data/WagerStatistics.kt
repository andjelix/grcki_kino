package com.example.grcki_kino.data

data class WagerStatistics(
    val columns: Int,
    val wagers: Int,
    val addOn: List<AddOn> // Assuming the AddOn class is already defined as shown previously
)