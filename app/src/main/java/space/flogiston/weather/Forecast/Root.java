package space.flogiston.weather.Forecast;

import java.util.ArrayList;
import java.util.List;
public class Root {
    private String cod;

    private double message;

    private int cnt;

    private List<WeatherList> list;

    private City city;

    public void setCod(String cod){
        this.cod = cod;
    }
    public String getCod(){
        return this.cod;
    }
    public void setMessage(double message){
        this.message = message;
    }
    public double getMessage(){
        return this.message;
    }
    public void setCnt(int cnt){
        this.cnt = cnt;
    }
    public int getCnt(){
        return this.cnt;
    }
    public void setList(List<WeatherList> list){
        this.list = list;
    }
    public List<WeatherList> getList(){
        return this.list;
    }
    public void setCity(City city){
        this.city = city;
    }
    public City getCity(){
        return this.city;
    }
}
