package space.flogiston.weather.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import space.flogiston.weather.data.entities.day.TodayWeather;

@Database(entities = TodayWeather.class, version = 2, exportSchema = false)
public abstract class TodayWeatherDB extends RoomDatabase {
    public abstract TodayWeatherDao weatherDao();
}
