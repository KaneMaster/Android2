package com.example.weatherprogranv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class SecondActivity extends AppCompatActivity {

    boolean wind, pressure, wet;
    String city_name;



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



}
