package space.flogiston.weather.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import space.flogiston.weather.data.entities.day.TodayWeather;

@Dao
public interface TodayWeatherDao {

    @Insert
    void insertWeather (TodayWeather todayWeather);

    @Query("SELECT * FROM `today_weather`")

    TodayWeather getTodayWeather();

    @Query("DELETE FROM `today_weather`")

    void deleteAll ();

}
