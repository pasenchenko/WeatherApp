package space.flogiston.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.day.TodayWeather;
import space.flogiston.weather.data.entities.day.WeatherDay;

public class MainActivity extends AppCompatActivity implements Observer<TodayWeather> {
    // public static String API_KEY = "5305c0a44167aba1f5518826de32b713";
    public static SharedPreferences sPref;
    private ImageView weatherImage;
    private TextView weatherCondition;
    private TextView temperature;
    private TextView wind;
    private TextView pressure;
    private TextView humidity;
    private Repository repository;
    MutableLiveData<TodayWeather> todayWeatherLiveData;

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
        repository = new Repository(this);
        todayWeatherLiveData = repository.getTodayWeather("Odessa,ua", "metric");
        todayWeatherLiveData.observe(this, this);
        // today();

    }
    /*public void today () {
        long lastTodayUpdate = sPref.getLong("last_today_update", -1);
        if (lastTodayUpdate > 0) {
            long now = (new Date()).getTime();
            if ((now - lastTodayUpdate) / 1000 < 1800) {

                showTodayWeather();
                return;
            }
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherService.class);

        weatherService.getWeatherByCityName("Odessa,ua", "metric", MainActivity.API_KEY).enqueue(new Callback<WeatherDay>() {
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                if (response.isSuccessful()) {
                    WeatherDay weatherDay = response.body();
                    temperature.setText("abcd");
                    //Log.e("Main", "Exception abcd");
                    int weatherCode = weatherDay.getWeather().get(0).getId();

                    long now = (new Date()).getTime();
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putLong("last_today_update", now);
                    ed.putInt("weather_code", weatherCode);
                    ed.putString("weather_condition", weatherDay.getWeather().get(0).getMain().toUpperCase());
                    ed.putFloat("temperature", (float)weatherDay.getMain().getTemp());
                    ed.putFloat("wind", (float)weatherDay.getWind().getSpeed());
                    ed.putFloat("pressure", (float)Math.round(weatherDay.getMain().getPressure() * 0.75));
                    ed.putFloat("humidity", weatherDay.getMain().getHumidity());

                    ed.apply();
                    showTodayWeather();
                    return;
                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                // Log.e("Main", "Exception" + t.toString());
            }
        });
    }*/
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

    /*
    public interface WeatherService {
        @GET("/data/2.5/weather")
        Call<WeatherDay> getWeatherByCityName(@Query("q")String city, @Query("units")String units,
                                              @Query("appid") String appID);
    }
    */
}
