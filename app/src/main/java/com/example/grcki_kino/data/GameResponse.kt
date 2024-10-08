package com.example.grcki_kino.data


data class GameResponse(
    val content: List<Game>
)

data class Game(
    val gameId: Int,
    val drawId: Int,
    val drawTime: Long,
    val status: String,
    val drawBreak: Int,
    val visualDraw: Int,
    val pricePoints: PricePoints,
    val winningNumbers: WinningNumbers,
    val prizeCategories: List<PrizeCategories>,
    val wagerStatistics: WagerStatistics
)

data class WinningNumbers(
    val list: List<Int>,
    val bonus: List<Int>,
    val sidebets: SideBets
)

data class SideBets(
    val evenNumbersCount:Int,
    val oddNumbersCount:Int,
    val winningColumn:Int,
    val winningParity:String,
    val oddNumbers:List<Int>,
    val evenNumbers:List<Int>
)

data class PrizeCategories(
    val id: Int,
    val divident:Double,
    val winners:Int,
    val distributed:Double,
    val jackpot:Int,
    val fixed:Double,
    val categoryType:Int,
    val gameType:String
)

