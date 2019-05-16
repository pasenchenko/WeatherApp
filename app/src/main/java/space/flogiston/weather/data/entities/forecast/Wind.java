package space.flogiston.weather.data.entities.forecast;

public class Wind {
    private double speed;

    private double deg;

    public void setSpeed(double speed){
        this.speed = speed;
    }
    public double getSpeed(){
        return this.speed;
    }
    public void setDeg(double deg){
        this.deg = deg;
    }
    public double getDeg(){
        return this.deg;
    }
}
