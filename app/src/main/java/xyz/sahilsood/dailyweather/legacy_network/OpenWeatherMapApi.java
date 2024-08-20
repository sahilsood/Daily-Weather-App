package xyz.sahilsood.dailyweather.legacy_network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.sahilsood.dailyweather.models.WeatherForecast;
import xyz.sahilsood.dailyweather.models.WeatherInfo;

public interface OpenWeatherMapApi {
    @GET("weather")
    Call<WeatherInfo> getWeatherInfo(@Query("q") String q,
                                     @Query("units") String units,
                                     @Query("appid") String appid);

    @GET("forecast")
    Call<WeatherForecast> getWeatherForecast(@Query("q") String q,
                                             @Query("units") String units,
                                             @Query("appid") String appid);
}
