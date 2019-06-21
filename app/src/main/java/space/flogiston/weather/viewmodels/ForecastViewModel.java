package space.flogiston.weather.viewmodels;

import androidx.lifecycle.ViewModel;

import space.flogiston.weather.data.Repository;

public class ForecastViewModel extends ViewModel {
    private Repository repository;
    public ForecastViewModel(Repository repository) {
        this.repository = repository;
    }
}

