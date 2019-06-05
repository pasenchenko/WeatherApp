package space.flogiston.weather;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

class ForecastViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ArrayList<WeatherForecast>> weatherForecast;
    ForecastViewModel(Repository repository) {
        this.repository = repository;
    }
    void loadData () {
        if (weatherForecast == null) {
            weatherForecast = repository.getWeatherForecast("Odessa,ua", "metric");
        }
    }
    MutableLiveData<ArrayList<WeatherForecast>> getWeatherForecast () {
        return weatherForecast;
    }
}

