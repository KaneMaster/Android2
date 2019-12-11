package com.example.weatherprogranv2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Service_GetDate extends Service {

    URL url;
    String ApiCode = "c3ffbae64d6548ce95bba096176abe15";



    @Override
    public void onCreate() {
        super.onCreate();
        try {

            url =  new URL("https://api.weatherbit.io/v2.0/current?key=" + ApiCode + "&lang=ru&city=");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Parcel parcel = (Parcel) intent.getExtras().getSerializable("INFO");

        Request.Builder builder = new Request.Builder();
        builder.url(url + parcel.getCity());

        Request request = builder.build();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("Warh!", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject baseObj = new JSONObject(response.body().string());
                    JSONArray data = baseObj.getJSONArray("data");
                    JSONObject tempObj = data.getJSONObject(0);
                    Singleton_Data progr_data = Singleton_Data.Create();
                    progr_data.setTemperature(tempObj.getDouble("temp"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
