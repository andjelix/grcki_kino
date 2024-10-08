import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.grcki_kino.utils.GameConstants
import com.example.grcki_kino.utils.TimeUtils
import com.example.grcki_kino.utils.TimeUtils.Stopwatch
import com.example.grcki_kino.viewmodel.RoundViewModel
import kotlinx.coroutines.launch

@Composable
fun NumberGrid(
    context: Context,
    numbers: List<Int>,
    drawId: Int,
    maxSelection: Int = 15,
    viewModel: RoundViewModel
) {
    val roundData by viewModel.roundData.observeAsState()

    viewModel.fetchRound(GameConstants.GAME_ID, drawId)

    var selectedNumbers by remember { mutableStateOf(setOf<Int>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val drawingTime = roundData?.drawTime?.let { TimeUtils.parseTimestampToTime(it) }
        val numberOfRound = roundData?.drawId

        var remainingTime by remember { mutableStateOf("00:00") }

        roundData?.let {
            Stopwatch(it, onTimeUpdate = { timeDisplay ->
                remainingTime = timeDisplay
            }, onTimeUp = {
                showToast(context,"Time is up! Choose anoother round.")
            })
        }

        Text(
            text = "Vreme izvlacenja: $drawingTime",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Broj kola: $numberOfRound",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Preostalo vreme za uplatu: $remainingTime",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // LazyVerticalGrid for numbers
        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(numbers, key = { it }) { number ->
                NumberCard(
                    number = number,
                    isSelected = selectedNumbers.contains(number),
                    onClick = {
                        if (remainingTime == "00:00") {
                            showToast(
                                context,
                                "Time is up, you can't choose numbers! Select another round!"
                            )
                        }
                        if (selectedNumbers.contains(number)) {
                            // Deselect the number
                            selectedNumbers -= number
                        } else if (selectedNumbers.size < maxSelection) {
                            // Select the number if under max selection
                            selectedNumbers += number
                        }
                    },
                    isClickable = selectedNumbers.size < maxSelection || selectedNumbers.contains(
                        number
                    ) && remainingTime != "00:00"
                )
            }
        }
    }
}

@Composable
fun NumberCard(number: Int, isSelected: Boolean, onClick: () -> Unit, isClickable: Boolean) {
    val circleRadius = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(4.dp) // Reduced padding to fit more numbers
            .size(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = isClickable) {
                    if (!isSelected) {
                        coroutineScope.launch {
                            circleRadius.animateTo(
                                targetValue = 40f,
                                animationSpec = tween(durationMillis = 300)
                            )
                        }
                    }
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                if (isSelected) {
                    drawCircle(
                        color = Color.Red.copy(alpha = 0.8f),
                        radius = circleRadius.value,
                        style = Stroke(width = 5.dp.toPx())
                    )
                }
            }
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
