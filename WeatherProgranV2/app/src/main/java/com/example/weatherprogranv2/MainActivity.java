package com.example.weatherprogranv2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherprogranv2.bd.DataReader;
import com.example.weatherprogranv2.bd.DataSource;
import com.example.weatherprogranv2.bd.Note;

import java.io.IOException;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private int PERMISSION_REQUEST_CODE;
    LocationListener ll;

    @SuppressLint("WrongConstant")
    @Override
    protected void onResume() {
        DataSource ds = new DataSource(this);

        try {
            ds.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataReader dataReader = ds.getReader();
        String[] cities = new String[dataReader.getCount()];
        Note note;
        for (int i = 0; i< cities.length; i++){
            note = dataReader.getPosition(i);
            cities[i] = note.getCity();
        }
        try {
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = findViewById(R.id.city_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CityAdapter adapter = new CityAdapter(cities);
        recyclerView.setAdapter(adapter);

        Singleton_Data singleton_data = Singleton_Data.Create();
        if (singleton_data.isNonData())
            Toast.makeText(this, this.getResources().getString(R.string.Warning_city), 10000).show();

        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //обработка геолокации
        CheckBox cb = findViewById(R.id.checkbox_geoloc);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    getPermission();
                else {
                    LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
                    locationManager.removeUpdates(ll);
                }
            }
        });

        EditText et = findViewById(R.id.cityText);

        Button b = findViewById(R.id.buttonAccept);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = et.getText().toString();
                Intent itn = new Intent();
                itn.setClass( getBaseContext(), SecondActivity.class);
                Parcel p = new Parcel();
                p.setCoord(cb.isChecked());
                p.setCity(city);
                Singleton_Data sd = Singleton_Data.Create();
                sd.setNonData(false);
                sd.setCityName(city);
                sd.setCoord(false);
                itn.putExtra("INFO", p);
                itn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(itn);
            }
        });

    }

    /**
     * Получение разрешений
     */
    private void getPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, PERMISSION_REQUEST_CODE);
        } else {
            startGeoLock();
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE){
            if (!(grantResults.length == 2 && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)))
            {
                CheckBox cb = findViewById(R.id.checkbox_geoloc);
                cb.setChecked(false);
                Toast.makeText(this, this.getResources().getString(R.string.Warning_geoloc), 10000).show();
            } else {
                startGeoLock();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("MissingPermission")
    private void startGeoLock(){
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            ll = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Singleton_Data sd = Singleton_Data.Create();
                    sd.setLat(location.getLatitude());
                    sd.setLon(location.getLongitude());
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            locationManager.requestLocationUpdates(provider, 100, 10, ll);
        }
    }

}
