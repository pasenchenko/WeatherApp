package space.flogiston.weather.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

@Dao
public interface WeatherForecastDao {

    @Insert
    void insertWeather (List<WeatherForecast> DBWeatherEntity);

    @Query("SELECT * FROM `weather_forecast`")

    List<WeatherForecast> getWeatherForecast();

    @Query("DELETE FROM `weather_forecast`")

    void deleteAll ();

}
