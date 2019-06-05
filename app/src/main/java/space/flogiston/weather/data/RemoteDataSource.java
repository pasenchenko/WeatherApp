package space.flogiston.weather.data;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import space.flogiston.weather.R;
import space.flogiston.weather.data.entities.day.TodayWeather;
import space.flogiston.weather.data.entities.day.WeatherDay;
import space.flogiston.weather.data.entities.forecast.Root;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;
import space.flogiston.weather.data.entities.forecast.WeatherList;

class RemoteDataSource {
    private WeatherService weatherService;
    private String apiKey;
    RemoteDataSource(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherService.class);
        apiKey = context.getString(R.string.api_key);
    }
    TodayWeather getWeatherDay (String city, String units) {
        Call<WeatherDay> call = weatherService.getWeatherDay(city, units, this.apiKey);
        try {
            Response<WeatherDay> response = call.execute();
            if (response.isSuccessful()) {
                WeatherDay weatherDay = response.body();
                TodayWeather todayWeather = new TodayWeather();
                todayWeather.weatherCode = weatherDay.getWeather().get(0).getId();
                todayWeather.weatherCondition = weatherDay.getWeather().get(0).getMain().toUpperCase();
                todayWeather.temperature = weatherDay.getMain().getTemp();
                todayWeather.wind = weatherDay.getWind().getSpeed();
                todayWeather.pressure = (double)Math.round(weatherDay.getMain().getPressure() * 0.75);
                todayWeather.humidity = weatherDay.getMain().getHumidity();
                return todayWeather;
            }
        } catch (IOException ioex) {
            return null;
        }
        return null;
    }
    ArrayList<WeatherForecast> getWeatherForecast (String city, String units) {
        Call<Root> call = weatherService.getWeatherForecast(city, units, this.apiKey);
        try {
            Response<Root> response = call.execute();
            if (response.isSuccessful()) {
                Root root = response.body();
                ArrayList<WeatherForecast> weatherForecast = new ArrayList<>();
                for (int i = 0; i < 40; i++) {
                    WeatherForecast n = new WeatherForecast();
                    WeatherList weatherList = root.getList().get(i);
                    n.weatherCode = weatherList.getWeather().get(0).getId();
                    n.weatherCondition = weatherList.getWeather().get(0).getMain().toUpperCase();
                    n.tempMin = weatherList.getMain().getTemp_min();
                    n.tempMax = weatherList.getMain().getTemp_max();
                    n.date = weatherList.getDt_txt();
                    n.wind = weatherList.getWind().getSpeed();
                    n.pressure = (double)Math.round(weatherList.getMain().getPressure() * 0.75);
                    n.humidity = weatherList.getMain().getHumidity();
                    weatherForecast.add(n);
                }
                return weatherForecast;
            }
        } catch (IOException ioex) {
            return null;
        }
        return null;
    }
}
