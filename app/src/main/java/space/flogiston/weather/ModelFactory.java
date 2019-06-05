package space.flogiston.weather;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.flogiston.weather.data.Repository;

public class ModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository repository;

    ModelFactory(Repository repository) {
        super();
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            if (modelClass == MainViewModel.class) {
                return (T) new MainViewModel(repository);
            } else if (modelClass == ForecastViewModel.class) {
                return (T) new ForecastViewModel(repository);
            } else if (modelClass == ListFragmentViewModel.class) {
                return (T) new ListFragmentViewModel(repository);
            } else if (modelClass == DetailFragmentViewModel.class) {
                return (T) new DetailFragmentViewModel(repository);
            }
        } catch (ClassCastException cce) {
            return null;
        }
        return null;
    }
}
