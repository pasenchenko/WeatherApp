package space.flogiston.weather;

import java.util.List;

import androidx.room.RoomDatabase;

public class WeatherDBOperation extends Thread {

    private RoomDatabase db;
    private String operation;
    private List<WeatherEntity> weatherList;
    private boolean stop = false;

    public WeatherDBOperation (RoomDatabase db, String operation, List<WeatherEntity> weatherList) {
        this.db = db;
        this.operation = operation;
        this.weatherList = weatherList;
    }
    public void run () {
        if (this.operation == null) {

        } else if (this.operation == "insertWeather") {
            ((WeatherDB) db).weatherDao().deleteAll();
            ((WeatherDB) this.db).weatherDao().insertWeather(this.weatherList);
        }
    }
    public void stopAll () {
        this.stop = true;
    }
}
