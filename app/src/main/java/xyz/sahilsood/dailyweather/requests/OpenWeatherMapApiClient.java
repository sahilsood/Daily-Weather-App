package xyz.sahilsood.dailyweather.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.sahilsood.dailyweather.models.WeatherForecast;
import xyz.sahilsood.dailyweather.models.WeatherInfo;

public class OpenWeatherMapApiClient {
    private static OpenWeatherMapApiClient instance;
    private static final String TAG = "OpenWeatherMapApiClient";
    private MutableLiveData<WeatherInfo> mWeather;
    private MutableLiveData<List<WeatherForecast.Listt>> mWeatherForecast;

    public OpenWeatherMapApiClient() {
        mWeather = new MutableLiveData<>();
        mWeatherForecast = new MutableLiveData<>();
        //getWeatherInfo("charlotte");
//        getWeatherForecast();
    }

    public LiveData<WeatherInfo> getWeatherLiveData(){
        return mWeather;
    }
    public LiveData<List<WeatherForecast.Listt>> getWeatherForecastLiveData(){
        return mWeatherForecast;
    }

    public static OpenWeatherMapApiClient getInstance(){
        if(instance == null){
            instance = new OpenWeatherMapApiClient();
        }
        return instance;
    }

    public void getWeatherInfo(String city){
        Call<WeatherInfo> call = ServiceGenerator.getOpenWeatherMapApi().getWeatherInfo(city, "imperial", "05df248e9c6e08986906cc06de7c6753");
        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                if(response.isSuccessful() || response.code()==200){
                    WeatherInfo weatherInfo = response.body();
                    mWeather.postValue(weatherInfo);
                }
            }
            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Log.d(TAG,"onResponse Error: "+t.toString());
                System.out.println(t.getMessage());
            }
        });
    }

    public void getWeatherForecast(String city){
        Call<WeatherForecast> call = ServiceGenerator.getOpenWeatherMapApi().getWeatherForecast(city, "imperial", "05df248e9c6e08986906cc06de7c6753");
        call.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                if(response.isSuccessful() || response.code()==200){
                    List<WeatherForecast.Listt> listdata = new ArrayList<>();
                    WeatherForecast weatherForecast = response.body();
                    listdata = weatherForecast.getList();
                    System.out.println(weatherForecast.getList().get(0).getClouds().toString());
                    mWeatherForecast.postValue(listdata);
                }
            }
            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.d(TAG,"onResponse Error: "+t.toString());
                System.out.println(t.getMessage());
            }
        });
    }

}
