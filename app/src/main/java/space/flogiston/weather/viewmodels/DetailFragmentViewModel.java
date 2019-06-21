package space.flogiston.weather.viewmodels;

import androidx.lifecycle.ViewModel;
import space.flogiston.weather.data.Repository;

public class DetailFragmentViewModel extends ViewModel {
    private Repository repository;

    public DetailFragmentViewModel(Repository repository) {
        this.repository = repository;
    }
}
