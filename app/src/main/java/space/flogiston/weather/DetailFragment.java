package space.flogiston.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

public class DetailFragment extends Fragment {
    private ImageView weatherImage;
    private TextView weatherCondition;
    private TextView temperature;
    private TextView wind;
    private TextView pressure;
    private TextView humidity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container);
        // getActivity();
        wind = view.findViewById(R.id.wind);
        weatherImage = view.findViewById(R.id.weatherImage);
        weatherCondition = view.findViewById(R.id.weatherCondition);
        temperature = view.findViewById(R.id.temperature);
        pressure = view.findViewById(R.id.pressure);
        humidity = view.findViewById(R.id.humidity);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void changeData (WeatherForecast forecast) {
        if (forecast != null) {
            weatherImage.setImageResource(MainActivity.getImageByWeatherCode(forecast.weatherCode));
            weatherCondition.setText(forecast.weatherCondition);
            temperature.setText(getActivity().getApplicationContext()
                    .getString(R.string.today_temperature,
                    forecast.tempMin));
            wind.setText(getActivity().getApplicationContext()
                    .getString(R.string.today_wind,
                    forecast.wind));
            pressure.setText(getActivity().getApplicationContext()
                    .getString(R.string.today_pressure,
                    forecast.pressure));
            humidity.setText(getActivity().getApplicationContext()
                    .getString(R.string.today_humidity,
                    forecast.humidity));
        }
    }
}
