package space.flogiston.weather;

import androidx.lifecycle.ViewModel;
import space.flogiston.weather.data.Repository;

class DetailFragmentViewModel extends ViewModel {
    private Repository repository;

    DetailFragmentViewModel(Repository repository) {
        this.repository = repository;
    }
}
