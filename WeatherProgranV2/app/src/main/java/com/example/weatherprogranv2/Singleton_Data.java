package com.example.weatherprogranv2;

public class Singleton_Data {

    private static Singleton_Data obj;
    private double temperature;
    private Integer wet;
    private double pressure;
    private double speed;
    private String description;
    private String img;
    private String cityName;

    public static Singleton_Data Create(){
        if (Singleton_Data.obj == null){
            Singleton_Data.obj = new Singleton_Data();
            Singleton_Data.obj.temperature = 0;
            Singleton_Data.obj.wet = 0;
            Singleton_Data.obj.pressure = 0;
            Singleton_Data.obj.speed = 0;
            Singleton_Data.obj.description = "";
            Singleton_Data.obj.img = "";
            Singleton_Data.obj.cityName = "Moscow";
        }
        return  Singleton_Data.obj;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setWet(Integer wet) {
        this.wet = wet;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Integer getWet() {
        return wet;
    }

    public double getPressure() {
        return pressure;
    }

    public double getSpeed() {
        return speed;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }



}
