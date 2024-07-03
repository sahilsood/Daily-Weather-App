package xyz.sahilsood.dailyweather.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import xyz.sahilsood.dailyweather.R;
import xyz.sahilsood.dailyweather.adapters.WeatherAdapter;
import xyz.sahilsood.dailyweather.models.WeatherForecast;
import xyz.sahilsood.dailyweather.models.WeatherInfo;
import xyz.sahilsood.dailyweather.viewmodels.WeatherInfoViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private WeatherInfoViewModel weatherInfoViewModel;
    static String KEY = "key";
    private TextView cityName, todaysDate, currentTemp, tempUnit, tempMax, tempMin, tempDesc;
    private ImageView tempIcon, menuImg;
    Toolbar toolbar;
    SearchView searchView;
    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private ConstraintLayout constraintLayout;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherInfoViewModel = ViewModelProviders.of(this).get(WeatherInfoViewModel.class);
        constraintLayout = findViewById(R.id.constraint_layout);
        searchView = findViewById(R.id.search);
        cityName = findViewById(R.id.tv_city);
        todaysDate = findViewById(R.id.tv_todays_date);
        currentTemp = findViewById(R.id.tv_current_temp);
        tempUnit = findViewById(R.id.tv_unit);
        tempMax = findViewById(R.id.tv_temp_max);
        tempMin = findViewById(R.id.tv_temp_min);
        tempDesc = findViewById(R.id.tv_description);
        tempIcon = findViewById(R.id.iv_temp_icon);
        menuImg = findViewById(R.id.iv_menu);
        cardView = findViewById(R.id.cardView);
        cardView.setBackgroundResource(R.drawable.card_view_shape);
        setSearchView();
        weatherInfoViewModel.getWeatherInfo("charlotte");
        weatherInfoViewModel.getWeatherForecast("charlotte");
        subscribeObservers();
//        constraintLayout.setBackground();
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    private void setSearchView() {
//        ImageView searchIcon = searchView.findViewById(R.id.search);
//        searchIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search));
//        searchView.setQueryHint("Search city here");
        //set color to searchview
//        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
//        searchAutoComplete.setHintTextColor(Color.WHITE);
//        searchAutoComplete.setTextColor(Color.BLACK);
//
//        searchAutoComplete.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        //----------------------- loading json to search view---------------------------
        final ArrayList<String> result = new ArrayList<>();
        String json = loadJSONFromAsset(MainActivity.this);
        try {
            JSONObject root = new JSONObject(json);
            JSONArray data = root.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonData = data.getJSONObject(i);
                result.add(jsonData.getString("city") + ", " + jsonData.getString("state"));
                System.out.println(result.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //---------------------------------------------------------------------------------

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, result);
//        searchAutoComplete.setAdapter(dataAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                weatherInfoViewModel.getWeatherInfo(dataAdapter.getItem(position));
                weatherInfoViewModel.getWeatherForecast(dataAdapter.getItem(position));
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void subscribeObservers() {
        weatherInfoViewModel.getWeatherInfoLiveData().observe(this, new Observer<WeatherInfo>() {
            @Override
            public void onChanged(@Nullable WeatherInfo weatherInfo) {
                if (weatherInfo != null) {
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
                    String dateString = sdf.format(date);
                    cityName.setText(weatherInfo.getName() + ", " + weatherInfo.getSys().getCountry());
                    todaysDate.setText(dateString);
                    currentTemp.setText(String.valueOf(Math.round(weatherInfo.getMain().getTemp())));
                    tempUnit.setText("F");
                    tempMax.setText(Math.round(weatherInfo.getMain().getTemp_max()) + " F");
                    tempMin.setText(Math.round(weatherInfo.getMain().getTemp_min()) + " F");
                    tempDesc.setText(weatherInfo.getWeather().get(0).getDescription());
                    String url = "http://openweathermap.org/img/wn/" + weatherInfo.getWeather().get(0).getIcon() + "@2x.png";
                    Picasso.get().load(url).resize(300, 300).centerCrop().into(tempIcon);
                    setLayoutBack(weatherInfo.getWeather().get(0).getDescription());
                }
            }
        });

        weatherInfoViewModel.getWeatherForecastLiveData().observe(this, new Observer<List<WeatherForecast.Listt>>() {
            @Override
            public void onChanged(List<WeatherForecast.Listt> weatherForecast) {
                if (weatherForecast != null) {
                    mrecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    mrecyclerView.setHasFixedSize(true);

                    mlayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    mrecyclerView.setLayoutManager(mlayoutManager);

                    mAdapter = new WeatherAdapter(weatherForecast);
                    mrecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.cities);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void setLayoutBack(String weatherCode) {
        switch (weatherCode) {
            case "clear sky":
                constraintLayout.setBackgroundResource(R.drawable.clear_sky);
                break;
            case "few clouds":
                constraintLayout.setBackgroundResource(R.drawable.few_clouds);
                break;
            case "scattered clouds":
                constraintLayout.setBackgroundResource(R.drawable.scattered_clouds);
                break;
            case "broken clouds":
                constraintLayout.setBackgroundResource(R.drawable.broken_clouds);
                break;
            case "light rain":
                constraintLayout.setBackgroundResource(R.drawable.shower_rain);
                break;
            case "rain":
                constraintLayout.setBackgroundResource(R.drawable.rain);
                break;
            case "thunderstorm":
                constraintLayout.setBackgroundResource(R.drawable.thunderstorm);
                break;
            case "snow":
                constraintLayout.setBackgroundResource(R.drawable.snow);
                break;
            case "mist":
                constraintLayout.setBackgroundResource(R.drawable.mist);
                break;
            default:
                constraintLayout.setBackgroundResource(R.drawable.scattered_clouds);
                break;
        }
    }
}