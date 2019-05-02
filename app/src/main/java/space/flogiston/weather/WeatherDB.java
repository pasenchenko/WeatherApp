package space.flogiston.weather;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = WeatherEntity.class, version = 1, exportSchema = false)
public abstract class WeatherDB extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}
