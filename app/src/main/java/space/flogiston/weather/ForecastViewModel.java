package space.flogiston.weather;

import android.content.Context;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

class ForecastViewModel extends ViewModel {
    private Repository repository;
    MutableLiveData<ArrayList<WeatherForecast>> weatherForecast;
    void loadData (Context context) {
        if (weatherForecast == null) {
            repository = new Repository(context);
            weatherForecast = repository.getWeatherForecast("Odessa,ua", "metric");
        }
    }
    MutableLiveData<ArrayList<WeatherForecast>> getWeatherForecast () {
        return weatherForecast;
    }
}

