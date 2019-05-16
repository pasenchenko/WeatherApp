package space.flogiston.weather.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;

import androidx.lifecycle.MutableLiveData;
import space.flogiston.weather.data.entities.day.TodayWeather;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

import static android.content.Context.MODE_PRIVATE;

public class Repository {
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    private final SharedPreferences sPref;
    public Repository(Context context) {
        localDataSource = new LocalDataSource(context);
        remoteDataSource = new RemoteDataSource(context);
        sPref = context.getSharedPreferences("weather", MODE_PRIVATE);
    }
    public MutableLiveData<TodayWeather> getTodayWeather (final String city, final String units) {
        final MutableLiveData<TodayWeather> liveData = new MutableLiveData<>();
        long lastTodayUpdate = sPref.getLong("last_today_update", -1);
        if (lastTodayUpdate > 0) {
            long now = (new Date()).getTime();
            if ((now - lastTodayUpdate) / 1000 < 1800) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        liveData.postValue(localDataSource.getTodayWeather());
                    }
                });
                return liveData;
            }
        }
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                TodayWeather todayWeather = remoteDataSource.getWeatherDay(city, units);
                if (todayWeather != null) {
                    long now = (new Date()).getTime();
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putLong("last_today_update", now);
                    ed.apply();
                    liveData.postValue(todayWeather);
                    localDataSource.storeTodayWeather(todayWeather);
                }
            }
        });
        return liveData;
    }
    public MutableLiveData<ArrayList<WeatherForecast>> getWeatherForecast (final String city, final String units) {
        final MutableLiveData<ArrayList<WeatherForecast>> liveData = new MutableLiveData<>();
        long lastForecastUpdate = sPref.getLong("last_forecast_update", -1);
        if (lastForecastUpdate > 0) {
            long now = (new Date()).getTime();
            if ((now - lastForecastUpdate) / 1000 < 1800) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<WeatherForecast> weatherForecast = localDataSource.getWeatherForecast();
                        liveData.postValue(weatherForecast);
                    }
                });
                return liveData;
            }
        }
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<WeatherForecast> weatherForecast =
                        remoteDataSource.getWeatherForecast(city, units);
                if (weatherForecast != null) {
                    long now = (new Date()).getTime();
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putLong("last_forecast_update", now);
                    ed.apply();
                    liveData.postValue(weatherForecast);
                    localDataSource.storeWeatherForecast(weatherForecast);
                }
            }
        });
        return liveData;
    }

}
