package xyz.sahilsood.dailyweather.utils

import java.text.SimpleDateFormat
import java.util.Date

fun formatDate(timestamp: Int): String {
    val date = Date(timestamp * 1000L)
    val sdf = SimpleDateFormat("EEEE, MMMM dd, yyyy")
    return sdf.format(date)
}

fun formatDay(timestamp: Int): String {
    val date = Date(timestamp * 1000L)
    val sdf = SimpleDateFormat("EE")
    return sdf.format(date)
}

fun formatTime(timestamp: Int): String {
    val date = Date(timestamp * 1000L)
    val sdf = SimpleDateFormat("h:mm a")
    return sdf.format(date)
}

fun formatTemperature(temp: Double): String {
    return "${temp.toInt()}°"
}