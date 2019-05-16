package space.flogiston.weather.data.entities.day;

public class Sys
{
    private int type;

    private int id;

    private double message;

    private String country;

    private int sunrise;

    private int sunset;

    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setMessage(double message){
        this.message = message;
    }
    public double getMessage(){
        return this.message;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }
    public void setSunrise(int sunrise){
        this.sunrise = sunrise;
    }
    public int getSunrise(){
        return this.sunrise;
    }
    public void setSunset(int sunset){
        this.sunset = sunset;
    }
    public int getSunset(){
        return this.sunset;
    }
}
