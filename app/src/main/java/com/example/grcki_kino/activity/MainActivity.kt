package com.example.grcki_kino.activity

import NumberGrid
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.grcki_kino.api.RetrofitBuilder
import com.example.grcki_kino.screens.RoundData
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
                            RoundData(
                                viewModel = viewModel,
                                onRoundClicked = { selectedRound ->
                                    navController.navigate("numberGrid/${selectedRound.drawId}")
                                },
                                onClickAnimation = {
                                    // Start your WebViewActivity or perform the desired action
                                    val intent =
                                        Intent(applicationContext, WebViewActivity::class.java)
                                    startActivity(intent)
                                },
                                onAnotherButtonClick = {
                                    // Handle another button click
                                    // You can navigate to another screen or perform other actions here
                                }
                            )
                        }
                        composable(
                            route = "numberGrid/{drawId}",
                            arguments = listOf(navArgument("drawId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val drawId = backStackEntry.arguments?.getInt("drawId") ?: -1
                            NumberGrid(
                                applicationContext,
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