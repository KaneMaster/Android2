package com.example.weatherprogranv2;

public class Singleton_Data {

    private static Singleton_Data obj;
    private double temperature;

    public static Singleton_Data Create(){
        if (Singleton_Data.obj == null){
            Singleton_Data.obj = new Singleton_Data();
            Singleton_Data.obj.temperature = 0;
        }
        return  Singleton_Data.obj;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

}
