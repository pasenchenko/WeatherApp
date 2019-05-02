package space.flogiston.weather;

import java.io.Serializable;

public class TodayWeather implements Serializable {
    public int weatherCode;
    public String weatherCondition;
    public double temperature;
    public double wind;
    public double pressure;
    public int humidity;
}
