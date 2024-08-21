package xyz.sahilsood.dailyweather.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import xyz.sahilsood.dailyweather.R
import xyz.sahilsood.dailyweather.data.DataOrException
import xyz.sahilsood.dailyweather.models.DailyWeather
import xyz.sahilsood.dailyweather.utils.formatDate
import xyz.sahilsood.dailyweather.utils.formatDay
import xyz.sahilsood.dailyweather.utils.formatTemperature
import xyz.sahilsood.dailyweather.utils.formatTime
import xyz.sahilsood.dailyweather.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<DailyWeather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData("Charlotte")
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
        )
    }) { paddingValues ->
        MainContent(data = weather, paddingValues = paddingValues)
    }
}

@Composable
fun MainContent(data: DailyWeather, paddingValues: PaddingValues) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Log.d("TAG", "MainContent: $imageUrl")
    Column(
        Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatDate(timestamp = data.list[0].dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape,
            color = Color(0xFFffC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatTemperature(temp = data.list[0].main.temp),
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = data.list[0].weather[0].main,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
        HumidityCard(main = data.list[0].main)
        Divider()
        SunsetSunriseRow(sys = data.city.sunrise, sunset = data.city.sunset)
        ThisWeekWeather(data = data)
    }
}

@Composable
fun ThisWeekWeather(data: DailyWeather) {
    Text(text = "This Week", style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
    val dailyData = data.list.groupBy { item ->
        formatDate(timestamp = item.dt)
    }.mapValues { it.value.first() }
    LazyColumn(modifier = Modifier.background(Color(0xFFEEEEEE))) {
        items(dailyData.values.take(n = 7).toList()) { item ->
            DailyWeatherItem(item = item)
        }
    }
}

@Composable
fun DailyWeatherItem(item: DailyWeather.WeatherObject) {
    val imageUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png"
    val customShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp, bottomStart = 50.dp, // Adjust for desired roundness
        bottomEnd = 50.dp  // Adjust for desired roundness
    )
    Card(elevation = 4.dp, modifier = Modifier.padding(4.dp), shape = customShape) {
        DailyWeatherCard(item = item, imageUrl = imageUrl)
    }
}

@Composable
fun DailyWeatherCard(item: DailyWeather.WeatherObject, imageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatDay(timestamp = item.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        WeatherStateImage(imageUrl = imageUrl)

        Surface(shape = RoundedCornerShape(20.dp), elevation = 5.dp, color = Color(0xFFffC400)) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = item.weather[0].main,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
        Row {
            Text(
                text = formatTemperature(temp = item.main.temp_max),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = formatTemperature(temp = item.main.temp_min),
                style = MaterialTheme.typography.h6,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SunsetSunriseRow(sys: Int, sunset: Int) {
    Row(
        modifier = Modifier
            .padding(all = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = formatTime(timestamp = sys),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = formatTime(timestamp = sunset),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HumidityCard(main: DailyWeather.WeatherObject.Main) {
    Row(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${main.humidity}%",
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${main.pressure}%",
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.broken_clouds),
                contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${main.feels_like}%",
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "Icon Image",
        modifier = Modifier.size(80.dp)
    )
}
