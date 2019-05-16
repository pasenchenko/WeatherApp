package space.flogiston.weather.data.entities.day;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "today_weather")
public class TodayWeather {
    @PrimaryKey
    public Integer key;

    public Integer weatherCode;
    public String weatherCondition;
    public Double temperature;
    public Double wind;
    public Double pressure;
    public Integer humidity;
}
