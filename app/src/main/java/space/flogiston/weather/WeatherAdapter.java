package space.flogiston.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.weather.Forecast.Root;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder>{
    private List<WeatherEntity> data;

    public WeatherAdapter(List<WeatherEntity> currentWeather) {
        this.data = currentWeather;
        notifyDataSetChanged();
    }

    /* public void changeData(String[] newData) {
        this.data = newData;
    } */

    class WeatherHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView text1;
        private TextView text2;
        private TextView date;

        public WeatherHolder(View view) {
            super(view);
            image = view.findViewById(R.id.listWeatherImage);
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
            date = view.findViewById(R.id.date);
        }

        public void setContent(int weatherId, double weatherTMin, double weatherTMax, String dateStr) {
            image.setImageResource(MainActivity.getImageByWeatherCode(weatherId));
            text1.setText("Tmin: " + weatherTMin + " °C");
            text2.setText("Tmax: " + weatherTMax + " °C");
            date.setText("" + dateStr);
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
    public void onBindViewHolder(@NonNull WeatherHolder weatherHolder, int i) {
        int weatherId = data.get(i).weatherCode;
        double weatherTMin = data.get(i).tempMin;
        double weatherTMax = data.get(i).tempMax;
        String date = data.get(i).date;
        weatherHolder.setContent(weatherId, weatherTMin, weatherTMax, date);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
