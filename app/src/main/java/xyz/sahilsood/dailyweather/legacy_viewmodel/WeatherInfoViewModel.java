package xyz.sahilsood.dailyweather.legacy_viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import xyz.sahilsood.dailyweather.models.WeatherForecast;
import xyz.sahilsood.dailyweather.models.WeatherInfo;
import xyz.sahilsood.dailyweather.legacy_repository.WeatherInfoRepository;

public class WeatherInfoViewModel extends ViewModel {
    private static WeatherInfoRepository weatherInfoRepository;

    public WeatherInfoViewModel(){
        weatherInfoRepository = WeatherInfoRepository.getInstance();
    }

    public static LiveData<WeatherInfo> getWeatherInfoLiveData(){
        return weatherInfoRepository.getWeatherInfoLiveData();
    }

    public static LiveData<List<WeatherForecast.Listt>> getWeatherForecastLiveData(){
        return weatherInfoRepository.getWeatherForecastLiveData();
    }

    public static void getWeatherInfo(String city){
        weatherInfoRepository.getWeatherInfo(city);
    }

    public static void getWeatherForecast(String city){
        weatherInfoRepository.getWeatherForecast(city);
    }
}
