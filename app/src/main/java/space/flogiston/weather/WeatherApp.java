package space.flogiston.weather;

import android.app.Application;

import space.flogiston.weather.data.Repository;

public class WeatherApp extends Application {

    private Repository repository;
    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(this);
    }

    public Repository getRepository() {
        return repository;
    }
}
