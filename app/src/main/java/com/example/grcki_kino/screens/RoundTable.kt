package com.example.grcki_kino.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.grcki_kino.R
import com.example.grcki_kino.data.RoundDataClass
import com.example.grcki_kino.utils.GameConstants
import com.example.grcki_kino.utils.TimeUtils
import com.example.grcki_kino.utils.TimeUtils.Stopwatch
import com.example.grcki_kino.viewmodel.RoundViewModel


@Composable
fun RoundTable(
    roundList: List<RoundDataClass>,
    onRowClick: (RoundDataClass) -> Unit,
    onLiveDrawingClicked: () -> Unit,
    onShowRoundsResultsClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround, // Space out buttons
        verticalAlignment = Alignment.CenterVertically // Al
    ) {
        Button(
            onClick = { onLiveDrawingClicked() },
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp), // Adjust the corner radius as needed
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta) // Button color
        ) {
            Text(
                text = stringResource(R.string.izvlacenje_uzivo),
                color = Color.White
            )
        }

        Button(
            onClick = { onShowRoundsResultsClicked() },
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp), // Adjust the corner radius as needed
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green) // Button color
        ) {
            Text(
                text = stringResource(R.string.rezultati_izvlacenja),
                color = Color.White
            )
        }
    }

    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.vreme_izvlacenja),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.preostalo_za_uplatu),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        items(roundList) { round ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(50.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface)
                    .clickable { onRowClick(round) },
                verticalAlignment = Alignment.CenterVertically
            ) {

                var formattedTime by remember { mutableStateOf("XX:XX") }

                Stopwatch(round, onTimeUpdate = { timeDisplay ->
                    formattedTime = timeDisplay
                })

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = TimeUtils.parseTimestampToTime(round.drawTime),
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = formattedTime, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun RoundData(viewModel: RoundViewModel, onRoundClicked: (RoundDataClass) -> Unit,
              onClickAnimation: () -> Unit, onAnotherButtonClick: () -> Unit) {
    val roundsData by viewModel.roundsData.observeAsState(emptyList())
    val errorMessage by viewModel.error.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (roundsData.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            RoundTable(
                roundList = roundsData,
                onRowClick = { selectedRound -> onRoundClicked(selectedRound) },
                onLiveDrawingClicked = {onClickAnimation()},
                onShowRoundsResultsClicked = {onAnotherButtonClick()}
            )

        }
    }
    viewModel.fetchRounds(GameConstants.GAME_ID)
}