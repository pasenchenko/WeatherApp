package space.flogiston.weather.data;

import android.content.Context;

import java.util.ArrayList;

import androidx.room.Room;
import space.flogiston.weather.data.entities.day.TodayWeather;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

class LocalDataSource {
    private final TodayWeatherDB dbTodayWeather;
    private final WeatherForecastDB dbWeatherForecast;
    LocalDataSource(Context context) {
        dbTodayWeather = Room.databaseBuilder(context, TodayWeatherDB.class, "today_weather").build();
        dbWeatherForecast = Room.databaseBuilder(context, WeatherForecastDB.class, "weather_forecast").build();
    }

    void storeTodayWeather (TodayWeather todayWeather) {
        dbTodayWeather.weatherDao().deleteAll();
        dbTodayWeather.weatherDao().insertWeather(todayWeather);
    }
    void storeWeatherForecast (ArrayList<WeatherForecast> weatherForecast) {
        dbWeatherForecast.weatherDao().deleteAll();
        dbWeatherForecast.weatherDao().insertWeather(weatherForecast);
    }
    TodayWeather getTodayWeather () {
        return dbTodayWeather.weatherDao().getTodayWeather();
    }
    ArrayList<WeatherForecast> getWeatherForecast () {
        return (ArrayList<WeatherForecast>)dbWeatherForecast.weatherDao().getWeatherForecast();
    }
}
