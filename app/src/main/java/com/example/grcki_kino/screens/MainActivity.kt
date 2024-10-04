package com.example.grcki_kino.screens

import NumberGrid
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.grcki_kino.api.RetrofitBuilder
import com.example.grcki_kino.data.RoundDataClass
import com.example.grcki_kino.utils.GameConstants
import com.example.grcki_kino.viewmodel.RoundViewModel
import com.example.grcki_kino.viewmodel.RoundViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: RoundViewModel by viewModels {
        RoundViewModelFactory(RetrofitBuilder.apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "roundData") {
                        composable("roundData") {
                            RoundData(viewModel) { selectedRound ->
                                navController.navigate("numberGrid/${selectedRound.drawId}")
                            }
                        }
                        composable(
                            route = "numberGrid/{drawId}",
                            arguments = listOf(navArgument("drawId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val drawId = backStackEntry.arguments?.getInt("drawId") ?: -1
                            NumberGrid(
                                numbers = (1..80).toList(),
                                drawId = drawId,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun RoundData(viewModel: RoundViewModel, onRoundClicked: (RoundDataClass) -> Unit) {

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
            RoundTable(roundsData) { selectedRound ->
                onRoundClicked(selectedRound)
                println("Clicked on Round with Game ID: ${selectedRound.drawId}")
            }
        }
    }

    // Fetch data when the app is launched
    viewModel.fetchRounds(GameConstants.GAME_ID)

}

