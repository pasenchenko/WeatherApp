package space.flogiston.weather;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

class ListFragmentViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ArrayList<WeatherForecast>> weatherForecast;
    ListFragmentViewModel(Repository repository) {
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
