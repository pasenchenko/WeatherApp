package space.flogiston.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

public class ListFragment extends Fragment implements Observer<List<WeatherForecast>> {
    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    private MutableLiveData<ArrayList<WeatherForecast>> forecastData;

    @Override
    public void onChanged(List<WeatherForecast> weatherForecast) {
        if (weatherForecast != null) {
            weatherAdapter.changeData(weatherForecast);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container);

        recyclerView = view.findViewById(R.id.rec_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        weatherAdapter = new WeatherAdapter(new ArrayList<WeatherForecast>(), getContext());
        recyclerView.setAdapter(weatherAdapter);

        Repository repository = ((WeatherApp)getActivity().getApplication()).getRepository();

        ListFragmentViewModel listFragmentViewModel = ViewModelProviders
                .of(this, new ModelFactory(repository))
                .get(ListFragmentViewModel.class);
        listFragmentViewModel.loadData();
        forecastData = listFragmentViewModel.getWeatherForecast();
        forecastData.observe(this, this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
