package com.example.grcki_kino.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.grcki_kino.data.Game
import com.example.grcki_kino.utils.GameConstants
import com.example.grcki_kino.utils.TimeUtils
import com.example.grcki_kino.viewmodel.RoundViewModel

@Composable
fun WinningNumbersScreen(viewModel: RoundViewModel) {
    val fromDate by remember { mutableStateOf("2024-10-07") }
    val toDate by remember { mutableStateOf("2024-10-07") }

    LaunchedEffect(Unit) {
        viewModel.fetchDrawingResults(GameConstants.GAME_ID, fromDate, toDate)
    }

    val drawingResults by viewModel.drawingResults.observeAsState()
    val errorMessage by viewModel.error.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Rezultati izvlacenja:", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Display error message if any
        errorMessage?.let {
            Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
        }

        drawingResults?.let { result ->
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(result.content) { game ->
                    GameTable(game, fromDate)
                }
            }
        } ?: run {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun GameTable(game: Game, fromDate: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Gray)
    ) {
        Text(
            text = "Vreme izvlacenja: " + fromDate + " " + TimeUtils.parseTimestampToTime(game.drawTime),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )

        Text(
            text = "Kolo: " + game.drawId,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )

        val winningNumbers = game.winningNumbers.list
        if (winningNumbers.isNotEmpty()) {
            NumbersInCircles(winningNumbers)
        } else {
            Text(text = "No winning numbers available", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun NumbersInCircles(numbers: List<Int>) {
    val colors =
        listOf(Color.Red, Color.Black, Color.Blue, Color.DarkGray, Color.Magenta, Color.Cyan)

    Column {
        val rows = numbers.chunked(5)

        for (row in rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEachIndexed { index, number ->
                    NumberCircle(number, colors[index % colors.size])
                }
            }
        }
    }
}

@Composable
fun NumberCircle(number: Int, circleColor: Color) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(circleColor, shape = CircleShape)
            .border(2.dp, Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}