package space.flogiston.weather.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.flogiston.weather.data.entities.day.WeatherDay;
import space.flogiston.weather.data.entities.forecast.Root;

public interface WeatherService {
    @GET("/data/2.5/weather")
    Call<WeatherDay> getWeatherDay(@Query("q")String city, @Query("units")String units,
                                   @Query("appid") String appID);
    @GET("/data/2.5/forecast")
    Call<Root> getWeatherForecast(@Query("q")String city, @Query("units")String units,
                                    @Query("appid") String appID);
}
