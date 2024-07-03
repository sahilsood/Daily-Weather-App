package xyz.sahilsood.dailyweather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import xyz.sahilsood.dailyweather.R

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(text = "Hello, World")
        }
    }

    @Composable
    @Preview
    fun Text() {
        Text(text = "Hello, World")
    }
}