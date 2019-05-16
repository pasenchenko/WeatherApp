package space.flogiston.weather.data.entities.day;

public class Weather {
    private int id;

    private String main;

    private String description;

    private String icon;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setMain(String main){
        this.main = main;
    }
    public String getMain(){
        return this.main;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }
}

