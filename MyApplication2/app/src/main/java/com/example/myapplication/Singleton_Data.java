package com.example.myapplication;

import android.app.Activity;

public class Singleton_Data {
    public static Singleton_Data sd;
    private Activity act;


    public static Singleton_Data create(){
        if (sd == null){
            sd = new Singleton_Data();
        }
        return sd;
    }

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }



}
