package com.example.weatherprogranv2;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.weatherprogranv2.bd.DataSource;
import com.example.weatherprogranv2.model.Datum;
import com.example.weatherprogranv2.model.Weather;
import com.example.weatherprogranv2.model.WeatherModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Service_GetDate extends Service {

    URL url;
    String ApiCode = "c3ffbae64d6548ce95bba096176abe15";
    private DataSource datasource;


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            url =  new URL("https://api.weatherbit.io");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    interface WeatherAPI{
        @GET ("v2.0/current")
        Call<WeatherModel> getWeather( @Query("key") String key, @Query("lang") String lang, @Query("city") String city);
    }

    interface WeatherAPICoord{
        @GET ("v2.0/current")
        Call<WeatherModel> getWeatherCoord( @Query("key") String key, @Query("lang") String lang, @Query("lat") double lat, @Query("lon") double lon);
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       datasource = new DataSource(this);
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Parcel parcel = (Parcel) intent.getExtras().getSerializable("INFO");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

       Call<WeatherModel> call;

        if (!parcel.getCoord()){
             call = retrofit.create(WeatherAPI.class).getWeather(ApiCode, "ru", parcel.getCity());
        }
        else
        {
            Singleton_Data sd = Singleton_Data.Create();
            call = retrofit.create(WeatherAPICoord.class).getWeatherCoord(ApiCode, "ru", sd.getLat(), sd.getLon());
        }
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                Singleton_Data progr_data = Singleton_Data.Create();
                WeatherModel weatherModel = response.body();
                if (weatherModel == null) {
                    progr_data.setNonData(true);
                } else {
                    Datum datum = weatherModel.getData().get(0);
                    progr_data.setTemperature(datum.getTemp());
                    progr_data.setPressure(datum.getPres());
                    progr_data.setSpeed(datum.getWindSpd());
                    progr_data.setWet(datum.getRh());
                    Weather weather = datum.getWeather();
                    progr_data.setDescription(weather.getDescription());
                    progr_data.setImg(weather.getIcon());
                    if (!parcel.getCoord()) {
                        datasource.add(parcel.getCity());
                    }
                }
                try {
                    datasource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.i("Wagh!", t.getMessage());
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
