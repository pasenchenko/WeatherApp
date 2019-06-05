package space.flogiston.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.day.TodayWeather;

public class MainActivity extends AppCompatActivity implements Observer<TodayWeather> {
    public static SharedPreferences sPref;
    private ImageView weatherImage;
    private TextView weatherCondition;
    private TextView temperature;
    private TextView wind;
    private TextView pressure;
    private TextView humidity;
    MutableLiveData<TodayWeather> todayWeatherData;

    @Override
    public void onChanged(TodayWeather todayWeather) {
        showTodayWeather(todayWeather);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPref = getPreferences(MODE_PRIVATE);
        wind = findViewById(R.id.wind);
        weatherImage = findViewById(R.id.weatherImage);
        weatherCondition = findViewById(R.id.weatherCondition);
        temperature = findViewById(R.id.temperature);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);

        Repository repository = ((WeatherApp)getApplication()).getRepository();
        MainViewModel mainViewModel = ViewModelProviders
                .of(this, new ModelFactory(repository))
                .get(MainViewModel.class);
        mainViewModel.loadData();
        todayWeatherData = mainViewModel.getTodayWeather();
        todayWeatherData.observe(this, this);
    }
    public static int getImageByWeatherCode (int weatherCode) {
        int imageId = R.drawable.placeholder;
        int firstDigit = (weatherCode - weatherCode % 100) / 100;
        if (firstDigit == 2) {
            imageId = R.drawable.thunder;
        } else if (firstDigit == 3 || firstDigit == 5) {
            imageId = R.drawable.rain;
        } else if (firstDigit == 6) {
            imageId = R.drawable.snow;
        } else if (firstDigit == 7 || firstDigit == 8) {
            imageId = R.drawable.clouds;
        }
        return imageId;
    }
    public void showTodayWeather (TodayWeather todayWeather) {
        if (todayWeather != null) {
            weatherImage.setImageResource(getImageByWeatherCode(todayWeather.weatherCode));
            weatherCondition.setText(todayWeather.weatherCondition);
            temperature.setText(getApplicationContext().getString(R.string.today_temperature,
                    todayWeather.temperature));
            wind.setText(getApplicationContext().getString(R.string.today_wind,
                    todayWeather.wind));
            pressure.setText(getApplicationContext().getString(R.string.today_pressure,
                    todayWeather.pressure));
            humidity.setText(getApplicationContext().getString(R.string.today_humidity,
                    todayWeather.humidity));
        }
    }
    public void switchToForecast (View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ForecastActivity.class);
        startActivity(intent);
    }
}
