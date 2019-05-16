package space.flogiston.weather.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import space.flogiston.weather.data.entities.forecast.WeatherForecast;

@Database(entities = WeatherForecast.class, version = 1, exportSchema = false)
public abstract class WeatherForecastDB extends RoomDatabase {
    public abstract WeatherForecastDao weatherDao();
}
