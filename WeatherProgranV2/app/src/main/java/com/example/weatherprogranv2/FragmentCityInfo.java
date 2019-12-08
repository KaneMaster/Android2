package com.example.weatherprogranv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCityInfo extends Fragment {
    public static FragmentCityInfo create(Parcel parcel){
        FragmentCityInfo fragment = new FragmentCityInfo();
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
        View vw = inflater.inflate(R.layout.fragment_city_info, container, false);
        TextView tv = vw.findViewById(R.id.city_name);
        Parcel parcel = getParcel();
        tv.setText(parcel.getCity());
        return vw;
    }
}
