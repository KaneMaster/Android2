package com.example.weatherprogranv2;

import java.io.Serializable;

public class Parcel implements Serializable {



    String city;



    Boolean coord;
    int inx;

    boolean pressure;
    boolean wind;
    boolean wet;


    public void setCity(String city) {
        this.city = city;
    }

    public void setInx(int inx) {
        this.inx = inx;
    }

    public String getCity() {
        return city;
    }

    public int getInx() {
        return inx;
    }

    public boolean isPressure() {
        return pressure;
    }

    public boolean isWind() {
        return wind;
    }

    public boolean isWet() {
        return wet;
    }

    public void setWet(boolean wet) {
        this.wet = wet;
    }

    public void setPressure(boolean pressure) {
        this.pressure = pressure;
    }

    public void setWind(boolean wind) {
        this.wind = wind;
    }

    public void setCoord(Boolean coord) {
        this.coord = coord;
    }

    public Boolean getCoord() {
        return coord;
    }
}
