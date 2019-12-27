package com.example.weatherprogranv2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {

    boolean wind, pressure, wet;
    String city_name;
    TextView wetL, tempL;
    SensorManager sm;
    Sensor wetSensor, tempSensor;
    AsyncTask<String, String, String> task;
    Timer t;

    @Override
    protected void onStop() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        sp.edit().putBoolean("wet",wet).commit();
        sp.edit().putBoolean("pressure",pressure).commit();
        sp.edit().putBoolean("wind", wind).commit();
        super.onStop();
    }

    @SuppressLint({"StaticFieldLeak", "WrongConstant"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        SharedPreferences sp = getPreferences(MODE_PRIVATE);

        wind = sp.getBoolean("wind", true);
        pressure = sp.getBoolean("pressure", true);
        wet = sp.getBoolean("wet", true);

        Parcel parcel = (Parcel) getIntent().getExtras().getSerializable("INFO");
        parcel.setPressure(pressure);
        parcel.setWet(wet);
        parcel.setWind(wind);

        Intent intent = new Intent(this, Service_GetDate.class);
        intent.putExtra("INFO", parcel);
        startService(intent);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Singleton_Data sd = Singleton_Data.Create();
        if (sd.isNonData()){
            finish();
        }

        FragmentCityInfo frag = FragmentCityInfo.create(parcel);
        ft.add(R.id.topPanel_info, frag);

        FragmentCityPanel frag2 = FragmentCityPanel.create();
        ft.add(R.id.topPanel_info2, frag2);

        FragmentCharacter frag3 = FragmentCharacter.create(parcel);
        ft.add(R.id.topPanel_info3, frag3);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        city_name = parcel.getCity();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                send(menuItem.getItemId());
                return true;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(wetSensorListenet, wetSensor);
        sm.unregisterListener(tempSensorListenet, tempSensor);
        t.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        tempL = header.findViewById(R.id.val_self_temperature);
        wetL = header.findViewById(R.id.val_self_wet);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        wetSensor = sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        tempSensor = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (wetSensor != null) {
            sm.registerListener(wetSensorListenet, wetSensor, 1000);
        } else {
            wetL.setText(R.string.no_sensor);
        }
        if (tempSensor != null) {
            sm.registerListener(tempSensorListenet, tempSensor, 1000);
        } else {
            tempL.setText(R.string.no_sensor);
        }

        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Singleton_Data sd = Singleton_Data.Create();
                if (sd.isNonData()){
                    finish();
                }

                Parcel parcel = new Parcel();
                parcel.setWet(wet);
                parcel.setWind(wind);
                parcel.setPressure(pressure);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                FragmentCityPanel frag2 = FragmentCityPanel.create();
                ft.replace(R.id.topPanel_info2, frag2);

                FragmentCharacter frag3 = FragmentCharacter.create(parcel);
                ft.replace(R.id.topPanel_info3, frag3);
                ft.commit();
            }
        }, 100, 500);
    }

    public void send(int id){
        Intent itn = new Intent();
        switch (id){
            case R.id.nav_about:
                itn.setClass(this, AboutInfo.class);
                break;
            case R.id.nav_send:
                itn.setClass(this, SendInfo.class);
                break;
        }
        itn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(itn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem mi = menu.getItem(1);
        mi.setChecked(wind);
        mi = menu.getItem(2);
        mi.setChecked(pressure);
        mi = menu.getItem(3);
        mi.setChecked(wet);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_choose_city:
                finish();
                return true;
            case R.id.menu_off_wind:
                item.setChecked(!item.isChecked());
                wind = item.isChecked();
                changeFragment();
                return true;
            case R.id.menu_off_pressure:
                item.setChecked(!item.isChecked());
                pressure = item.isChecked();
                changeFragment();
                return true;
            case R.id.menu_off_wet:
                item.setChecked(!item.isChecked());
                wet = item.isChecked();
                changeFragment();
                return true;
        }
            return true;
        }

        private void changeFragment(){
            Parcel parcel = new Parcel();
            parcel.setWind(wind);
            parcel.setWet(wet);
            parcel.setPressure(pressure);
            parcel.setCity(city_name);

            FragmentCharacter fch = FragmentCharacter.create(parcel);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.topPanel_info3, fch);
            ft.commit();
        }

        SensorEventListener wetSensorListenet = new SensorEventListener(){

            @Override
            public void onSensorChanged(SensorEvent event) {
                wetL.setText(String.valueOf(event.values[0]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    SensorEventListener tempSensorListenet = new SensorEventListener(){

        @Override
        public void onSensorChanged(SensorEvent event) {
            tempL.setText(String.valueOf(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };



}
