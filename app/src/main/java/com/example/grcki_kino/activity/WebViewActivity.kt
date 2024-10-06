package com.example.grcki_kino.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.grcki_kino.screens.WebViewScreen
import com.example.grcki_kino.utils.GameConstants

class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                WebViewScreen(GameConstants.DRAW_ANIMATION_URL)
            }
        }
    }
}