package xyz.sahilsood.dailyweather.screens.search

import android.annotation.SuppressLint
import androidx.appcompat.widget.SearchView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import xyz.sahilsood.dailyweather.widgets.WeatherAppBar

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun SearchScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Search",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }) { paddingValues ->
        Surface {
//            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(text = "Search...")
//            }
            Column(
                Modifier.fillMaxWidth().height(100.dp),
                verticalArrangement = Arrangement.Center, // Centers items vertically within the Column
                horizontalAlignment = Alignment.CenterHorizontally // Centers items horizontally within each row of the Column
            ) {
                Text("Hello")
                Text("World")
            }
        }
    }
}