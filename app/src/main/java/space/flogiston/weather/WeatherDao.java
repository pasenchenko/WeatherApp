package space.flogiston.weather;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WeatherDao {

    @Insert
    public void insertWeather (List<WeatherEntity> weatherEntity);

    @Query("SELECT * FROM weather_forecast")

    public List<WeatherEntity> getWeatherForecast();

    @Query("DELETE FROM weather_forecast")

    public int deleteAll ();

}
