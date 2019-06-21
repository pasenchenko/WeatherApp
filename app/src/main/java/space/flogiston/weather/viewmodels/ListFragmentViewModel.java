package space.flogiston.weather.viewmodels;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

public class ListFragmentViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ArrayList<WeatherForecast>> weatherForecast;
    public ListFragmentViewModel(Repository repository) {
        this.repository = repository;
    }
    public void loadData () {
        if (weatherForecast == null) {
            weatherForecast = repository.getWeatherForecast("Odessa,ua", "metric");
        }
    }
    public MutableLiveData<ArrayList<WeatherForecast>> getWeatherForecast () {
        return weatherForecast;
    }
}
