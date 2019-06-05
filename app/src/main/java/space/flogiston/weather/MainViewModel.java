package space.flogiston.weather;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.day.TodayWeather;

class MainViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<TodayWeather> todayWeather;
    MainViewModel(Repository repository) {
        this.repository = repository;
    }
    void loadData () {
        if (todayWeather == null) {
            todayWeather = repository.getTodayWeather("Odessa,ua", "metric");
        }
    }
    MutableLiveData<TodayWeather> getTodayWeather () {
        return todayWeather;
    }
}
