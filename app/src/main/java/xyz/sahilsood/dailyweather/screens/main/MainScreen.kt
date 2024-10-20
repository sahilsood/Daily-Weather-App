package xyz.sahilsood.dailyweather.screens.main

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import xyz.sahilsood.dailyweather.R
import xyz.sahilsood.dailyweather.data.DataOrException
import xyz.sahilsood.dailyweather.models.DailyWeather
import xyz.sahilsood.dailyweather.navigation.WeatherScreens
import xyz.sahilsood.dailyweather.widgets.MainContent
import xyz.sahilsood.dailyweather.widgets.WeatherAppBar
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String
) {
    val weatherData = produceState<DataOrException<DailyWeather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = city)
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController = navController)
    }
}

@Composable
fun MainScaffold(weather: DailyWeather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + ", ${weather.city.country}",
            navController = navController,
            elevation = 5.dp,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }
        )
    }) { paddingValues ->
        // Falling leaves animation overlay
//        FallingLeavesOverlay()
        // "Happy Fall Y'all" message overlay
//        HappyFallMessage(paddingValues)
        MainContent(data = weather, paddingValues = paddingValues)
    }
}

@Composable
fun FallingLeavesOverlay() {
    val leafCount = 10 // Number of leaves
    for (i in 1..leafCount) {
        FallingLeaf()
    }
}

@Composable
fun FallingLeaf() {
    // Get the screen dimensions using LocalConfiguration
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    // Convert Dp to Px using LocalDensity
    val density = LocalDensity.current
    val screenWidthPx = with(density) { screenWidthDp.toPx() }  // Screen width in pixels
    val screenHeightPx = with(density) { screenHeightDp.toPx() }  // Screen height in pixels

    // Create animation specs for vertical and horizontal movement
    val infiniteTransition = rememberInfiniteTransition()

    // Animate vertical fall with random durations to simulate natural falling
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -50f,  // Start slightly above the screen
        targetValue = screenHeightPx + 50f,  // Fall past the bottom of the screen
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = Random.nextInt(5000, 10000), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Animate horizontal sway with a sinusoidal function for natural left-right movement
    val horizontalDrift = remember { Random.nextInt(30, 80) }  // Amplitude for left-right drift
    val startX =
        remember { Random.nextFloat() * screenWidthPx }  // Random starting X position across the screen width

    // Animate leaf rotation
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,  // Rotate full circle
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = Random.nextInt(6000, 12000), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    // Replace the circle with an image of a leaf
    Image(
        painter = painterResource(id = R.drawable.leaf), // Your leaf image resource
        contentDescription = "Falling Leaf",
        modifier = Modifier
            .size(30.dp)  // Adjust leaf size if needed
            .graphicsLayer(
                translationX = startX + horizontalDrift * sin(offsetY / 100f),  // Sinusoidal left-right drift
                translationY = offsetY,  // Falling down
                rotationZ = rotation  // Rotate leaf
            ),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun HappyFallMessage(paddingValues: PaddingValues) {
    var visible by remember { mutableStateOf(false) }

    // Make the message appear with a delay
    LaunchedEffect(Unit) {
        delay(500) // 0.5-second delay before showing the message
        visible = true
    }

    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.8f), // Slight transparency to see the background
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "Happy Fall Y'all!",
                fontSize = 30.sp,
                color = Color(0xFFFFA500), // Fall orange color
                fontWeight = FontWeight.Bold
            )
        }
    }
}