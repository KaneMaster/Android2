package com.example.weatherprogranv2.bd;

public class Note {

    private long id;
    private String city;
    private String temp;

    public void setId(long id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getTemp() {
        return temp;
    }
}
