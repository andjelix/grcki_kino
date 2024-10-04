package com.example.grcki_kino.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.grcki_kino.utils.TimeUtils
import com.example.grcki_kino.utils.TimeUtils.Stopwatch

@Composable
fun RoundTable(roundList: List<RoundDataClass>, onRowClick: (RoundDataClass) -> Unit) {

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
                    contentAlignment = Alignment.Center // Center content inside the Box
                ) {
                    Text(
                        text = stringResource(R.string.vreme_izvlacenja),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center // Center content inside the Box
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

                var formattedTime by remember { mutableStateOf("00:00") }

                Stopwatch(round) { timeDisplay ->
                    formattedTime = timeDisplay
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = TimeUtils.parseTimestampToTime(round.drawTime),
                        textAlign = TextAlign.Center)
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