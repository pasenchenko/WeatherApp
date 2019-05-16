package space.flogiston.weather.data.entities.forecast;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_forecast")
public class WeatherForecast {
    @PrimaryKey
    public Integer key;

    public Integer weatherCode;
    public Double tempMin;
    public Double tempMax;
    public String date;
}
