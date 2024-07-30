package xyz.sahilsood.dailyweather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import xyz.sahilsood.dailyweather.navigation.WeatherNavigation
import xyz.sahilsood.dailyweather.ui.theme.DailyWeatherTheme

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyWeatherTheme {
                Surface(color = MaterialTheme.colors.background) {
                    WeatherNavigation()
                }
            }
        }
    }

    @Composable
    @Preview
    fun Text() {
        Text(text = "Hello, World")
    }
}