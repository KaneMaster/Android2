package com.example.weatherprogranv2;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class SecondActivity extends AppCompatActivity {

    boolean wind, pressure, wet;
    String city_name;
    TextView wetL, tempL;
    SensorManager sm;
    Sensor wetSensor, tempSensor;



    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        wind = true;
        pressure = true;
        wet = true;

        Parcel parcel = (Parcel) getIntent().getExtras().getSerializable("INFO");
        parcel.setPressure(pressure);
        parcel.setWet(wet);
        parcel.setWind(wind);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        FragmentCityInfo frag = FragmentCityInfo.create(parcel);
        ft.add(R.id.topPanel_info, frag);

        FragmentCityPanel frag2 = FragmentCityPanel.create(parcel);
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        tempL = header.findViewById(R.id.val_self_temperature);
        wetL = header.findViewById(R.id.val_self_wet);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);

        new AsyncTask<String, String, Integer>(){

            @Override
            protected Integer doInBackground(String... strings) {
                int result;
                wetSensor = sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                tempSensor = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                if (wetSensor != null) {
                    sm.registerListener(wetSensorListenet, wetSensor, 1000);
                    result = 1;
                } else {
                    result = 0;
                }
                result = result << 1;
                if (tempSensor != null) {
                    sm.registerListener(tempSensorListenet, tempSensor, 1000);
                    result = result + 1;
                } else {
                    result = result + 0;
                }
                return result;
            }

            @Override
            protected void onPostExecute(Integer s) {
                super.onPostExecute(s);
                if ((s & 1) == 0) {
                    tempL.setText(R.string.no_sensor);
                }
                s = s >> 1;
                if ((s & 1) == 0) {
                    wetL.setText(R.string.no_sensor);
                }
            }
        }.execute("");
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
