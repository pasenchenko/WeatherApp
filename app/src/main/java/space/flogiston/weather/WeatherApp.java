package space.flogiston.weather;

import android.app.Application;
import android.provider.SyncStateContract;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.SyncWorker;

public class WeatherApp extends Application {

    private Repository repository;
    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(this);
        PeriodicWorkRequest.Builder sendDataBuilder =
                new PeriodicWorkRequest.Builder(SyncWorker.class, 20, TimeUnit.MINUTES,
                        10, TimeUnit.MINUTES).setConstraints(Constraints.NONE);
        PeriodicWorkRequest periodicWorkRequest = sendDataBuilder
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                "SyncData",  ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
    }

    public Repository getRepository() {
        return repository;
    }
}
