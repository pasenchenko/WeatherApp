package space.flogiston.weather.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Date;

import space.flogiston.weather.WeatherApp;

public class SyncWorker extends Worker {
    public SyncWorker (Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    @Override
    public Result doWork () {
        Repository repository = ((WeatherApp)getApplicationContext()).getRepository();
        repository.getTodayWeather("Odessa", "metric");
        repository.getWeatherForecast("Odessa", "metric");
        SharedPreferences sPref = getApplicationContext()
                .getSharedPreferences("weather", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        long now = (new Date()).getTime();
        ed.putLong("last_upd_worker", now);
        ed.apply();
        return Result.success();
    }
}
