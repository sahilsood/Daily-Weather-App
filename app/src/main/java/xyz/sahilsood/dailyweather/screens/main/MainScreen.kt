package xyz.sahilsood.dailyweather.screens.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    Text(text = "Main Screen")
    val mainViewModel: MainViewModel = hiltViewModel()
    mainViewModel.data.value.data?.let {
        Text(text = it.toString())
    }
}