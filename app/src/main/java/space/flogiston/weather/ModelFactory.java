package space.flogiston.weather;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.viewmodels.DetailFragmentViewModel;
import space.flogiston.weather.viewmodels.ForecastViewModel;
import space.flogiston.weather.viewmodels.ListFragmentViewModel;
import space.flogiston.weather.viewmodels.MainActivityViewModel;

public class ModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository repository;

    public ModelFactory(Repository repository) {
        super();
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            if (modelClass == MainActivityViewModel.class) {
                return (T) new MainActivityViewModel(repository);
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
