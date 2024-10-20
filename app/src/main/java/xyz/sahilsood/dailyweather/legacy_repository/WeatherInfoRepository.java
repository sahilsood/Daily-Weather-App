package xyz.sahilsood.dailyweather.legacy_repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import xyz.sahilsood.dailyweather.models.WeatherForecast;
import xyz.sahilsood.dailyweather.models.WeatherInfo;
import xyz.sahilsood.dailyweather.legacy_network.OpenWeatherMapApiClient;

public class WeatherInfoRepository {
    private static WeatherInfoRepository instance;
    private static OpenWeatherMapApiClient openWeatherMapApiClient;

    private WeatherInfoRepository(){
        openWeatherMapApiClient = OpenWeatherMapApiClient.getInstance();
    }

    public static WeatherInfoRepository getInstance(){
        if(instance == null){
            instance = new WeatherInfoRepository();
        }
        return instance;
    }

    public static LiveData<WeatherInfo> getWeatherInfoLiveData(){
        return openWeatherMapApiClient.getWeatherLiveData();
    }

    public static LiveData<List<WeatherForecast.Listt>> getWeatherForecastLiveData(){
        return openWeatherMapApiClient.getWeatherForecastLiveData();
    }

    public static void getWeatherInfo(String city){
        openWeatherMapApiClient.getWeatherInfo(city);
    }

    public static void getWeatherForecast(String city){
        openWeatherMapApiClient.getWeatherForecast(city);
    }
}
