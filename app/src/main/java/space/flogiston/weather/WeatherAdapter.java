package space.flogiston.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder>{
    private List<WeatherForecast> data;
    private Context context;

    WeatherAdapter(List<WeatherForecast> weatherForecast, Context context) {
        this.data = weatherForecast;
        this.context = context;
        notifyDataSetChanged();
    }

    void changeData(List<WeatherForecast> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    class WeatherHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView text1;
        private TextView text2;
        private TextView date;

        WeatherHolder(View view) {
            super(view);
            image = view.findViewById(R.id.listWeatherImage);
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
            date = view.findViewById(R.id.date);
        }

        void setContent(int weatherId, double weatherTMin, double weatherTMax, String dateStr) {
            image.setImageResource(MainActivity.getImageByWeatherCode(weatherId));
            text1.setText(context.getApplicationContext().getString(R.string.forecast_tmin, weatherTMin));
            text2.setText(context.getApplicationContext().getString(R.string.forecast_tmax, weatherTMax));
            date.setText(dateStr);
        }
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.weather_item, viewGroup, false);
        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder weatherHolder, final int i) {
        int weatherId = data.get(i).weatherCode;
        double weatherTMin = data.get(i).tempMin;
        double weatherTMax = data.get(i).tempMax;
        String date = data.get(i).date;
        weatherHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ForecastActivity)context).updateDetail(data.get(i));
            }
        });
        weatherHolder.setContent(weatherId, weatherTMin, weatherTMax, date);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
