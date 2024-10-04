package com.example.grcki_kino.data

data class RoundDataClass(
    val gameId: Int,
    val drawId: Int,
    val drawTime: Long,
    val status: String,
    val drawBreak: Int,
    val visualDraw: Int,
    val pricePoints: PricePoints,
    val prizeCategories: List<Any>,
    val wagerStatistics: WagerStatistics
)

