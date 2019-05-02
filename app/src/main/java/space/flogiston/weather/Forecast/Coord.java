package space.flogiston.weather.Forecast;

public class Coord {
    private double lat;

    private double lon;

    public void setLat(double lat){
        this.lat = lat;
    }
    public double getLat(){
        return this.lat;
    }
    public void setLon(double lon){
        this.lon = lon;
    }
    public double getLon(){
        return this.lon;
    }
}
