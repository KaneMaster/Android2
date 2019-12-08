package com.example.weatherprogranv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCityPanel extends Fragment {
    public static FragmentCityPanel create(Parcel parcel){
        FragmentCityPanel fragment = new FragmentCityPanel();
        Bundle args = new Bundle();
        args.putSerializable("INFO", parcel);
        fragment.setArguments(args);
        return fragment;
    }

    public Parcel getParcel() {
        return (Parcel) getArguments().getSerializable("INFO");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_weather_panel, container, false);
        return vw;
    }
}
