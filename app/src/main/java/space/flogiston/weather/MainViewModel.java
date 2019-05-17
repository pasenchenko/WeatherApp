package space.flogiston.weather;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.day.TodayWeather;

class MainViewModel extends ViewModel {
    private Repository repository;
    MutableLiveData<TodayWeather> todayWeather;
    void loadData (Context context) {
        if (todayWeather == null) {
            repository = new Repository(context);
            todayWeather = repository.getTodayWeather("Odessa,ua", "metric");
        }
    }
    MutableLiveData<TodayWeather> getTodayWeather () {
        return todayWeather;
    }
}
