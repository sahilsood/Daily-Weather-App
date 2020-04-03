package xyz.sahilsood.dailyweather.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import xyz.sahilsood.dailyweather.R;
import xyz.sahilsood.dailyweather.models.WeatherForecast;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    List<WeatherForecast.Listt> mData = new ArrayList<>();

    public WeatherAdapter(List<WeatherForecast.Listt> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherForecast.Listt weather = mData.get(position);
        holder.itemTemp.setText((String.valueOf(Math.round(weather.getMain().getTemp())))+" F");

        DateFormat outputFormat = new SimpleDateFormat("h a", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        String inputText = weather.getDt_txt();
        Date date = null;
        try {
            date = inputFormat.parse(inputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);

//        holder.itemTime.setText(weather.getDt_txt());
        holder.itemTime.setText(outputText);
//        SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date prettyDate = null;
//        try {
//            prettyDate = formatter6.parse(weather.dateF);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        PrettyTime t = new PrettyTime();
//        long dateToMilli = prettyDate.getTime();
//        holder.date.setText(t.format(new Date(dateToMilli)));

        String url = "http://openweathermap.org/img/wn/" + weather.getWeather().get(0).getIcon() + "@2x.png";
        Picasso.get().load(url).into((holder.itemIcon));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTemp, itemTime;
        ImageView itemIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTemp = (TextView) itemView.findViewById(R.id.tv_item_temp);
            itemTime = (TextView) itemView.findViewById(R.id.tv_item_time);
            itemIcon = (ImageView) itemView.findViewById(R.id.iv_item_icon);

        }
    }
}