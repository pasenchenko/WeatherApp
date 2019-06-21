package space.flogiston.weather.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import space.flogiston.weather.ModelFactory;
import space.flogiston.weather.R;
import space.flogiston.weather.WeatherApp;
import space.flogiston.weather.data.Repository;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;
import space.flogiston.weather.fragments.DetailFragment;
import space.flogiston.weather.fragments.ListFragment;
import space.flogiston.weather.viewmodels.ForecastViewModel;

public class ForecastActivity extends AppCompatActivity {
    private int orientation;
    private ListFragment listFragment;
    private DetailFragment detailFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.list_detail_activity);

        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.setTitle(getString(R.string.forecast_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {};
        orientation = getResources().getConfiguration().orientation;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            listFragment = new ListFragment();
            fragmentTransaction.add(R.id.layout_list, listFragment);
        } else {
            listFragment = new ListFragment();
            detailFragment = new DetailFragment();
            fragmentTransaction.add(R.id.layout_list, listFragment);
            fragmentTransaction.add(R.id.layout_detail, detailFragment);
        }
        fragmentTransaction.commit();

        Repository repository = ((WeatherApp)getApplication()).getRepository();

        ForecastViewModel forecastViewModel = ViewModelProviders
                .of(this, new ModelFactory(repository))
                .get(ForecastViewModel.class);
    }
    public void updateDetail (WeatherForecast forecast) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            detailFragment.changeData(forecast);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
