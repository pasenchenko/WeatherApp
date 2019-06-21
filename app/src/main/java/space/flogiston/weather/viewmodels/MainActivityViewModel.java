package space.flogiston.weather.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.day.TodayWeather;

public class MainActivityViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<TodayWeather> todayWeather;
    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }
    public void loadData () {
        if (todayWeather == null) {
            todayWeather = repository.getTodayWeather("Odessa,ua", "metric");
        }
    }
    public MutableLiveData<TodayWeather> getTodayWeather () {
        return todayWeather;
    }
}
