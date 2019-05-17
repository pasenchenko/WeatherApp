package space.flogiston.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

public class ForecastActivity extends AppCompatActivity implements Observer<List<WeatherForecast>> {
    private Repository repository;
    private MutableLiveData<ArrayList<WeatherForecast>> forecastData;

    @Override
    public void onChanged(List<WeatherForecast> weatherForecast) {
        createRecyclerView(weatherForecast);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ForecastViewModel forecastViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        forecastViewModel.loadData(this);
        forecastData = forecastViewModel.getWeatherForecast();
        forecastData.observe(this, this);
    }
    public void createRecyclerView (List<WeatherForecast> weatherForecast) {
        if (weatherForecast != null) {
            RecyclerView recyclerView = findViewById(R.id.rec_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                    RecyclerView.VERTICAL,
                    false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new WeatherAdapter(weatherForecast, ForecastActivity.this));
        }
    }
    public void switchToMain (View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
