package space.flogiston.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

public class ForecastActivity extends AppCompatActivity implements Observer<List<WeatherForecast>> {
    //private WeatherForecast weatherForecast;
    //private SharedPreferences sPref;
    private Repository repository;
    private MutableLiveData<ArrayList<WeatherForecast>> forecastLiveData;

    @Override
    public void onChanged(List<WeatherForecast> weatherForecast) {
        createRecyclerView(weatherForecast);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //sPref = getPreferences(MODE_PRIVATE);
        repository = new Repository(this);
        forecastLiveData = repository.getWeatherForecast("Odessa,ua", "metric");
        forecastLiveData.observe(this, this);
    }
    public void createRecyclerView (List<WeatherForecast> weatherForecast) {
        if (weatherForecast != null) {
            RecyclerView recyclerView = findViewById(R.id.rec_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                    RecyclerView.VERTICAL,
                    false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new WeatherAdapter(weatherForecast, ForecastActivity.this));
        }
    }/*
    public void requestWeatherForecast () {
        long lastTodayUpdate = sPref.getLong("last_update", -1);
        if (lastTodayUpdate > 0) {
            long now = (new Date()).getTime();
            if ((now - lastTodayUpdate) / 1000 < 1800) {
                final RoomDatabase db = Room.databaseBuilder(
                        ForecastActivity.this, WeatherForecastDB.class, "weather_forecast")
                        .build();
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<DBWeatherEntity> weatherForecast = ((WeatherForecastDB) db).weatherDao().getWeatherForecast();
                        weatherData.postValue(weatherForecast);
                    }
                });
                // List<WeatherForecast> weatherForecast = ((WeatherForecastDB) db).weatherDao().getWeatherForecast();
                // createRecyclerView(weatherForecast);
                return;
            }
        }
        Retrofit retrofitForecast = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherForecast = retrofitForecast.create(WeatherForecast.class);

        weatherForecast.getWeatherByCityName("Odessa,ua", "metric", MainActivity.API_KEY)
                .enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(Call<Root> call, Response<Root> response) {
                        if (response.isSuccessful()) {
                            Root weatherForecast = response.body();
                            ArrayList<DBWeatherEntity> weatherList = new ArrayList<DBWeatherEntity>();
                            for (int i = 0; i < 40; i++) {
                                DBWeatherEntity n = new DBWeatherEntity();
                                n.weatherCode = weatherForecast.getList().get(i).getWeather().get(0).getId();
                                n.tempMin = weatherForecast.getList().get(i).getMain().getTemp_min();
                                n.tempMax = weatherForecast.getList().get(i).getMain().getTemp_min();
                                n.date = weatherForecast.getList().get(i).getDt_txt();
                                weatherList.add(n);
                            }
                            RoomDatabase db = Room.databaseBuilder(
                                    ForecastActivity.this, WeatherForecastDB.class,
                                    "weather_forecast").build();
                            WeatherDBOperation dbOp = new WeatherDBOperation(db, "insertWeather", weatherList);
                            dbOp.start();

                            long now = (new Date()).getTime();
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putLong("last_update", now);
                            ed.apply();

                            weatherData.postValue(weatherList);
                            // temperature.setText("" + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Root> call, Throwable t) {
                        // temperature.setText("Exception in forecast receiving" + t.toString());
                    }
                });
    }
    public interface WeatherForecast {
        @GET("/data/2.5/forecast")
        Call<Root> getWeatherByCityName(@Query("q")String city, @Query("units")String units,
                                        @Query("appid") String appID);
    }
    */
    public void switchToMain (View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
