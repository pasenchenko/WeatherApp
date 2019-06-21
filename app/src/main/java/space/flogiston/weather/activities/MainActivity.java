package space.flogiston.weather.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import space.flogiston.weather.ModelFactory;
import space.flogiston.weather.R;
import space.flogiston.weather.WeatherApp;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.SyncWorker;
import space.flogiston.weather.data.entities.day.TodayWeather;
import space.flogiston.weather.viewmodels.MainActivityViewModel;

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

        sPref = getSharedPreferences("weather", Context.MODE_PRIVATE);
        wind = findViewById(R.id.wind);
        weatherImage = findViewById(R.id.weatherImage);
        weatherCondition = findViewById(R.id.weatherCondition);
        temperature = findViewById(R.id.temperature);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);

        Repository repository = ((WeatherApp)getApplication()).getRepository();
        MainActivityViewModel mainActivityViewModel = ViewModelProviders
                .of(this, new ModelFactory(repository))
                .get(MainActivityViewModel.class);
        /*
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(sPref.getLong("last_upd_worker", 0));

        DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        String dateString = df.format(calendar.getTime());
        Toast.makeText(this, dateString, Toast.LENGTH_SHORT).show();*/

        mainActivityViewModel.loadData();

        todayWeatherData = mainActivityViewModel.getTodayWeather();
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
