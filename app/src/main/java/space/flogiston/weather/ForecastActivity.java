package space.flogiston.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.flogiston.weather.Forecast.Root;

public class ForecastActivity extends AppCompatActivity implements Observer<List<WeatherEntity>> {
    private WeatherForecast weatherForecast;
    private SharedPreferences sPref;
    MutableLiveData<List<WeatherEntity>> weatherData;

    @Override
    public void onChanged(List<WeatherEntity> weatherEntities) {
        createRecyclerView(weatherEntities);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        sPref = getPreferences(MODE_PRIVATE)    ;
        requestWeatherForecast();
        weatherData = new MutableLiveData<>();
        weatherData.observe(this, this);
    }
    public void createRecyclerView (List<WeatherEntity> currentWeather) {
        RecyclerView recyclerView = findViewById(R.id.rec_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new WeatherAdapter(currentWeather));
    }
    public void requestWeatherForecast () {
        long lastTodayUpdate = sPref.getLong("last_update", -1);
        if (lastTodayUpdate > 0) {
            long now = (new Date()).getTime();
            if ((now - lastTodayUpdate) / 1000 < 1800) {
                final RoomDatabase db = Room.databaseBuilder(
                        ForecastActivity.this, WeatherDB.class, "weather_forecast")
                        .build();
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<WeatherEntity> weatherForecast = ((WeatherDB) db).weatherDao().getWeatherForecast();
                        weatherData.postValue(weatherForecast);
                    }
                });
                // List<WeatherEntity> weatherForecast = ((WeatherDB) db).weatherDao().getWeatherForecast();
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
                            ArrayList<WeatherEntity> weatherList = new ArrayList<WeatherEntity>();
                            for (int i = 0; i < 40; i++) {
                                WeatherEntity n = new WeatherEntity();
                                n.weatherCode = weatherForecast.getList().get(i).getWeather().get(0).getId();
                                n.tempMin = weatherForecast.getList().get(i).getMain().getTemp_min();
                                n.tempMax = weatherForecast.getList().get(i).getMain().getTemp_min();
                                n.date = weatherForecast.getList().get(i).getDt_txt();
                                weatherList.add(n);
                            }
                            RoomDatabase db = Room.databaseBuilder(
                                    ForecastActivity.this, WeatherDB.class,
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
    public void switchToMain (View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
