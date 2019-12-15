package com.example.weatherprogranv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class FragmentCityPanel extends Fragment {
    public static FragmentCityPanel create(){
        FragmentCityPanel fragment = new FragmentCityPanel();
//        Bundle args = new Bundle();
//        args.putSerializable("INFO", parcel);
//        fragment.setArguments(args);
        return fragment;
    }

//    public Parcel getParcel() {
//        return (Parcel) getArguments().getSerializable("INFO");
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_weather_panel, container, false);
        TextView temp_panel = vw.findViewById(R.id.panel_temp);
        Singleton_Data sd = Singleton_Data.Create();
        double temp_val = sd.getTemperature();
        String val = (temp_val > 0)?"+":"";
        val += String.valueOf(temp_val);
        temp_panel.setText(val);
        Picasso.with(getActivity())
                .load("https://www.weatherbit.io/static/img/icons/" + sd.getImg() + ".png")
                .error(R.drawable.loading)
                .into((ImageView) vw.findViewById(R.id.image_weather));
        return vw;
    }
}
