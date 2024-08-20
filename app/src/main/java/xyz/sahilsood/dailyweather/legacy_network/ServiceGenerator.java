package xyz.sahilsood.dailyweather.legacy_network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static OpenWeatherMapApi openWeatherMapApi = retrofit.create(OpenWeatherMapApi.class);

    public static OpenWeatherMapApi getOpenWeatherMapApi(){
        return openWeatherMapApi;
    }

}
